package com.zw.knight.core.intercept;

import com.zw.knight.core.annotion.TableSharding;
import com.zw.knight.core.util.DataBaseUtil;
import com.zw.knight.core.util.TargetUtils;
import com.zw.knight.core.util.ThreadLocalUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import static org.apache.ibatis.reflection.SystemMetaObject.DEFAULT_OBJECT_FACTORY;
import static org.apache.ibatis.reflection.SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY;

/**
 * 分表拦截
 *
 * @author xyj
 * @date 2019/9/20
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class ShardingInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ShardingInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Map<String, Object> argMap = ThreadLocalUtil.SHARDING_LOCAL.get();
        LOG.debug("argMap:{}", argMap);
        if (argMap == null || argMap.isEmpty()) {
            return invocation.proceed();
        }
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        statementHandler = (StatementHandler) TargetUtils.getTarget(statementHandler);
        MetaObject metaStatementHandler = (MetaObject) MetaObject.forObject(statementHandler,
                DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        if (originalSql != null && !originalSql.equals("")) {
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
                    .getValue("delegate.mappedStatement");
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            Class<?> classObj = Class.forName(className);
            // 根据配置自动生成分表SQL
            TableSharding tableSharding = classObj.getAnnotation(TableSharding.class);

            if (tableSharding != null) {
                String newSql = getShardingSQL(boundSql, tableSharding, argMap);
                if (newSql != null) {
                    LOG.debug("原sql：\n{},分表后SQL =====>\n{}", boundSql.getSql(), newSql);
                    metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
                }
            }
        }
        return invocation.proceed();
    }

    private String getShardingSQL(BoundSql sql, TableSharding tableSharding, Map<String, Object> argMap) {
        String returnSql = sql.getSql();
        String tableName = tableSharding.tableName();
        int shardingNums = tableSharding.shardingNums();
        Object shardingKeyObj = argMap.get(tableSharding.shardingKey());
        if (shardingKeyObj instanceof String) {
            String shardingKey = (String) shardingKeyObj;
            String newTableName = DataBaseUtil.getShardingTable(tableName, shardingKey, shardingNums);
            newTableName = " " + newTableName + " ";
            //正则替换
            returnSql = returnSql.replaceAll("\\s" + tableName + "\\s", newTableName);
        }
        return returnSql;
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
