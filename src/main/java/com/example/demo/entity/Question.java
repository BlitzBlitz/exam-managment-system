package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Question {
    static {
        try {
            createQuestionTable();
//            Exam.insertExam(new Exam("English101"));
            insertQuestion(new Question("Is sun a star?" , true,Exam.getIdByTitle("English101")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private int id;
    private String title;
    private Boolean answer;
    private int examId;

    public static void main(String[] args) {

    }

    public Question() {
    }

    public Question(String title, Boolean answer, int examId) {
        this.title = title;
        this.answer = answer;
        this.examId = examId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    private static void createQuestionTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS question" +
                "(id INTEGER PRIMARY KEY ASC, title TEXT, answer BOOLEAN, exam_id INTEGER, FOREIGN KEY(exam_id) REFERENCES exam(id))");
        statement.close();
    }

    public static void insertQuestion(Question question) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO question(title, answer, exam_id) VALUES ( '"  + question.title + "' , "+ question.answer +" , " + question.examId + " )");
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
