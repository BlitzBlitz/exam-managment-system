package com.example.demo.controller;

import com.example.demo.entity.Message;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MessageLabelController {
    @FXML
    HBox messageContainer;
    @FXML
    Label messageLabel;


    Message message;
    public void setMessage(Message message) {
        this.message = message;
    }
    public void setAlignment(Pos position){
        messageContainer.setAlignment(position);
    }

    public Node getMessageLabel() {
        messageLabel.setText(message.getMessage());
        return messageContainer;
    }

    public void setBackgroundColor(Color color) {
        messageLabel.setBackground(new Background(new BackgroundFill
                (color, new CornerRadii(5.0), new Insets(-5.0))));
        messageLabel.setTextFill(Color.WHITE);
    }
}
