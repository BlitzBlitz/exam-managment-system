package com.example.demo.controller;

import com.example.demo.entity.Teacher;
import com.example.demo.entity.TableData;
import com.example.demo.entity.TeacherRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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



    public void displayTeachers(ActionEvent actionEvent) {
        loc.setText("/home/teachers");
        category.setText("Teachers");
        ObservableList<TableData> teachersData = FXCollections.observableArrayList();
        infoTable.setPlaceholder(new Label("There are no teacher registered"));
        try{
            ArrayList<Teacher> teachers = TeacherRepository.getAllTeachers();
            teachersData = FXCollections.observableArrayList(
                    teachers.stream().map(teacher -> new TableData(teacher)).toArray(TableData[]::new));
        }catch (SQLException e){
            System.out.println(e.getMessage());
            //TODO pop-up error
        }
        infoTable.setItems(teachersData);
    }

    public void displayStudents(ActionEvent actionEvent) {
        loc.setText("/home/students");
        category.setText("Students");
        infoTable.setPlaceholder(new Label("There are no students registered"));

    }


}
