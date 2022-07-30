package com.example.demo.controller;

import com.example.demo.entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AnswerQuestionsController {

    @FXML
    Button previousBtn;
    @FXML
    Button nextBtn;
    @FXML
    Label questionLabel;
    @FXML
    RadioButton correctRadio;

    Student student;
    Exam exam;

    ArrayList<Question> questions;
    int totalNumberOfQuestions;
    int currentQuestionIndex;
    ArrayList<Boolean> answers = new ArrayList<>();

    public void setUp(ArrayList<Question> questions, Student student, Exam exam){
        this.questions = questions;
        this.student = student;
        this.exam = exam;
        totalNumberOfQuestions = questions.size();
        currentQuestionIndex = 0;
        questionLabel.setText(questions.get(currentQuestionIndex).getTitle());
        answers.addAll(questions.stream().map(question -> true).collect(Collectors.toCollection(ArrayList::new)));
    }


    public void handleOnNext() {
        answers.set(currentQuestionIndex,correctRadio.isSelected());
        if(currentQuestionIndex <= totalNumberOfQuestions){
            currentQuestionIndex++;
        }
        previousBtn.setDisable(currentQuestionIndex == 0);
        nextBtn.setDisable(currentQuestionIndex == totalNumberOfQuestions);
        if(currentQuestionIndex >= totalNumberOfQuestions){
            questionLabel.setText("Finish");
            nextBtn.setDisable(false);
            nextBtn.setText("Show result");
            nextBtn.setOnAction(actionEvent -> {
                int points = calculateResult();
                AlertController.showAlert("Your Score is: " + points + "/" + totalNumberOfQuestions, "Score", Alert.AlertType.INFORMATION);
                Result result = new Result(student,exam,points/totalNumberOfQuestions);
                try {
                    ResultRepository.insertResult(result);
                    closePopUp(actionEvent);
                } catch (SQLException e) {
                    AlertController.showAlert("Result not saved!", "Error saving the result", Alert.AlertType.ERROR);
                }
            });
        }else{
            questionLabel.setText(currentQuestionIndex + ". "+ questions.get(currentQuestionIndex).getTitle());
        }
    }

    private void closePopUp(ActionEvent actionEvent) {
        ((Stage)((Button)actionEvent.getTarget()).getScene().getWindow()).close();
    }

    private int calculateResult() {
        int points = 0;
        for(int i = 0; i < answers.size(); i++){
            if(answers.get(i) == questions.get(i).getAnswer()){
                points++;
            }
        }
        return points;
    }

    public void handleOnPrevious(ActionEvent actionEvent) {
        if(currentQuestionIndex > 0){
            currentQuestionIndex--;
        }
        previousBtn.setDisable(currentQuestionIndex == 0);
        nextBtn.setDisable(currentQuestionIndex == totalNumberOfQuestions);
        if(currentQuestionIndex < totalNumberOfQuestions){
            questionLabel.setText(currentQuestionIndex + ". "+ questions.get(currentQuestionIndex).getTitle());
            nextBtn.setDisable(false);
            nextBtn.setText("Next");
            nextBtn.setOnAction((event)->{
                handleOnNext();
            });
        }
    }
}
