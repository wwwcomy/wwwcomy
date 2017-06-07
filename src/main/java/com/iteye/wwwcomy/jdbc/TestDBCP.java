package com.iteye.wwwcomy.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * DBCP Test Code
 *
 * @author   xingnan.liu
 */
public class TestDBCP {
    static DataSource dataSource;
    static {
        Properties prop = new Properties();

        try {
            prop.put("driverClassName", "oracle.jdbc.driver.OracleDriver");
            prop.put("url", "jdbc:oracle:thin:@192.168.1.135:1521:orcl");
            String userName = "user";
            String password = "password";
            prop.put("username", userName);
            prop.put("password", password);
            dataSource = BasicDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Connection conn = dataSource.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
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
}
