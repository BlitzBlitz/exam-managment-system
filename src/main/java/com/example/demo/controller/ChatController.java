package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.Message;
import com.example.demo.entity.MessageRepository;
import com.example.demo.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    @FXML
    VBox messageContainer;

    ArrayList<User> senders;
    User receiver;


    public void setFriends(ArrayList<User> senders, User receiver){
        this.senders = senders;
        this.receiver = receiver;
        senders.forEach(sender -> {
            HBox chatUserContainer = null;
            try {
                chatUserContainer = getChatUserContainer(sender, receiver);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            HBox finalChatUserContainer = chatUserContainer;
            chatUserContainer.setOnMouseClicked((event) -> {
                //Remove the unread messages badge
                finalChatUserContainer.getChildren().get(1).setVisible(false);
                try {
                    showChatMessages(sender, receiver);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            usersContainer.getChildren().add(chatUserContainer);
        });
    }

    private void showChatMessages(User sender, User receiver) throws SQLException {

        this.messageContainer.getChildren().removeAll(this.messageContainer.getChildren());
        ArrayList<Message> messages = MessageRepository.getMessages(sender,receiver);
        messages.forEach(message -> {
            try {
                addMessageLabelToChatWindow(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void addMessageLabelToChatWindow(Message message) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("messageLabel.fxml"));
        fxmlLoader.load();
        MessageLabelController messageLabelController = fxmlLoader.getController();
        messageLabelController.setMessage(message);
        System.out.println(message.getSender().getClass().getSimpleName().compareTo(receiver.getClass().getSimpleName()) == 0);
        if(message.getSender().getClass().getSimpleName().compareTo(receiver.getClass().getSimpleName()) == 0){
            messageLabelController.setAlignment(Pos.CENTER_LEFT);
        }else {
            messageLabelController.setAlignment(Pos.CENTER_RIGHT);
        }
        messageContainer.getChildren().add(messageLabelController.getMessageLabel());
    }

    private HBox getChatUserContainer(User sender, User receiver) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chatUser.fxml"));
        fxmlLoader.load();
        ChatUserController chatUserController = fxmlLoader.getController();
        chatUserController.setSender(sender);
        chatUserController.setReceiver(receiver);
        chatUserController.setShowMessagesListener(() ->{
            showChatMessages(sender,receiver);
        });
        return chatUserController.getChatUserContainer();
    }

    public HBox getChatContainer() {
        return chatContainer;
    }

}
