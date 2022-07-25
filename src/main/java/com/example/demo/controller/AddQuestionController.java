package com.example.demo.controller;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import com.example.demo.entity.QuestionRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddQuestionController {
    @FXML
    TextField questionField;
    @FXML
    RadioButton correctRadio;

    Exam exam;

    public void setUp(Exam exam){
        this.exam = exam;
    }

    public void handleOnAdd(ActionEvent actionEvent) throws SQLException {
        String question = questionField.getText();
        boolean answer = correctRadio.isSelected();
        QuestionRepository.addQuestion(new Question(question,answer,exam.getId()));
        //TODO go back to previous scene
        ((Stage)questionField.getScene().getWindow()).close();
    }
}
