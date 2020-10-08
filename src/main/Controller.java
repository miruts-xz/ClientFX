package main;

import client.Client;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import models.ChatMessage;
import views.ChatCustomCell;
import views.FriendCustomCell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button newMessageSend;
    public TextArea newMessage;
    public Client client;
    public ListView<ChatMessage> chatList = new ListView<>();
    public ListView<String> friendsList = new ListView<>();
    public Button connectButton;
    public TextField portInput;
    public TextField hostInput;
    public TextField username;
    public String selectedFriend;
    public Label connectionLabel;

    @FXML
    private void onSendPressed(ActionEvent event) {
        if(newMessage.getText() == null || newMessage.getText().trim().equals("")){
            newMessage.requestFocus();
            return;
        }
        client.writer.println(selectedFriend + " " + newMessage.getText());
        ChatMessage message = new ChatMessage(username.getText(), selectedFriend, newMessage.getText());
        message.isMe = true;
        chatList.getItems().add(message);
        chatList.getItems().addListener((ListChangeListener<? super ChatMessage>) observable -> {
            if(chatList.getItems().size() > 0){
                chatList.scrollTo(chatList.getItems().size()-1);
            }
        });
        newMessage.clear();
        newMessage.requestFocus();
    }


    public void onConnectPressed(ActionEvent actionEvent) {
        if (connectButton.getText().equals("Connected")) {
            client.disconnect();
            friendsList.setItems(null);
        } else {
            client = new Client(hostInput.getText(), Integer.parseInt(portInput.getText()), username.getText());
            client.start();
            friendsList.setItems(client.friends);
            client.friends.addListener((ListChangeListener<? super String>) observable -> {
                if(client.friends.size() > 0 && friendsList.getSelectionModel().getSelectedItem() == null){
                    friendsList.getSelectionModel().selectFirst();
                }
            });
            client.isConnected.addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    Platform.runLater(() -> {
                        connectionLabel.setStyle("-fx-background-color: #00cf00; -fx-background-radius: 10px;");
                        connectButton.setText("Connected");
                    });
                } else {
                    Platform.runLater(() -> {
                        connectionLabel.setStyle("-fx-background-color: #df0000; -fx-background-radius: 10px;");
                        connectButton.setText("Connect");
                    });
                }
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                selectedFriend = newValue;
                newMessageSend.setDisable(false);
                newMessage.setDisable(false);
                client.messages.computeIfAbsent(selectedFriend, k -> FXCollections.<ChatMessage>observableArrayList());
                chatList.setItems(client.messages.get(selectedFriend));
            }else {
                newMessageSend.setDisable(true);
                newMessage.setDisable(true);
                chatList.setItems(null);
            }
        });
        friendsList.setCellFactory(data -> new FriendCustomCell(friendsList));
        chatList.setCellFactory(chatMessage -> new ChatCustomCell());
    }

}


