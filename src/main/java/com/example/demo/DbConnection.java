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


            TeacherRepository.insertTeacher(new Teacher("1","1","1","1", "1"));
            CourseRepository.insertCourse(new Course("English101", 1));
            ExamRepository.insertExam(new Exam("English101-mid", 1));
            QuestionRepository.insertQuestion(new Question("The English language has 23 letters", false, 1));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
       conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kleme\\IdeaProjects\\demo\\db\\javatest.db");
       return conn;
    }


}
