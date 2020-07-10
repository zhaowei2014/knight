package com.zw.knight.core.dataSource;

import com.zhangyue.arch.mysql.MysqlClient;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 分表数据源
 *
 * @author xyj
 * @date 2019/9/20
 */
public class ShardingDataSource implements DataSource {
    private MysqlClient mysqlClient;

    public ShardingDataSource(MysqlClient mysqlClient) {
        this.mysqlClient = mysqlClient;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return mysqlClient.getMysqlTemplate().getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
