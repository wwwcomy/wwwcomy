package com.iteye.wwwcomy.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestTransaction {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/simplejee", "root", "");

			// 不自动提交
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.addBatch("insert into worker values('Luma4','male','24')");
			stmt.addBatch("insert into worker values('Luma5','male','25')");
			stmt.addBatch("insert into worker values('Luma6','male','26')");
			stmt.executeBatch();
			conn.commit();
			// 注意恢复
			conn.setAutoCommit(true);
		} catch (Throwable e) {
			e.printStackTrace();
			try {
				if (conn != null) {
					// 出错后回滚
					conn.rollback();
					conn.setAutoCommit(true);
				}
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
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
