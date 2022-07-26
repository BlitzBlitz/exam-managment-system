package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;

public class StudentDashboardController {
    @FXML
    GridPane cardGrid;
    @FXML
    Label loc;
    @FXML
    Label category;

    Student student;
    Course course;

    public void initialize() throws SQLException, IOException {
        showCourses();
    }

    public StudentDashboardController() {
        this.student = StudentRepository.getStudentByEmail(LoginController.getLoggedInEmail());
    }

    public void handleOnLogout(ActionEvent actionEvent) throws IOException {
        LoginController.handleOnLogout((Stage)((Button)actionEvent.getTarget()).getScene().getWindow());
    }
    public void showCourses() throws IOException, SQLException {
        category.setText("Courses");
        loc.setText("/home/courses");

        ObservableList<Node> cards = cardGrid.getChildren();
        cards.removeAll(cards);

        ArrayList<Course> courses = CourseRepository.getAllCourses(student);
        int column = 0, row = 0;
        for(int i = 0; i< courses.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
            AnchorPane anchorPane = loader.load();
            CourseCardController courseCardController = loader.getController();
            courseCardController.setTitle(courses.get(i).getName());
            courseCardController.setOnClickListener(cardTitle -> {
                displayExamsForCourse(cardTitle);
            });
            cardGrid.add(anchorPane,column++,row);
            if (column == 3) {
                column = 0;
                row++;
            }
        }

    }

    private void displayExamsForCourse(String courseTitle) throws IOException, SQLException {
        loc.setText("/home/courses/"+courseTitle);
        category.setText(courseTitle.toUpperCase());
        course = CourseRepository.getCourseByName(courseTitle);

        ObservableList<Node> cards = cardGrid.getChildren();
        cards.removeAll(cards);
        ArrayList<Exam> exams = ExamRepository.getAllUndoneExamsForCourse(course.getId(), student.getId());
        int column = 0, row = 0;

        for(int i = 0; i< exams.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
            AnchorPane anchorPane = loader.load();
            CourseCardController courseCardController = loader.getController();
            courseCardController.setTitle(exams.get(i).getTitle());
            courseCardController.setImage(new Image(HelloApplication.class.
                    getResource("images/icons/exam.png").toString()));
            int finalI = i;
            courseCardController.setOnClickListener((event) -> {
//                showQuestionsForExam(exams.get(finalI));
            });
            cardGrid.add(anchorPane,column++,row);
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    public void handleShowCourses(ActionEvent actionEvent) throws SQLException, IOException {
        showCourses();
    }

    public void handleEditPersonalInfo() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        scene.setUserData("student");
        stage.setTitle("Edit Personal Info!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
