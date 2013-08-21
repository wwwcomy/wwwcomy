package com.iteye.wwwcomy.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestTransaction {

	public static void main(String[] args) {
		testStmtBatch();
		testPrepareStmtBatch();
	}

	public static void testStmtBatch() {
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

	private static void testPrepareStmtBatch() {
		Connection conn = null;
		PreparedStatement stmt = null;
		conn = DBManager.getConn();
		stmt = DBManager.prepare(conn, "insert into worker values(?,?,?)");
		try {
			conn.setAutoCommit(false);
			int i = 0;
			while (i < 5) {
				stmt.setString(1, "Name" + i);
				stmt.setString(2, "female");
				stmt.setString(3, "" + i);
				stmt.addBatch();
				i++;
			}
			stmt.executeBatch();
			conn.commit();
		} catch (Throwable e) {
			try {
				if (conn != null) {
					// 出错后回滚
					conn.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBManager.closeStatement(stmt);
			DBManager.closeConnection(conn);
		}

	}
}
