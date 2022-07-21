package com.example.demo.controller;

import javafx.scene.control.Alert;

public class AlertController {
    public static void showAlert(String message, String title, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
