package com.example.demo.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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


    public void handleCardClick(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getTarget();
        if(button.getText().compareTo("ADD") == 0){
            System.out.println("displaing add");

            //TODO add course pop-up
        }else {
            GridPane cardGrid = (GridPane)button.getParent().getParent();
            ObservableList<Node> cards = cardGrid.getChildren();
            cards.removeAll(cards);

        }

    }
}
