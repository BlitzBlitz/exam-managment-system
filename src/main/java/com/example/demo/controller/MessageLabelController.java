package com.example.demo.controller;

import com.example.demo.entity.Message;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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
}
