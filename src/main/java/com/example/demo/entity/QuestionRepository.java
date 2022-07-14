package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuestionRepository {


    public static void createQuestionTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS question" +
                "(id INTEGER PRIMARY KEY ASC, title TEXT, answer BOOLEAN, exam_id INTEGER, FOREIGN KEY(exam_id) REFERENCES exam(id))");
        statement.close();
    }

    public static void insertQuestion(Question question) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO question(title, answer, exam_id) VALUES ( '"  + question.getTitle() + "' , "+ question.getAnswer() +" , " + question.getExamId() + " )");
        ResultSet resultSet = statement.executeQuery("SELECT id from question ORDER BY id DESC LIMIT 1");
        int id = -1;
        id = resultSet.getInt("id");

        if(id != -1){
            question.setId(id);
        }else {
            throw new SQLException("Question insertion went wrong!");
        }
        statement.close();
    }
    public static ArrayList<Question> getAllQuestionsForExam(int examId) throws SQLException{
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM question WHERE exam_id = " + examId);
        ArrayList<Question> questions = new ArrayList<>();
        while (results.next()){
            Question question = new Question();
            question.setId(results.getInt("id"));
            question.setTitle(results.getString("title"));
            question.setAnswer(results.getBoolean("answer"));
            question.setExamId(results.getInt("exam_id"));
            questions.add(question);
        }
        statement.close();
        return questions;

    }
}
