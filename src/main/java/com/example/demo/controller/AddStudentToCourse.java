package com.example.demo.controller;

import com.example.demo.entity.CourseRepository;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class AddStudentToCourse {
    @FXML
    TextField searchField;
    @FXML
    VBox studentsContainer;


    public void handleOnSearch() throws SQLException {
        studentsContainer.getChildren().removeAll(studentsContainer.getChildren());
        String courseName = getCourseName();
        String keyWord= searchField.getText();
        ArrayList<Student> students = StudentRepository.getStudentsByAnyField(keyWord);
        students.forEach(student -> {
            Button button = getButton(courseName, student);
            studentsContainer.getChildren().add(button);
        });

    }

    private Button getButton(String courseName, Student student) {
        Button button = new Button(student.getName()+" " + student.getLastname()
                + " | " + student.getEmail());
        button.setPrefWidth(330);
        button.setPadding(new Insets(10));
        button.setStyle("-fx-background-color: #274690");
        button.setTextFill(Color.WHITE);
        button.setOnAction((ActionEvent event)->{
            showConfirmation(student.getName(), courseName).ifPresent((btnType) -> {
                if(btnType == ButtonType.OK){
                    addStudentToCourse(student.getName(), courseName);
                }
            });

        });
        return button;
    }

    private void addStudentToCourse(String studentName, String courseName){
        try {
            CourseRepository.addStudentToCourse(studentName, courseName);
        } catch (SQLException e) {
             if(e.getMessage().contains("registered")){
                AlertController.showAlert("Student already registered!",
                        "Student can not be added", Alert.AlertType.WARNING);
            }else {
                 AlertController.showAlert("Please try again!",
                         "Error while adding student to course", Alert.AlertType.ERROR);
             }

        }
    }

    private String getCourseName() {
        return (String) searchField.getScene().getUserData();
    }

    private Optional<ButtonType> showConfirmation(String studentName, String courseName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add student to Course");
        alert.setHeaderText("Do you want to add "+ studentName +" to course: "+ courseName +"?");
        return alert.showAndWait();
    }
}
