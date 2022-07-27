package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatController {
    @FXML
    HBox chatContainer;
    @FXML
    Label usersTitle;
    @FXML
    VBox usersContainer;

    ArrayList<User> senders;


    public void setUsers(ArrayList<User> senders, User receiver){
        this.senders = senders;
        senders.forEach(sender -> {
            HBox chatUserContainer = null;
            try {
                chatUserContainer = getChatUserContainer(sender, receiver);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            usersContainer.getChildren().add(chatUserContainer);
        });
    }

    private HBox getChatUserContainer(User sender, User receiver) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chatUser.fxml"));
        fxmlLoader.load();
        ChatUserController chatUserController = fxmlLoader.getController();
        chatUserController.setSender(sender);
        chatUserController.setReceiver(receiver);
        return chatUserController.getChatUserContainer();
    }

    public HBox getChatContainer() {
        return chatContainer;
    }

}
