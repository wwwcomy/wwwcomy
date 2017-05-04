package com.iteye.wwwcomy.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class TestC3P0OnNP3 {
    DataSource dsProView;

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = new TestC3P0OnNP3().initProViewDataSource();
            stmt = conn.createStatement();
            String sql = "select * from device";
            rs = stmt.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                i++;
                // System.out.println("data:");
                // System.out.println(rs.getString("deviceid"));
            }
            System.out.println("Done " + i);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null)
                    stmt.close();
                stmt = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Connection initProViewDataSource() throws SQLException {
        Connection conn = null;
        try {
            String url = null;
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String dbHost = "192.168.1.135";
            url = "jdbc:oracle:thin:@" + dbHost + ":" + "1521" + ":" + "orcl";
            Map props = new HashMap();
            props.put("acquireIncrement", 3);
            props.put("acquireRetryAttempts", 10);
            props.put("breakAfterAcquireFailure", false);
            props.put("checkoutTimeout", 30000);
            props.put("idleConnectionTestPeriod", 30);
            props.put("maxIdleTime", 50);
            props.put("maxPoolSize", 20);
            props.put("minPoolSize", 5);
            props.put("maxStatements", 0);
            props.put("testConnectionOnCheckin", false);
            String userName = "user";
            String password = "password";
            dsProView = DataSources.pooledDataSource(DataSources.unpooledDataSource(url, userName, password),
                    props);
            conn = dsProView.getConnection();
            return conn;
        } catch (SQLException e) {
            DataSources.destroy(dsProView);
            throw e;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Class not found initializing ProView datasource.");
        } finally {
        }
    }
}
