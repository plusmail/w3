package kroryi.w3.todo.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public enum ConnectionUtil {

    INSTANCE;
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/webdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "edurootroot";
    private static final int MAX_POOL_SIZE = 20;
    private static final long CONNECTION_TIMEOUT = 10000;

    private HikariDataSource ds;

    ConnectionUtil(){
        initDataSource();
    }

    public void initDataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DB_DRIVER);
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setMaximumPoolSize(MAX_POOL_SIZE);
        config.setConnectionTimeout(CONNECTION_TIMEOUT);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds  =new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        if(ds == null){
            initDataSource();
        }
        return ds.getConnection();
    }
    public void shutdown()
    {
        if(ds != null){
            ds.close();
        }
    }
}
