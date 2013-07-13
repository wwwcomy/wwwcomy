package com.iteye.wwwcomy.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 最简单的JDBC操作
 * 
 * @author Liuxn
 * 
 */
public class TestJdbc {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/simplejee", "root", "");
			stmt = conn.createStatement();
			String sql = "select * from worker";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("gender"));
				System.out.println(rs.getString("age"));
			}
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
