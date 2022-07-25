package com.example.demo.controller;

import com.example.demo.HelloApplication;

import com.example.demo.entity.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class TeacherController {
    @FXML
    GridPane cardGrid;
    @FXML
    Label loc;
    @FXML
    Label category;
    @FXML
    Button addStudentBtn;

    private Teacher teacher;
    final int numberOfColumns = 3;
    private Course course;



    public TeacherController(){
        this.teacher =  TeacherRepository.getTeacherByEmail(LoginController.getLoggedInEmail());
    }

    public void initialize() throws IOException, SQLException {
        showCourses();
    }


    public void showCourses() throws IOException, SQLException {
        category.setText("Courses");
        loc.setText("/home/courses");
        addStudentBtn.setVisible(false);

        ObservableList<Node> cards = cardGrid.getChildren();
        cards.removeAll(cards);

        ArrayList<Course> courses = CourseRepository.getAllCourses(teacher);
        addAddBtn();
        int column = 1, row = 0;
        for(int i = 0; i< courses.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
            AnchorPane anchorPane = loader.load();
            CourseCardController courseCardController = loader.getController();
            courseCardController.setTitle(courses.get(i).getName());
            courseCardController.setOnClickListener(cardTitle -> {
                displayExamsForCourse(cardTitle);
            });
            cardGrid.add(anchorPane,column++,row);
            if (column == numberOfColumns) {
                column = 0;
                row++;
            }
        }

    }

    private void addCourse() throws SQLException {
        TextInputDialog addPopUp = new TextInputDialog();
        addPopUp.setTitle("Add New Course");
        addPopUp.setHeaderText("Enter the name of the course: ");
        addPopUp.setContentText("Name: ");

        Optional<String > result = addPopUp.showAndWait();
        if(result.isPresent()){
            Course newCourse = new Course();
            newCourse.setName(result.get());
            newCourse.setCreatedBy(teacher.getId());
            CourseRepository.insertCourse(newCourse);
        }
    }

    private void displayExamsForCourse(String courseTitle) throws IOException, SQLException {
        loc.setText("/home/courses/"+courseTitle);
        category.setText(courseTitle.toUpperCase());
        course = CourseRepository.getCourseByName(courseTitle);
        addStudentBtn.setVisible(true);

        ObservableList<Node> cards = cardGrid.getChildren();
        cards.removeAll(cards);
        ArrayList<Exam> exams = ExamRepository.getAllExamsForCourse(courseTitle);
        addAddBtn();
        int column = 1, row = 0;

        for(int i = 0; i< exams.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
            AnchorPane anchorPane = loader.load();
            CourseCardController courseCardController = loader.getController();
            courseCardController.setTitle(exams.get(i).getTitle());
            courseCardController.setImage(new Image(HelloApplication.class.
                    getResource("images/icons/exam.png").toString()));
            cardGrid.add(anchorPane,column++,row);
            if (column == numberOfColumns) {
                column = 0;
                row++;
            }
        }
    }

    public void addStudentToCourse() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("showCourseStudents.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 350);
        stage.setTitle("Course " + course.getName());
        ShowCourseStudentsController showCourseStudentsController = fxmlLoader.getController();
        showCourseStudentsController.setUp(course);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    private void addAddBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("courseCard.fxml"));
        AnchorPane anchorPane = loader.load();
        CourseCardController courseCardController = loader.getController();
        courseCardController.setTitle("ADD");
        courseCardController.setImage(new Image(HelloApplication.class.
                getResource("images/icons/add.png").toString()));
        if(category.getText().compareTo("Courses") == 0){
            courseCardController.setOnClickListener(cardTitle -> {
                addCourse();
                showCourses();
            });
        }else {
            courseCardController.setOnClickListener(cardTitle -> {
                String courseName = category.getText().toLowerCase();
                courseName = courseName.substring(0, 1).toUpperCase() + courseName.substring(1);
                addExam();
                displayExamsForCourse(courseName);
            });
        }

        cardGrid.add(anchorPane,0,0);
    }

    private void addExam() throws SQLException {
        TextInputDialog addPopUp = new TextInputDialog();
        addPopUp.setTitle("Add New Exam");
        addPopUp.setHeaderText("Enter the title of the exam: ");
        addPopUp.setContentText("Title: ");

        Optional<String > result = addPopUp.showAndWait();
        if(result.isPresent()){
            Exam newExam = new Exam();
            newExam.setTitle(result.get());
            newExam.setCourse_id(course.getId());
            ExamRepository.insertExam(newExam);
        }
    }

    public void handleEditPersonalInfo() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("updateUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        scene.setUserData("teacher");
        stage.setTitle("Edit Personal Info!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public void handleOnLogout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getTarget()).getScene().getWindow();
        LoginController.handleOnLogout(stage);
    }
}
