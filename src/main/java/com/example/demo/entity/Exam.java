package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Exam {



    static {
        try {
            createExamTable();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int id;
    private String title;

    public Exam(String title) {
        this.title = title;
    }

    public Exam(int id, String title) {
        this.id = id;
        this.title = title;
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

    private static void createExamTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS exam" +
                "(id INTEGER PRIMARY KEY ASC, title TEXT)");
        statement.close();
    }


    public static void insertExam(Exam exam) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO exam(title) VALUES ( '"  + exam.title + "' )");
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
