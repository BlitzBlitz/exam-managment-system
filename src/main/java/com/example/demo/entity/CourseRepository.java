package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseRepository {

    public static void createCourseTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS course " +
                "(id INTEGER PRIMARY KEY ASC, name TEXT UNIQUE, created_by INTEGER, " +
                "FOREIGN KEY(created_by) REFERENCES teacher(id))");
        statement.execute("CREATE TABLE IF NOT EXISTS course_student " +
                "(id INTEGER PRIMARY KEY ASC, course_id INTEGER, student_id INTEGER, " +
                "FOREIGN KEY(course_id) REFERENCES course(id), " +
                "FOREIGN KEY(student_id) REFERENCES student(id))");
        statement.close();
    }



    public static void insertCourse(Course course) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO course(name, created_by) VALUES ( '"  + course.getName()
                + "', ' "+ course.getCreatedBy()+" ' )");

        ResultSet resultSet = statement.executeQuery("SELECT id from course ORDER BY id DESC LIMIT 1");
        int id = -1;
        id = resultSet.getInt("id");
        if(id != -1){
            course.setId(id);
        }else {
            throw new SQLException("Question insertion went wrong!");
        }
        statement.close();
    }
    public static void insertStudentToCourse(Student student, Course course) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO course_student(course_id, student_id) VALUES ( '"  + course.getId()
                + "', ' "+ student.getId()+" ' )");
        statement.close();
    }



}
