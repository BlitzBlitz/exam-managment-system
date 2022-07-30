package com.example.demo.controller;

import com.example.demo.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import com.example.demo.entity.MessageRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ChatUserController {
    @FXML
    HBox chatUserContainer;
    @FXML
    Label usernameLabel;
    @FXML
    Label unreadMessagesLabel;

    ShowMessagesListener showMessagesListener;

    User sender;
    User receiver;

    public void setSender(User sender) {
        this.sender = sender;
    }
    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    public HBox getChatUserContainer() throws SQLException {
        if(this.sender == null){
            return null;
        }
        usernameLabel.setText(this.sender.getName() + " " + this.sender.getLastname() );
        int unreadMessages = MessageRepository.getUnreadMessagesCount(this.sender, this.receiver);
        if(unreadMessages > 0){
            unreadMessagesLabel.setText( unreadMessages + "");
        }else {
            unreadMessagesLabel.setVisible(false);
        }
        return chatUserContainer;
    }
    public void setShowMessagesListener(ShowMessagesListener showMessagesListener){
        this.showMessagesListener = showMessagesListener;
    }


    public void handleShowChat(MouseEvent mouseEvent) throws SQLException, IOException {
        showMessagesListener.onClickListener();
    }
}
