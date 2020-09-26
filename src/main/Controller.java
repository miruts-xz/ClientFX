package main;

import client.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import models.ChatMessage;

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

    @FXML
    private void onSendPressed(ActionEvent event) {
        client.writer.println(selectedFriend + " " + newMessage.getText());
        chatList.getItems().add(new ChatMessage(username.getText(), selectedFriend, newMessage.getText()));
        newMessage.clear();
    }


    public void onConnectPressed(ActionEvent actionEvent) {
        client = new Client(hostInput.getText(), Integer.parseInt(portInput.getText()), username.getText());
        client.start();
        friendsList.setItems(client.friends);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedFriend = newValue;
            newMessageSend.setDisable(false);
            newMessage.setDisable(false);
            chatList.setItems(client.messages.get(selectedFriend));
            client.messages.computeIfAbsent(selectedFriend, k -> FXCollections.<ChatMessage>observableArrayList());
            chatList.setItems(client.messages.get(selectedFriend));
        });

    }
}


