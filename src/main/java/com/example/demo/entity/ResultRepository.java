package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ResultRepository {
    public static void createResultTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS result" +
                "(id INTEGER PRIMARY KEY ASC, student_id INTEGER, exam_id INTEGER, result INTEGER, date TEXT, " +
                "FOREIGN KEY(student_id) REFERENCES student(id), FOREIGN KEY(exam_id) REFERENCES exam(id))");
        statement.close();
    }
    public static void insertResult(Result result) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO result(student_id, exam_id, result, date) " +
                "VALUES ( '"  + result.getStudent().getId() + "', '"  + result.getExam().getId() +
                "', '"  + result.getResult() + "', '"  + result.getDate() + "' )");
        ResultSet resultSet = statement.executeQuery("SELECT id from result ORDER BY id DESC LIMIT 1");
        int id = -1;
        id = resultSet.getInt("id");
        if(id != -1){
            result.setId(id);
        }else {
            throw new SQLException("Question insertion went wrong!");
        }
        statement.close();
    }
    private static ArrayList<Result> getAllStudentResults(Student student) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM result WHERE student_id =" +
                student.getId());
        ArrayList<Result> results = new ArrayList<>();
        while (resultSet.next()){
            Result result = new Result();
            result.setId(resultSet.getInt("id"));
            result.setResult(resultSet.getInt("result"));
            

            results.add(result);
        }
        statement.close();
        return results;
    }
}
