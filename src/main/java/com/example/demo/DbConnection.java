package com.example.demo;
import com.example.demo.entity.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbConnection {
    private static Connection conn;
    public static void main(String[] args) {
        try{
            getConnection();
            TeacherRepository.createTeacherTable();
            CourseRepository.createCourseTable();
            ExamRepository.createExamTable();
            QuestionRepository.createQuestionTable();
            StudentRepository.createStudentTable();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
       conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kleme\\IdeaProjects\\demo\\db\\javatest.db");
       return conn;
    }


}
