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

            Teacher teacher = new Teacher("1244413","1","1","1", "1");
            TeacherRepository.insertTeacher(teacher);
            Course course = new Course("English11", teacher.getId());
            CourseRepository.insertCourse(course);
            Exam exam = new Exam("English01-mid", course.getId());
            ExamRepository.insertExam(exam);
            Question question = new Question("The English language has 23 letters", false, exam.getId());
            QuestionRepository.insertQuestion(question);
//            Student student = new Student("122544445533","1","1","1", "1");
//            StudentRepository.insertStudent(student);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
       conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kleme\\IdeaProjects\\demo\\db\\javatest.db");
       return conn;
    }


}
