package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class TeacherRepository {
    public static void createTeacherTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS teacher " +
                "(id INTEGER PRIMARY KEY ASC, email TEXT, password TEXT, name TEXT, lastname TEXT, phone TEXT)");
        statement.close();
    }


    public static void insertTeacher(Teacher teacher) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO teacher(email, password, name, lastname, phone) VALUES ( '"
                + teacher.getEmail() + "' , '" + teacher.getPassword() +  "' , '" + teacher.getName() +
                 "' , '" + teacher.getLastname() + "' , '" + teacher.getPhoneNumber() +"')");
        statement.close();

    }
}
