package views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FriendCustomCell extends ListCell<String> implements Initializable {
    @FXML
    public FontAwesomeIcon friendIcon;
    @FXML
    public Label friendNameLabel;
    @FXML
    public FontAwesomeIcon friendRemoveButton;
    @FXML
    public AnchorPane friendAnchorPane;
    public FXMLLoader loader;
    public ListView<String> friends;

    public FriendCustomCell(ListView<String> friends){
        this.friends = friends;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if(loader == null) {
                loader = new FXMLLoader(getClass().getResource("friend.fxml"));
                loader.setController(this);
            }
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            friendNameLabel.setText(item);
            loader.setRoot(null);
            setText(null);
            setGraphic(friendAnchorPane);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendRemoveButton.setOnMouseClicked(event -> {
            friends.getItems().remove(friends.getSelectionModel().getSelectedIndex());
        });
    }
}


