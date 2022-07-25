package com.example.demo.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;

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
        cardBtn.setOnAction(actionEvent -> {
            try {
                listener.onClickListener(((Button) actionEvent.getTarget()).getText());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
