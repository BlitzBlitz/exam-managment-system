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
                "(student_id INTEGER, exam_id INTEGER, result REAL," +
                "PRIMARY KEY (student_id, exam_id), " +
                "FOREIGN KEY(student_id) REFERENCES student(id), FOREIGN KEY(exam_id) REFERENCES exam(id))");
        statement.close();
    }
    public static void insertResult(Result result) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO result(student_id, exam_id, result) " +
                "VALUES ( '"  + result.getStudent().getId() + "', '"  + result.getExam().getId() +
                "', "  + result.getResult()+")");
        statement.close();
    }
    public static ArrayList<Result> getAllStudentResults(Student student) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT result, title FROM result inner join exam " +
                "on result.exam_id = exam.id WHERE student_id =" +
                student.getId());
        ArrayList<Result> results = new ArrayList<>();
        while (resultSet.next()){
            Result result = new Result();
            result.setResult(resultSet.getDouble("result"));
            result.setExam(new Exam(resultSet.getString("title")));
            results.add(result);
        }
        statement.close();
        return results;
    }
}
