package com.example.demo.controller;

import com.example.demo.entity.Result;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class StudentResultsController {
    @FXML
    VBox resultsContainer;

    public void setUp(ArrayList<Result> results){
        results.forEach(result -> {
            resultsContainer.getChildren().add(getResultRow(result));
        });
    }

    private HBox getResultRow(Result result) {
        HBox resultRow = new HBox();
        resultRow.setPrefWidth(330);
        resultRow.setPrefHeight(40);

        resultRow.getChildren().add(getLabel(result.getExam().getTitle(), 200));
        resultRow.getChildren().add(getLabel(result.getResult()+"", 70));
        return resultRow;
    }

    private Label getLabel(String title, int width) {
        Label examTitleLabel = new Label(title);
        examTitleLabel.setPrefWidth(width);
        examTitleLabel.setPadding(new Insets(10));
        examTitleLabel.setAlignment(Pos.CENTER);
        examTitleLabel.setTextFill(Color.WHITE);
        examTitleLabel.setBackground(new Background(new BackgroundFill
                (Color.rgb(0, 0, 80, 0.8),
                        new CornerRadii(0.0), new Insets(-5.0))));
        examTitleLabel.setFont(new Font("System Bold", 14));
        return examTitleLabel;
    }
}
