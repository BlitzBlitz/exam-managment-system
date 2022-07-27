package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDashboardController {
    @FXML
    Label loc;
    @FXML
    Label category;
    @FXML
    VBox dashArea;

    GridPane cardGrid;
    HBox dashContent;
    HBox chatContainer;

    Student student;
    Course course;

    public void initialize() throws SQLException, IOException {
        showCourses();
    }

    public StudentDashboardController() {
        this.student = StudentRepository.getStudentByEmail(LoginController.getLoggedInEmail());
    }

    public ChatController switchToChatUI() throws IOException {
        dashArea.getChildren().remove(dashContent);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("chat.fxml"));
        HBox chatConatiner = loader.load();
        dashArea.getChildren().add(chatConatiner);
        category.setText("Messages");
        loc.setText("/home/messages");
        return loader.getController();
    }
    public void handleOnShowChat() throws IOException {
        if(category.getText().compareTo("Messages") != 0){
            ChatController chatController = switchToChatUI();
            chatContainer = chatController.getChatContainer();
        }

    }

    public void handleOnLogout(ActionEvent actionEvent) throws IOException {
        LoginController.handleOnLogout((Stage)((Button)actionEvent.getTarget()).getScene().getWindow());
    }
    public void showCourses() throws IOException, SQLException {

        if(category.getText().compareTo("Messages") == 0 || category.getText().compareTo("HOME") == 0){
            switchToCourseUI();
        }


        ObservableList<Node> cards = cardGrid.getChildren();
        cards.removeAll(cards);

        ArrayList<Course> courses = CourseRepository.getAllCourses(student);
        int column = 0, row = 0;
        for(int i = 0; i< courses.size();i++){
            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
            AnchorPane anchorPane = loader1.load();
            CourseCardController courseCardController = loader1.getController();
            courseCardController.setTitle(courses.get(i).getName());
            courseCardController.setOnClickListener(cardTitle -> {
                displayExamsForCourse(cardTitle);
            });
            cardGrid.add(anchorPane,column++,row);
            if (column == 3) {
                column = 0;
                row++;
            }
        }

    }

    private void switchToCourseUI() throws IOException {
        dashArea.getChildren().remove(chatContainer);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("dashGrid.fxml"));
        HBox cardContainer = loader.load();
        dashArea.getChildren().add(cardContainer);
        DashGridController dashGridController = loader.getController();
        this.cardGrid = dashGridController.getCardGrid();
        this.dashContent = dashGridController.getDashContent();
        category.setText("Courses");
        loc.setText("/home/courses");
    }

    private void displayExamsForCourse(String courseTitle) throws IOException, SQLException {
        loc.setText("/home/courses/"+courseTitle);
        category.setText(courseTitle.toUpperCase());
        course = CourseRepository.getCourseByName(courseTitle);

        ObservableList<Node> cards = cardGrid.getChildren();
        cards.removeAll(cards);
        ArrayList<Exam> exams = ExamRepository.getAllUndoneExamsForCourse(course.getId(), student.getId());
        int column = 0, row = 0;

        for(int i = 0; i< exams.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
            AnchorPane anchorPane = loader.load();
            CourseCardController courseCardController = loader.getController();
            courseCardController.setTitle(exams.get(i).getTitle());
            courseCardController.setImage(new Image(HelloApplication.class.
                    getResource("images/icons/exam.png").toString()));
            int finalI = i;
            courseCardController.setOnClickListener((event) -> {
                showTakeExam(exams.get(finalI));
            });
            cardGrid.add(anchorPane,column++,row);
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    private void showTakeExam(Exam exam) throws IOException, SQLException {
        ArrayList<Question> questions = QuestionRepository.getAllQuestionsForExam(exam.getId());
        if(questions.size() == 0){
            AlertController.showAlert("No question for this exam", "No Question", Alert.AlertType.ERROR);
            return;
        }
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("answerQuestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 350);
        stage.setTitle("Exam " + exam.getTitle());
        AnswerQuestionsController answerQuestionsController = fxmlLoader.getController();
        answerQuestionsController.setUp(questions, student,exam);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        displayExamsForCourse(course.getName());
    }

    public void handleShowCourses(ActionEvent actionEvent) throws SQLException, IOException {
        showCourses();
    }

    public void handleEditPersonalInfo() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        scene.setUserData("student");
        stage.setTitle("Edit Personal Info!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void handleShowResults(ActionEvent actionEvent) {
        try {
            ArrayList<Result> results = ResultRepository.getAllStudentResults(student);
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("studentResults.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 350);
            StudentResultsController studentResultsController = fxmlLoader.getController();
            studentResultsController.setUp(results);
            stage.setTitle("Your Results");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
