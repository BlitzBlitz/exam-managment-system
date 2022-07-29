package com.example.demo.controller;

import com.example.demo.HelloApplication;
import com.example.demo.entity.Message;
import com.example.demo.entity.MessageRepository;
import com.example.demo.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML
    TextField messageTextField;


    ArrayList<User> friendUsers;
    User loggedInUser;
    User selectedUser;


    public void setFriends(ArrayList<User> senders, User receiver){
        this.friendUsers = senders;
        this.loggedInUser = receiver;
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

                showChatMessages(sender, receiver);
                selectedUser = sender;

            });
            usersContainer.getChildren().add(chatUserContainer);
        });
    }

    private void showChatMessages(User sender, User receiver){

        this.messageContainer.getChildren().removeAll(this.messageContainer.getChildren());
        ArrayList<Message> messages = null;
        try {
            messages = MessageRepository.getMessages(sender,receiver);
        } catch (SQLException e) {
            AlertController.showAlert("Could not display message of chat. Try again!",
                    "Error while displaying messages", Alert.AlertType.ERROR);
        }
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
        if(message.getSender().getClass().getSimpleName().compareTo(loggedInUser.getClass().getSimpleName()) == 0){
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

    public void handleOnSend(){
        String message = messageTextField.getText();

        try {
            MessageRepository.sendMessage(loggedInUser, selectedUser, message);
        } catch (Exception e) {
            AlertController.showAlert("Select a contact please",
                    "Contact not selected", Alert.AlertType.ERROR);
        }
        messageTextField.setText("");
        showChatMessages(loggedInUser,selectedUser);
    }
}
