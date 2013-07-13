package com.iteye.wwwcomy.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Liuxn
 *
 */
public class TestPrepareStmt {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/simplejee", "root", "");
			String sql = "insert into worker values(?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "Lili");
			stmt.setString(2, "female");
			stmt.setString(3, "15");
			stmt.executeUpdate();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
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
