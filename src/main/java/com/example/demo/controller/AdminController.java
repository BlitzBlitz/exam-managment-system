package com.example.demo.controller;

import com.example.demo.entity.Teacher;
import com.example.demo.entity.TableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

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
        teachersData.add(new TableData(new Teacher(1,"1","1","1","1","1")));

        TableColumn<TableData, String> column = (TableColumn<TableData, String>)infoTable.getColumns().get(1);
        infoTable.setItems(teachersData);
    }

    public void displayStudents(ActionEvent actionEvent) {
        loc.setText("/home/students");
        category.setText("Students");
    }

    class GetTeachers extends Task{
        @Override
        protected Object call() throws Exception {
            ArrayList<TableData> teachers = new ArrayList<>();
            teachers.add(new TableData(new Teacher(1,"1","1","1","1","1")));
            return FXCollections.observableArrayList(
                    teachers
            );
        }
    }
}
