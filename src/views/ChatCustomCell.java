package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import models.ChatMessage;

import java.io.IOException;

public class ChatCustomCell extends ListCell<ChatMessage> {

    public Label messageLabel;

    public GridPane chatGridPage;

    public FXMLLoader loader;


    @Override
    protected void updateItem(ChatMessage item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("chat.fxml"));
                loader.setController(this);
            }
            try {
                loader.load();
            } catch (IOException e) {
                System.out.println("Loading Error: " + e.getMessage());
            }
            messageLabel.setText(item.message);
            if (!item.isMe) {
                messageLabel.setStyle("-fx-background-color: #b3b3b3; -fx-background-radius: 0 20 20 20; -fx-border-radius: 20px");
                GridPane.setHalignment(messageLabel, HPos.LEFT);
            }
            setText(null);
            loader.setRoot(null);
            setGraphic(chatGridPage);
        }
    }

}
