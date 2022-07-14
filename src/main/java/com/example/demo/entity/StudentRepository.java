package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentRepository {
    public static void createStudentTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS student " +
                "(id INTEGER PRIMARY KEY ASC, email TEXT UNIQUE, password TEXT, name TEXT, lastname TEXT, phone TEXT)");
        statement.close();
    }



    public static void insertStudent(Student student) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO student(email, password, name, lastname, phone) VALUES ( '"
                + student.getEmail() + "' , '" + student.getPassword() +  "' , '" + student.getName() +
                "' , '" + student.getLastname() + "' , '" + student.getPhoneNumber() +"')");
        ResultSet resultSet = statement.executeQuery("SELECT id from student where email = '" + student.getEmail()+"'");
        int id = -1;
        id = resultSet.getInt("id");

        if(id != -1){
            student.setId(id);
        }else {
            throw new SQLException("Student insertion went wrong!");
        }
        statement.close();
    }

}
