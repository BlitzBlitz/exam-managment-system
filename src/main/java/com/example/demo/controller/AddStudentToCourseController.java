package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.Course;
import com.example.demo.entity.CourseRepository;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentRepository;
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

public class AddStudentToCourseController {
    @FXML
    Label titleLabel;
    @FXML
    VBox studentsContainer;
    @FXML
    TextField searchField;

    Course course;

    public void setUp(Course course) throws SQLException {
        this.course = course;
        titleLabel.setText(titleLabel.getText() + course.getName());
        addStudentsButtons();
    }

    public void handleOnSearch(ActionEvent actionEvent) throws SQLException {
        studentsContainer.getChildren().removeAll(studentsContainer.getChildren());
        addStudentsButtons();
    }

    private void addStudentsButtons() throws SQLException {
        ArrayList<Student> students = StudentRepository.getStudentsByAnyField(searchField.getText());
        students.forEach(student -> {
            Button studentBtn = getButton(student);
            studentsContainer.getChildren().add(studentBtn);
        });
    }

    private Button getButton(Student student) {
        Button studentBtn = new Button(student.getName() + " " +
                student.getLastname() + " | " + student.getEmail());
        studentBtn.setPrefWidth(335);
        studentBtn.setPadding(new Insets(10));
        studentBtn.setStyle("-fx-background-color: #274690");
        studentBtn.setTextFill(Color.WHITE);
        studentBtn.setOnAction((ActionEvent event)->{
            showConfirmation(student.getName(), course.getName()).ifPresent((btnType) -> {
                if(btnType == ButtonType.OK){
                    addStudentToCourse(student.getName(), course.getName());
                }
            });
        });
        return studentBtn;
    }

    private void addStudentToCourse(String studentName, String courseName){
        try {
            CourseRepository.addStudentToCourse(studentName, courseName);
            AlertController.showAlert("Student: " + studentName +" added to course: " + courseName,
                    "Student added", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            if(e.getMessage().contains("registered")){
                AlertController.showAlert("Student already registered!",
                        "Student can not be added", Alert.AlertType.WARNING);
            }else {
                AlertController.showAlert("Please try again!",
                        "Error while adding student to course", Alert.AlertType.ERROR);
            }

        }
    }

    private Optional<ButtonType> showConfirmation(String studentName, String courseName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add student to Course");
        alert.setHeaderText("Do you want to add "+ studentName +" to course: "+ courseName +"?");
        return alert.showAndWait();
    }

    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("searchAndAdd.fxml"));
        Button button = (Button) actionEvent.getTarget();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene showStudents = new Scene(loader.load(), 350,350);
        SearchAndAddController searchAndAddController = loader.getController();
        searchAndAddController.setUp(course);
        stage.setScene(showStudents);
    }
}
