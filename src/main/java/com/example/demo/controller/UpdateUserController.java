package com.example.demo.controller;

import com.example.demo.entity.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UpdateUserController {
    @FXML
    TextField emailField;
    @FXML
    TextField nameField;
    @FXML
    TextField lastnameField;
    @FXML
    TextField phoneField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField confirmPasswordField;

    User user;


    @FXML
    public void initialize(){
        Platform.runLater(() -> {
            loadData(getCategory());
        });

    }

    private void loadData(String category) {
        if(category.compareTo("teacher") == 0){
            user = TeacherRepository.getTeacherByEmail(LoginController.getLoggedInEmail());
        }else {
            user = StudentRepository.getStudentByEmail(LoginController.getLoggedInEmail());
        }
        emailField.setText(user.getEmail());
        nameField.setText(user.getName());
        lastnameField.setText(user.getLastname());
        phoneField.setText(user.getPhoneNumber());
        passwordField.setText(user.getPassword());
        confirmPasswordField.setText(user.getPassword());

    }

    public void handleOnClean(ActionEvent actionEvent) {
        nameField.setText("");
        lastnameField.setText("");
        phoneField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    public void handleOnUpdate(ActionEvent actionEvent) {
        if(nameField.getText().isBlank() || lastnameField.getText().isBlank() ||phoneField.getText().isBlank()
                ||passwordField.getText().isBlank() ||confirmPasswordField.getText().isBlank() ){
            AlertController.showAlert("Complete all you data!", "Error", Alert.AlertType.ERROR);
            return;
        }
        if(passwordField.getText().compareTo(confirmPasswordField.getText()) != 0){
            AlertController.showAlert("Passwords must match!", "Error", Alert.AlertType.ERROR);
            return;
        }
        user.setName(nameField.getText());
        user.setLastname(lastnameField.getText());
        user.setPassword(passwordField.getText());
        user.setPhoneNumber(phoneField.getText());

        try {
            if(getCategory().compareTo("teacher") == 0) {
                TeacherRepository.updateTeacher(user);
                closeWindow();
            }else {
                StudentRepository.updateStudent(user);
                closeWindow();
            }
        }catch (SQLException e){
            AlertController.showAlert("Error occurred while updating. Try again!", "Error",
                    Alert.AlertType.ERROR);
        }


    }
    public String getCategory(){
        return (String)emailField.getScene().getUserData();
    }
    public void closeWindow(){
        ((Stage)emailField.getScene().getWindow()).close();
    }
}
