package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbConnection {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        String dbPath = ExamManagmentSystem.class.getResource("db/javatest.db").getPath().substring(1);
       conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
       return conn;
    }


}
