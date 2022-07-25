package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    Button loginBtn;
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    Label messageLabel;
    @FXML
    RadioButton adminRadioBtn;
    @FXML
    RadioButton teacherRadioBtn;
    @FXML
    RadioButton studentRadioBtn;

    private static String loggedInEmail;

    public void onLogin() throws IOException {
        String userEmail = email.getText();
        String userPassword = password.getText();

        if (userEmail.isBlank() || userPassword.isBlank()){
            displayErrorMessage("Enter email and password!");
        } else {
            if(adminRadioBtn.isSelected()){
                Admin admin = AdminRepository.getAdminByEmail(userEmail);
                if(admin == null || admin.getPassword().compareTo(userPassword) != 0){
                    displayErrorMessage("Enter email and password!");
                }else {
                    loggedInEmail = userEmail;
                    Parent root  = FXMLLoader.load(HelloApplication.class.getResource("admin.fxml"));
                    Stage mainStage = (Stage) loginBtn.getScene().getWindow();
                    mainStage.setTitle("Admin Dashboard");
                    mainStage.setScene(new Scene(root, 700,500));

                }
            }else if(teacherRadioBtn.isSelected()){
                Teacher teacher = TeacherRepository.getTeacherByEmail(userEmail);
                if(teacher == null || teacher.getPassword().compareTo(userPassword) != 0){
                    displayErrorMessage("Enter email and password!");
                }else {
                    loggedInEmail = userEmail;
                    Parent root  = FXMLLoader.load(HelloApplication.class.getResource("teacher.fxml"));
                    Stage mainStage = (Stage) loginBtn.getScene().getWindow();
                    mainStage.setTitle("Admin Dashboard");
                    mainStage.setScene(new Scene(root, 700,500));

                }
            }else if(studentRadioBtn.isSelected()){
                Student student = StudentRepository.getStudentByEmail(userEmail);
                if(student == null && student.getPassword().compareTo(userPassword) == 0){
                    displayErrorMessage("Enter email and password!");
                }else {

                    //TODO go to student dashboard

                }
            }

        }
    }
    public void displayErrorMessage(String message){
        messageLabel.setText(message);
        messageLabel.setTextFill(Color.RED);
    }
    public static void handleOnLogout(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }
    public static String getLoggedInEmail() {
        return loggedInEmail;
    }
}


