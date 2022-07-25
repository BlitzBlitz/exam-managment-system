package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.Course;
import com.example.demo.entity.CourseRepository;
import com.example.demo.entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ShowCourseStudentsController {
    @FXML
    TextField searchField;
    @FXML
    VBox studentsContainer;
    @FXML
    Label titleLabel;

    ArrayList<Student> students;
    Course course;

    public void setUp(Course course) {
        try {
            students = CourseRepository.getStudentsForCourse(course);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.course = course;
        setTitleLabel(course.getName());
        addStudents(students);
    }

    public void setTitleLabel(String title){
        titleLabel.setText(titleLabel.getText() + title);
    }
    public void addStudents(ArrayList<Student> students){
        this.students = students;
        if(students.size() != 0){
            cleanContainer();
        }
        students.forEach(student -> {
            Label label = getLabel(student);
            studentsContainer.getChildren().add(label);
        });
    }

    public void handleOnSearch(){
        cleanContainer();
        String keyWord= searchField.getText();
        students.stream().filter(student -> student.getName().contains(keyWord)).forEach(
                student -> {
                    Label label = getLabel(student);
                    studentsContainer.getChildren().add(label);
                }
        );
    }

    private void cleanContainer() {
        studentsContainer.getChildren().removeAll(studentsContainer.getChildren());
    }

    public void handleOnAdd(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("addStudentToCourse.fxml"));
        Button button = (Button) actionEvent.getTarget();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene addScene = new Scene(loader.load(), 350,350);
        AddStudentToCourseController addStudentToCourseController = loader.getController();
        addStudentToCourseController.setUp(course);
        stage.setScene(addScene);
    }

    private Label getLabel(Student student) {
        Label label = new Label(student.getName()+" " + student.getLastname()
                + " | " + student.getEmail());
        label.setPrefWidth(335);
        label.setPadding(new Insets(10));
        label.setStyle("-fx-background-color: #274690");
        label.setTextFill(Color.WHITE);

        return label;
    }






}
