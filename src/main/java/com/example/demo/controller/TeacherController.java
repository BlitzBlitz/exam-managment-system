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

public class TeacherController {
    @FXML
    GridPane cardGrid;
    @FXML
    Label loc;
    @FXML
    Label category;

    private Teacher teacher;


    public TeacherController(){
        this.teacher =  TeacherRepository.getTeacherByEmail(LoginController.getLoggedInEmail());

    }

    public void initialize() throws IOException, SQLException {
        showCourses();

    }


    public void showCourses() throws IOException, SQLException {
        category.setText("Courses");
        loc.setText("/home/courses");

        ObservableList<Node> cards = cardGrid.getChildren();
        cards.removeAll(cards);

        ArrayList<Course> courses = CourseRepository.getAllCourses(teacher);
        addAddBtn();
        int column = 1, row = 0;
        for(int i = 0; i< courses.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
            AnchorPane anchorPane = loader.load();
            CourseCardController courseCardController = loader.getController();
            courseCardController.setTitle(courses.get(i).getName());
            courseCardController.setOnClickListener(cardTitle -> {
                if(cardTitle.compareTo("ADD") == 0){
                    System.out.println("displaing add");

                    //TODO add course pop-up
                }else {
                    displayExamsForCourse(cardTitle);
                }
            });
            cardGrid.add(anchorPane,column++,row);
            if (column == 4) {
                column = 0;
                row++;
            }
        }

    }

    private void displayExamsForCourse(String courseTitle) throws IOException, SQLException {
        loc.setText("/home/courses/"+courseTitle);
        category.setText(courseTitle.toUpperCase());
        ObservableList<Node> cards = cardGrid.getChildren();
        cards.removeAll(cards);
        ArrayList<Exam> exams = ExamRepository.getAllExamsForCourse(courseTitle);
        addAddBtn();
        int column = 1, row = 0;
        for(int i = 0; i< exams.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
            AnchorPane anchorPane = loader.load();
            CourseCardController courseCardController = loader.getController();
            courseCardController.setTitle(exams.get(i).getTitle());
            courseCardController.setImage(new Image(HelloApplication.class.
                    getResource("images/icons/exam.png").toString()));
            cardGrid.add(anchorPane,column++,row);
            if (column == 4) {
                column = 0;
                row++;
            }
        }
    }

    private void addAddBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
        AnchorPane anchorPane = loader.load();
        CourseCardController courseCardController = loader.getController();
        courseCardController.setTitle("ADD");
        courseCardController.setImage(new Image(HelloApplication.class.
                getResource("images/icons/add.png").toString()));
        cardGrid.add(anchorPane,0,0);
    }

    public void handleEditPersonalInfo() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        scene.setUserData("teacher");
        stage.setTitle("Edit Personal Info!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public void handleOnLogout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getTarget()).getScene().getWindow();
        LoginController.handleOnLogout(stage);
    }
}
