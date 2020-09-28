package views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FriendCustomCell extends ListCell<String> {
    @FXML
    public FontAwesomeIcon friendIcon;
    @FXML
    public Label friendNameLabel;
    @FXML
    public FontAwesomeIcon friendRemoveButton;
    @FXML
    public AnchorPane friendAnchorPane;
    public FXMLLoader loader;
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
            setText(null);
            setGraphic(friendAnchorPane);
        }
    }
}


