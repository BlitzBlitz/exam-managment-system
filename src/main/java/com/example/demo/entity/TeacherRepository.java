package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TeacherRepository {
    public static void createTeacherTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS teacher " +
                "(id INTEGER PRIMARY KEY ASC, email TEXT UNIQUE, password TEXT, name TEXT, lastname TEXT, phone TEXT)");
        statement.close();
    }


    public static void insertTeacher(Teacher teacher) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO teacher(email, password, name, lastname, phone) VALUES ( '"
                + teacher.getEmail() + "' , '" + teacher.getPassword() +  "' , '" + teacher.getName() +
                 "' , '" + teacher.getLastname() + "' , '" + teacher.getPhoneNumber() +"')");


        ResultSet resultSet = statement.executeQuery("SELECT id from teacher where email = '"
                + teacher.getEmail()+"'");
        int id = -1;
        id = resultSet.getInt("id");
        if(id != -1){
            teacher.setId(id);
        }else {
            throw new SQLException("Student insertion went wrong!");
        }
        statement.close();
    }
    public static ArrayList<Teacher> getAllTeachers() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM teacher");
        ArrayList<Teacher> teachers = new ArrayList<>();
        while (results.next()){
            Teacher teacher = new Teacher();
            teacher.setId(results.getInt("id"));
            teacher.setEmail(results.getString("email"));
            teacher.setPassword(results.getString("password"));
            teacher.setName(results.getString("name"));
            teacher.setLastname(results.getString("lastname"));
            teacher.setPhoneNumber(results.getString("phone"));

            teachers.add(teacher);
        }
        statement.close();
        return teachers;
    }

    public static Teacher getTeacherByEmail(String userEmail) {
        Teacher teacher = new Teacher();
        try {
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM teacher WHERE email = '"+userEmail+"'");

            while (results.next()){
                teacher.setId(results.getInt("id"));
                teacher.setEmail(results.getString("email"));
                teacher.setPassword(results.getString("password"));
                teacher.setName(results.getString("name"));
                teacher.setLastname(results.getString("lastname"));
                teacher.setPhoneNumber(results.getString("phone"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return teacher;
    }
}
