package com.example.demo.controller;

import com.example.demo.HelloApplication;

import com.example.demo.entity.Course;
import com.example.demo.entity.CourseRepository;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.TeacherRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

    private Teacher teacher;

    public TeacherController(){
        this.teacher =  TeacherRepository.getTeacherByEmail(LoginController.getLoggedInEmail());
    }

    public void initialize() throws IOException, SQLException {
        showCourses();

    }

    public void showCourses() throws IOException, SQLException {

        ArrayList<Course> courses = CourseRepository.getAllCourses(teacher);
        addEditBtn();
        int column = 1, row = 0;
        for(int i = 0; i< courses.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
            AnchorPane anchorPane = loader.load();
            CourseCardController courseCardController = loader.getController();
            courseCardController.setTitle(courses.get(i).getName());
            cardGrid.add(anchorPane,column++,row);
            if (column == 4) {
                column = 0;
                row++;
            }
        }

    }

    private void addEditBtn() throws IOException {
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
