package com.warframe.smart4j.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/25 14:49
 * 数据库操作助手类
 */
public final class DatabaseHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final Connection CONNECTIONINITIAL;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            CONNECTIONINITIAL = DriverManager.getConnection(ConfigHelper.getJdbcUrl());

        } catch (ClassNotFoundException e) {
            LOGGER.error("com.mysql.jdbc.Driver is not found", e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            LOGGER.error("connection initial failure", e);
            throw new RuntimeException(e);
        }
    }

    private static ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            return CONNECTIONINITIAL;
        }
    };

    /**
     * 开启事务
     */
    public static void beginTransactin() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("commit transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }


    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("rollback transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    public static Connection getConnection() {
        Connection conn;

        //获取数据库连接，如果CONNECTION_HOLDER为空，就新增加一个
        conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            conn = CONNECTIONINITIAL;
            CONNECTION_HOLDER.set(conn);
        }


        return conn;
    }
}
