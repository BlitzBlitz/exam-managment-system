package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class DashGridController {
    @FXML
    GridPane cardGrid;
    @FXML
    HBox dashContent;

    public HBox getDashContent() {
        return dashContent;
    }

    public GridPane getCardGrid(){
        return cardGrid;
    }
}
