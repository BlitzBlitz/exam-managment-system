package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class CourseRepository {

    public static void createCourseTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS course " +
                "(id INTEGER PRIMARY KEY ASC, name TEXT, created_by INTEGER, " +
                "FOREIGN KEY(created_by) REFERENCES teacher(id))");
        statement.close();
    }



    public static void insertCourse(Course course) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO course(name, created_by) VALUES ( '"  + course.getName()
                + "', ' "+ course.getCreatedBy()+" ' )");
        statement.close();
    }

    public void addExamToCourse(Course course, Exam exam) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO course_content(course_id, exam_id) VALUES ( '"  +
                course.getId() + ", " + exam.getId() + "' )");
        statement.close();
    }

}
