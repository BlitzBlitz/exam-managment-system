package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class AdminController {
    @FXML
    Label loc;
    @FXML
    Label category;
    @FXML
    TableView<TableData> infoTable;
    @FXML
    TextField searchText;



    public void displayTeachers() {
        loc.setText("/home/teachers");
        category.setText("Teachers");
        infoTable.setPlaceholder(new Label("There are no teacher registered"));
        try{
            ArrayList<Teacher> teachers = TeacherRepository.getAllTeachers();
            ObservableList<TableData> teachersData = FXCollections.observableArrayList(
                    teachers.stream().map(teacher -> new TableData(teacher)).toArray(TableData[]::new));
            infoTable.setItems(teachersData);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            AlertController.showAlert("Error occurred. Please restart the program.", "Error", Alert.AlertType.ERROR);
        }
    }



    public void displayStudents() {
        loc.setText("/home/students");
        category.setText("Students");
        infoTable.setPlaceholder(new Label("There are no students registered"));
        try{
            ArrayList<Student> students = StudentRepository.getAllStudents();
            ObservableList<TableData> studentsData = FXCollections.observableArrayList(
                    students.stream().map(TableData::new).toArray(TableData[]::new));
            infoTable.setItems(studentsData);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            AlertController.showAlert("Error occurred. Please restart the program.", "Error", Alert.AlertType.ERROR);
        }
    }

    public void search(){
        ObservableList<TableData> tableData = FXCollections.observableArrayList();
        String keyword = searchText.getText();
        try {
            if(category.getText().contains("Teachers")){
                infoTable.setPlaceholder(new Label("No teacher found matching the search"));
                ArrayList<Teacher> teachers = TeacherRepository.getTeachersByAnyField(keyword);
                tableData = FXCollections.observableArrayList(
                        teachers.stream().map(teacher -> new TableData(teacher)).toArray(TableData[]::new));
            }else if (category.getText().contains("Students")){
                infoTable.setPlaceholder(new Label("No student found matching the search"));
                ArrayList<Student> students = StudentRepository.getStudentsByAnyField(keyword);
                tableData = FXCollections.observableArrayList(
                        students.stream().map(student -> new TableData(student)).toArray(TableData[]::new));
            }else {
                infoTable.setPlaceholder(new Label("Select a category please"));

            }

            infoTable.setItems(tableData);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            AlertController.showAlert("Error occurred. Please restart the program.", "Error", Alert.AlertType.ERROR);
        }
    }


    public void addUser() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
        stage.setTitle("Add User!");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if(category.getText().compareTo("Teachers") == 0){
            displayTeachers();
        }else {
            displayStudents();
        }
    }

    public void handleOnLogout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)  ((Button)actionEvent.getTarget()).getScene().getWindow();
        LoginController.handleOnLogout(stage);
    }
}
