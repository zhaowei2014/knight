/*
 * Copyright (c) 2018.掌阅科技
 * All rights reserved.
 */
package com.zw.knight.config;


import com.zhangyue.arch.mysql.MysqlClient;
import com.zhangyue.arch.mysql.MysqlClientBuilderFromNamespace;
import com.zhangyue.arch.nsc.NSClient;
import com.zhangyue.common2.utils.NameSpaceUtils;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库数据源配置类
 * xingyh
 */
@Configuration
public class DatabaseConfig {
    @Autowired
    private MysqlConfig mysqlConfig;
    @Autowired
    private NSClient nsClient;


    @Bean
    public MysqlClient mySqlMasterClient() {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setMaxWait(mysqlConfig.getMasterMaxWait());
        poolProperties.setMaxActive(mysqlConfig.getMasterMaxActive());
        poolProperties.setMaxIdle(mysqlConfig.getMasterMaxIdle());
        poolProperties.setMinIdle(mysqlConfig.getMasterMinIdle());
        poolProperties.setTestOnBorrow(mysqlConfig.getMasterOnBorrow());
        poolProperties.setValidationQuery(mysqlConfig.getMasterValidationQuery());
        poolProperties.setDriverClassName(mysqlConfig.getMasterDriver());
        poolProperties.setUsername(mysqlConfig.getMasterUsr());
        poolProperties.setPassword(mysqlConfig.getMasterPassword());
        poolProperties.setInitSQL(mysqlConfig.getInitSql());
        MysqlClientBuilderFromNamespace mcbfn = new MysqlClientBuilderFromNamespace();
        mcbfn.withNamespace(mysqlConfig.getNameSpace()).withUrl(mysqlConfig.getMasterUrl())
                .withNSClient(nsClient).withType(NameSpaceUtils.DB_MASTER)
                .withPoolProperties(poolProperties);
        MysqlClient mysqlClient = mcbfn.parsePathConfig().build();
        mcbfn.watch(5);
        return mysqlClient;
    }
}
