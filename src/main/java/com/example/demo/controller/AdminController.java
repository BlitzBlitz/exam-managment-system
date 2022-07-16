package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminController {
    @FXML
    Label loc;
    @FXML
    Label category;


    public void displayTeachers(ActionEvent actionEvent) {
        loc.setText("/home/teachers");
        category.setText("Teachers");
    }

    public void displayStudents(ActionEvent actionEvent) {
        loc.setText("/home/students");
        category.setText("Students");
    }
}
