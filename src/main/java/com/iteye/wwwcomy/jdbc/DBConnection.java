package com.iteye.wwwcomy.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
	static {
		init();
	}

	private static class Holder {
		private static DBConnection instance = new DBConnection();
	}

	public static DBConnection getConn() {
		return Holder.instance;
	}

	private static void init() {

	}

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/simplejee", "root", "");
			Statement stmt = conn.createStatement();
			String sql = "select * from worker";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("gender"));
				System.out.println(rs.getString("age"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
