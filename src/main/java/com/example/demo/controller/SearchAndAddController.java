package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchAndAddController {
    @FXML
    TextField searchField;
    @FXML
    VBox dataContainer;
    @FXML
    Label titleLabel;
    @FXML
    Button addBtn;

    ArrayList<Student> students;
    Course course;
    ArrayList<Question> questions;
    Exam exam;

    public void setUp(Course course) {
        try {
            students = CourseRepository.getStudentsForCourse(course);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.course = course;
        setTitleLabel("Students of " + course.getName());
        addStudents(students);
    }
    public void setUp(Exam exam) {
        try {
            questions = QuestionRepository.getAllQuestionsForExam(exam.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.exam = exam;
        setTitleLabel("Questions of " + exam.getTitle());
        addQuestions(questions);
        this.addBtn.setOnAction((event)->{
            Button button = (Button) event.getTarget();
            FXMLLoader loader = new FXMLLoader();
            Stage stage = (Stage) button.getScene().getWindow();
            loader.setLocation(HelloApplication.class.getResource("addQuestion.fxml"));
            Scene addScene = null;
            try {
                addScene = new Scene(loader.load(), 350,350);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            stage.setScene(addScene);
        });
    }

    private void addQuestions(ArrayList<Question> questions) {
        this.questions = questions;
        if(questions.size() != 0){
            cleanContainer();
        }
        questions.forEach(question -> {
            Label label = getQuestionLabel(question);
            dataContainer.getChildren().add(label);
        });
    }

    private Label getQuestionLabel(Question question) {
        Label label = new Label(question.getTitle());
        label.setPrefWidth(335);
        label.setPadding(new Insets(10));

        if(question.getAnswer()){
            label.setStyle("-fx-background-color: green");
        }else {
            label.setStyle("-fx-background-color: red");
        }
        return label;
    }

    public void setTitleLabel(String title){
        titleLabel.setText(title);
    }
    public void addStudents(ArrayList<Student> students){
        this.students = students;
        if(students.size() != 0){
            cleanContainer();
        }
        students.forEach(student -> {
            Label label = getLabel(student);
            dataContainer.getChildren().add(label);
        });
    }

    public void handleOnSearch(){
        cleanContainer();
        String keyWord= searchField.getText();
        if(students != null){
            students.stream().filter(student -> student.getName().contains(keyWord)).forEach(
                    student -> {
                        Label label = getLabel(student);
                        dataContainer.getChildren().add(label);
                    }
            );
        }else {
            questions.stream().filter(question -> question.getTitle().contains(keyWord)).forEach(
                    question -> {
                        Label label = getQuestionLabel(question);
                        dataContainer.getChildren().add(label);
                    }
            );
        }

    }

    private void cleanContainer() {
        dataContainer.getChildren().removeAll(dataContainer.getChildren());
    }

    public void handleOnAdd(ActionEvent actionEvent) throws IOException, SQLException {
        Button button = (Button) actionEvent.getTarget();
        FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) button.getScene().getWindow();
        loader.setLocation(HelloApplication.class.getResource("addStudentToCourse.fxml"));
        Scene addScene = new Scene(loader.load(), 350,350);
        AddStudentToCourseController addStudentToCourseController = loader.getController();
        addStudentToCourseController.setUp(course);
        stage.setScene(addScene);

    }

    private Label getLabel(Student student) {
        Label label = new Label(student.getName()+" " + student.getLastname()
                + " | " + student.getEmail());
        label.setPrefWidth(335);
        label.setPadding(new Insets(10));
        label.setStyle("-fx-background-color: #274690");
        label.setTextFill(Color.WHITE);

        return label;
    }






}
