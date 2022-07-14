package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExamRepository {
    static {
        try {
            ExamRepository.createExamTable();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createExamTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS exam" +
                "(id INTEGER PRIMARY KEY ASC, title TEXT, course_id INTEGER, " +
                "FOREIGN KEY(course_id) REFERENCES course(id))");
        statement.close();
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
    public static int getIdByTitle(String title) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM exam WHERE title = '"  + title + "'");
        int id = -1;
        while (result.next()){
            id = result.getInt("id");
        }
        statement.close();
        System.out.println(id);
        return id;
    }
}
