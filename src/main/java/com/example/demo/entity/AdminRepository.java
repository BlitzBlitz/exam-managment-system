package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminRepository {
    public static void createExamTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS admin" +
                "(id INTEGER PRIMARY KEY ASC, email TEXT, password TEXT)");
        statement.close();
    }
    public static Admin getAdminByEmail(String userEmail) {
        Admin admin = new Admin();
        try {
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM admin WHERE email = '"+userEmail+"'");

            while (results.next()){
                admin.setId(results.getInt("id"));
                admin.setEmail(results.getString("email"));
                admin.setPassword(results.getString("password"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return admin;
    }
}
