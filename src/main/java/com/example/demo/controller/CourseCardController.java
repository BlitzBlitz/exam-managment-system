package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.Course;
import com.example.demo.entity.CourseRepository;
import com.example.demo.entity.Exam;
import com.example.demo.entity.ExamRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseCardController {

    @FXML
    Button cardBtn;
    @FXML
    ImageView cardImg;




    public void setTitle(String title){
        cardBtn.setText(title);
    }
    public  void setImage(Image image){
        cardImg.setImage(image);
    }
    public void setOnClickListener(MyCardListener listener){
        cardBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    listener.onClickListener(((Button) actionEvent.getTarget()).getText());
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void handleCardClick(ActionEvent actionEvent) throws SQLException, IOException {
        Button button = (Button) actionEvent.getTarget();
        if(button.getText().compareTo("ADD") == 0){
            System.out.println("displaing add");

            //TODO add course pop-up
        }else {


            ArrayList<Exam> exams = ExamRepository.getAllExamsForCourse(cardBtn.getText());
            int column = 1, row = 0;
            for(int i = 0; i< exams.size();i++){


                if (column == 4) {
                    column = 0;
                    row++;
                }
            }
        }

    }

}
