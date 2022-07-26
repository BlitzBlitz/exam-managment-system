package com.example.demo.controller;

import com.example.demo.entity.StudentRepository;
import com.example.demo.entity.TeacherRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddUserController {

    @FXML
    TextField emailField;
    @FXML
    TextField nameField;
    @FXML
    TextField lastnameField;
    @FXML
    TextField phoneField;
    @FXML
    RadioButton teacherRadio;
    @FXML
    RadioButton studentRadio;


    public void handleOnClean() {
        emailField.setText("");
        nameField.setText("");
        lastnameField.setText("");
        phoneField.setText("");
    }


    public void handleOnSave(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getTarget();

        String email = emailField.getText();
        String name = nameField.getText();
        String lastname = lastnameField.getText();
        String phone = phoneField.getText();
        if(teacherRadio.isSelected()){
            try {
                TeacherRepository.addTeacher(email,name,lastname,phone);
                AlertController.showAlert("The Password is the same as the phone number.",
                        "Registration succeeded", Alert.AlertType.INFORMATION);
            }catch (SQLException e){
                AlertController.showAlert("Error occurred when inserting! Try again!","Error", Alert.AlertType.ERROR);
            }
        }else {
            try{
                StudentRepository.addStudent(email,name,lastname,phone);
                AlertController.showAlert("The Password is the same as the phone number.",
                        "Registration succeeded", Alert.AlertType.INFORMATION);
            }catch (SQLException e){
                AlertController.showAlert("Error occurred when inserting! Try again!", "Error", Alert.AlertType.ERROR);
            }
        }

        closeAddUserStage(button);
    }

    public void closeAddUserStage(Button button){
        Stage addUserStage = (Stage) button.getScene().getWindow();
        addUserStage.close();
    }


}
