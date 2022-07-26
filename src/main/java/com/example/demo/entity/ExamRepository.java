package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExamRepository {

    public static void createExamTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS exam" +
                "(id INTEGER PRIMARY KEY ASC, title TEXT, course_id INTEGER, " +
                "FOREIGN KEY(course_id) REFERENCES course(id))");
        statement.close();
    }

    public static  ArrayList<Exam> getAllExamsForCourse(String title) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT exam.id, exam.title, exam.course_id FROM exam " +
                "INNER JOIN course ON course.id = exam.course_id WHERE course.name = '" + title+"'");
        ArrayList<Exam> exams = new ArrayList<>();
        while (resultSet.next()){
            Exam exam = new Exam();
            exam.setId(resultSet.getInt("id"));
            exam.setTitle(resultSet.getString("title"));
            exam.setCourse_id(resultSet.getInt("course_id"));
            exams.add(exam);
        }
        statement.close();
        return exams;
    }

    public static  ArrayList<Question> getQuestionForExam(Exam exam) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM question WHERE exam_id =" + exam.getId());
        ArrayList<Question> questions = new ArrayList<>();
        while (resultSet.next()){
            Question question = new Question();
            question.setId(resultSet.getInt("id"));
            question.setTitle(resultSet.getString("title"));
            question.setAnswer(resultSet.getBoolean("answer"));
            question.setExamId(resultSet.getInt("exam_id"));
            questions.add(question);
        }
        statement.close();
        return questions;
    }

    public static void insertExam(Exam exam) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO exam(title, course_id) VALUES ( '"  + exam.getTitle() + "', '"  + exam.getCourse_id() +  "' )");
        ResultSet resultSet = statement.executeQuery("SELECT id from exam ORDER BY id DESC LIMIT 1");
        int id = -1;
        id = resultSet.getInt("id");
        if(id != -1){
            exam.setId(id);
        }else {
            throw new SQLException("Question insertion went wrong!");
        }
        statement.close();
    }


}
