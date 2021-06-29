package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.GroupChat;
import Client.Model.PersonalChat;
import Client.Model.Message;
import Client.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatPageController implements Initializable
{
    @FXML
    private Label chatLabel, errorLabel;
    @FXML
    private TextField messageTF, addMemberTF;
    @FXML
    private Button sendMessageButton, addMemberButton;
    @FXML
    private AnchorPane membersPane;
    @FXML
    private ListView<String> membersListView;
    @FXML
    private VBox background;

    private static PersonalChat personalChat;
    private static GroupChat groupChat;
    private static String chatID;

    public static void setPersonalChat(PersonalChat chosenChat) {
        personalChat = chosenChat;
        chatID = chosenChat.getChatID();
        groupChat = null;
    }

    public static void setGroupChat(GroupChat chosenChat) {
        groupChat = chosenChat;
        chatID = chosenChat.getChatID();
        personalChat = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        if (personalChat != null)
        {
            List<String> members = personalChat.getMembers();

            if (members.get(0).equals(Utils.currentUser)) {
                chatLabel.setText(members.get(1));
            }
            else chatLabel.setText(members.get(0));

            for (Message message : personalChat.getMessageList()) {
                createMessage(message);
            }
        }
        else
        {
            List<String> members = groupChat.getMembers();
            ObservableList<String> searchResults = FXCollections.observableList(members);
            membersListView.setItems(searchResults);
            membersListView.setStyle(" -fx-font: 18pt \"System\" ");

            chatLabel.setText("Group Chat");
            errorLabel.setText(Utils.ADD_MEMBER_ERROR_TEXT);
            membersPane.setVisible(true);
            Utils.resetErrorTexts();

            for (Message message : groupChat.getMessageList()) {
                createMessage(message);
            }
        }
    }

    public void createMessage(Message message)
    {
        AnchorPane labelHolder = new AnchorPane();
        Label messageLabel = new Label("  " + message.getText() + "  ");
//        labelHolder.setStyle("-fx-background-color: white;");
        messageLabel.setFont(new Font("Ebrima", 22));

        if ( message.getSender().equals(Utils.currentUser) )
        {
            labelHolder.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            messageLabel.setStyle("-fx-background-color: #d4d4d4;" +
                    "-fx-background-radius: 18");
        }
        else
        {
            messageLabel.setStyle(
                    "-fx-background-color: #ffffff;" +
                    "-fx-background-radius: 18");
        }

        labelHolder.setPrefSize(880, 50);
        messageLabel.setPrefHeight(35);
        messageLabel.setLayoutX(5);
        messageLabel.setLayoutY(3);

        labelHolder.getChildren().add(messageLabel);
        background.setPrefHeight(background.getPrefHeight() + 50);
        background.getChildren().add(labelHolder);
    }

    @FXML
    void sendButtonClickHandler(ActionEvent event)
    {
        if (! messageTF.getText().matches("\\s*") )
        {
            String messageText = messageTF.getText();
            Message message = new Message(Utils.currentUser, messageText);
            createMessage(message);
            messageTF.setText("");

            Request req = new Request(Utils.REQ.MESSAGE, new Data(chatID, message));
            NetworkManager.putRequest(req);
        }
    }

    @FXML
    void addMemberButtonClickHandler(ActionEvent event)
    {
        String searchText = addMemberTF.getText().toLowerCase();

        if (! searchText.matches("\\s*") ) {
            Request req = new Request(Utils.REQ.ADD_MEMBER, new Data(searchText, chatID));
            NetworkManager.putRequest(req);
        }

    }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.chatsButton();
    }
}
