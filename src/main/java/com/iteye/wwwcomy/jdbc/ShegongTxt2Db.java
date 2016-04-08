package com.iteye.wwwcomy.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.iteye.wwwcomy.lxn.utils.DebugUtil;

public class ShegongTxt2Db {
    public static final String FOLDER = "C:/download/tmp/";
    public static final String DB_TYPE = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/shegong";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        ShegongTxt2Db migrater = new ShegongTxt2Db();
        migrater.migrate();
    }

    private void migrate() {
        File folder = new File(FOLDER);
        if (!folder.isDirectory()) {
            DebugUtil.warn("The parameter 'FOLDER' is not a directory.");
            return;
        }
        File[] files = folder.listFiles();
        try {
            Class.forName(DB_TYPE);
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String sql = "insert into user(username,password) values(?,?)";
            for (File tmpFile : files) {
                if (tmpFile.isDirectory()) {
                    continue;
                }
                String tmpFileName = tmpFile.getName();
                DebugUtil.info("Dealing with file: " + tmpFileName);
                BufferedReader reader = new BufferedReader(new FileReader(tmpFile));
                PreparedStatement stmt = conn.prepareStatement(sql);
                String tmpLine;
                int counter = 0;
                int c1 = 0;
                conn.setAutoCommit(false);
                while ((tmpLine = reader.readLine()) != null) {
                    counter++;
                    String[] tmpChars = tmpLine.split("----");
                    if (tmpChars.length != 2) {
                        DebugUtil.warn("The Line cannot be handled correctly!-->" + tmpLine);
                        continue;
                    }
                    String userName = tmpChars[0];
                    String password = tmpChars[1];
                    stmt.setString(1, userName);
                    stmt.setString(2, password);
                    stmt.addBatch();
                    if (counter == 1000) {
                        c1++;
                        DebugUtil.info("Commit:" + c1 * 1000);
                        counter = 0;
                        stmt.executeBatch();
                        conn.commit();
                    }
                }
                stmt.executeBatch();
                conn.setAutoCommit(true);
                stmt.close();
                reader.close();
                return;
            }
            conn.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
