package com.example.demo.controller;

import com.example.demo.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
            displayAlertMessage("Error occurred. Please restart the program.");
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
            displayAlertMessage("Error occurred. Please restart the program.");
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
            displayAlertMessage("Error occurred. Please restart the program.");
        }
    }

    public static void displayAlertMessage(String message){
        Alert alertWindow = new Alert(Alert.AlertType.ERROR);
        alertWindow.setTitle("Error");
        alertWindow.setContentText(message);
        alertWindow.showAndWait();
    }

}
