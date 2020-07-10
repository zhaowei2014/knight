/*
 * Copyright (c) 2018.掌阅科技
 * All rights reserved.
 */

/*
 * Copyright (c) 2017.掌阅科技
 * All rights reserved.
 */

/*
 * Copyright (c) 2017.掌阅科技
 * All rights reserved.
 */

/*
 * Copyright (c) 2017.掌阅科技
 * All rights reserved.
 */

package com.zw.knight.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.zhangyue.arch.mysql.MysqlClient;
import com.zhangyue.arch.mysql.MysqlClientBuilderFromNamespace;
import com.zhangyue.arch.nsc.NSClient;
import com.zhangyue.common2.utils.NameSpaceUtils;
import com.zhangyue.dj.task.core.dataSource.ShardingDataSource;
import com.zhangyue.dj.task.core.intercept.ShardingInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 描述： mysql 配置
 *
 * @author xingyh
 * @date 2017/11/3
 */
@Component
@PropertySource("classpath:config/database.properties")
public class MysqlConfig {
    @Value("${datasource.master.url}")
    private String masterUrl;
    @Value("${datasource.master.driver-class-name}")
    private String masterDriver;
    @Value("${datasource.master.username}")
    private String masterUsr;
    @Value("${datasource.master.password}")
    private String masterPassword;
    @Value("${datasource.master.max-wait}")
    private int masterMaxWait;
    @Value("${datasource.master.max-active}")
    private int masterMaxActive;
    @Value("${datasource.master.maxIdle}")
    private int masterMaxIdle;
    @Value("${datasource.master.minIdle}")
    private int masterMinIdle;
    @Value("${datasource.master.test-on-borrow}")
    private Boolean masterOnBorrow;
    @Value("${datasource.master.validationQuery}")
    private String masterValidationQuery;
    @Value("${datasource.master.namespace}")
    private String nameSpace;
    @Value("${datasource.master.datasource.initSQL}")
    private String initSql;

    @Autowired
    private NSClient nsClient;

    @Bean
    public MysqlClient mySqlMasterClient() {
        PoolProperties poolProperties = getPoolProperties();
        MysqlClientBuilderFromNamespace clientBuilder = new MysqlClientBuilderFromNamespace();
        clientBuilder.withNamespace(nameSpace).withUrl(masterUrl)
                .withNSClient(nsClient).withType(NameSpaceUtils.DB_MASTER)
                .withPoolProperties(poolProperties);
        MysqlClient mysqlClient = clientBuilder.parsePathConfig().build();
        clientBuilder.watch(5);
        return mysqlClient;
    }

    private PoolProperties getPoolProperties() {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setMaxWait(masterMaxWait);
        poolProperties.setMaxActive(masterMaxActive);
        poolProperties.setMaxIdle(masterMaxIdle);
        poolProperties.setMinIdle(masterMinIdle);
        poolProperties.setTestOnBorrow(masterOnBorrow);
        poolProperties.setValidationQuery(masterValidationQuery);
        poolProperties.setDriverClassName(masterDriver);
        poolProperties.setUsername(masterUsr);
        poolProperties.setPassword(masterPassword);
        poolProperties.setInitSQL(initSql);
        return poolProperties;
    }

    @Bean
    public DataSource shardingDatasource(MysqlClient mysqlClient) {
        return new ShardingDataSource(mysqlClient);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public Interceptor shardingInterceptor() {
        return new ShardingInterceptor();
    }

    public String getInitSql() {
        return initSql;
    }

    public String getMasterUrl() {
        return masterUrl;
    }

    public String getMasterDriver() {
        return masterDriver;
    }

    public String getMasterUsr() {
        return masterUsr;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public int getMasterMaxWait() {
        return masterMaxWait;
    }

    public int getMasterMaxActive() {
        return masterMaxActive;
    }

    public int getMasterMaxIdle() {
        return masterMaxIdle;
    }

    public int getMasterMinIdle() {
        return masterMinIdle;
    }

    public Boolean getMasterOnBorrow() {
        return masterOnBorrow;
    }

    public String getMasterValidationQuery() {
        return masterValidationQuery;
    }
}
