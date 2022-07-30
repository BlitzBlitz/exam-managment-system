package com.example.demo.entity;

import com.example.demo.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentRepository {
    public static void createStudentTable() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS student " +
                "(id INTEGER PRIMARY KEY ASC, email TEXT UNIQUE, password TEXT, name TEXT, lastname TEXT, phone TEXT)");
        statement.close();
    }

    public static void insertStudent(Student student) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        statement.execute("INSERT INTO student(email, password, name, lastname, phone) VALUES ( '"
                + student.getEmail() + "' , '" + student.getPassword() +  "' , '" + student.getName() +
                "' , '" + student.getLastname() + "' , '" + student.getPhoneNumber() +"')");
        ResultSet resultSet = statement.executeQuery("SELECT id from student where email = '" + student.getEmail()+"'");
        int id = -1;
        id = resultSet.getInt("id");

        if(id != -1){
            student.setId(id);
        }else {
            throw new SQLException("Student insertion went wrong!");
        }
        statement.close();
    }

    public static ArrayList<Student> getAllStudents() throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM student");
        ArrayList<Student> students = new ArrayList<>();
        while (results.next()){
            Student student = getStudentFromResults(results);
            students.add(student);
        }
        statement.close();
        return students;
    }

    private static Student getStudentFromResults(ResultSet results) throws SQLException {
        Student student = new Student();
        student.setId(results.getInt("id"));
        student.setEmail(results.getString("email"));
        student.setPassword(results.getString("password"));
        student.setName(results.getString("name"));
        student.setLastname(results.getString("lastname"));
        student.setPhoneNumber(results.getString("phone"));
        return student;
    }

    public static Student getStudentByEmail(String userEmail) {
        Student student = new Student();
        try {
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM student WHERE email = '"+userEmail+"'");
            student = getStudentFromResults(results);
            statement.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return student;
    }

    public static ArrayList<Student> getStudentsByAnyField(String keyword) throws SQLException {
        ArrayList<Student> students = new ArrayList<>();

        Statement statement = DbConnection.getConnection().createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM student WHERE ((id LIKE '%" +
                keyword +"%') OR (name LIKE '%"+keyword+"%') OR " +
                "(lastname LIKE '%"+keyword+"%') OR (phone LIKE  '%"+keyword+"%') " +
                "OR (email LIKE '%"+keyword+"%'))");

        while (results.next()){
            Student student = new Student();
            convertFromResult(student, results);
            students.add(student);
        }
        statement.close();
        return students;
    }
    private static void convertFromResult(Student student, ResultSet results) throws SQLException {
        student.setId(results.getInt("id"));
        student.setEmail(results.getString("email"));
        student.setPassword(results.getString("password"));
        student.setName(results.getString("name"));
        student.setLastname(results.getString("lastname"));
        student.setPhoneNumber(results.getString("phone"));
    }

    public static void addStudent(String email, String name, String lastname, String phone) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        int count = statement.executeUpdate("INSERT INTO student(email,password, name,lastname,phone) VALUES ('"+ email+
                "' , '"+ phone+"' , '"+ name+"' , '"+ lastname+"' , '"+ phone+"')");
        if(count <= 0){
            statement.close();
            throw new SQLException("Insertion went wrong");
        }
        statement.close();
    }

    public static void updateStudent(User student) throws SQLException {
        Statement statement = DbConnection.getConnection().createStatement();
        int count = statement.executeUpdate("UPDATE student SET password = '"+ student.getPassword()+
                "' , name = '"+ student.getName()+"' , lastname = '"+ student.getLastname()+"' , phone = '"+
                student.getPhoneNumber()+"' WHERE email = '" + student.getEmail() + "'");
        statement.close();
        if(count <= 0){
            throw new SQLException("Updating went wrong");
        }
    }

    public static Student getStudentByName(String studentName) {
        Student student = new Student();
        try {
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM student WHERE name = '"+studentName+"'");
            student = getStudentFromResults(results);
            statement.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return student;
    }
}
