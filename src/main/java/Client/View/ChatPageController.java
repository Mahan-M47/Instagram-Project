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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatPageController implements Initializable
{
    @FXML
    private Label chatLabel, errorLabel;
    @FXML
    private Hyperlink usernameLink;
    @FXML
    private TextField messageTF, addMemberTF;
    @FXML
    private Button sendMessageButton, sendFileButton, addMemberButton;
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
            usernameLink.setVisible(true);
            List<String> members = personalChat.getMembers();

            if (members.get(0).equals(Utils.currentUser)) {
                usernameLink.setText(members.get(1));
            }
            else usernameLink.setText(members.get(0));

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

            chatLabel.setVisible(true);
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
        AnchorPane messageHolder = new AnchorPane();

        if (message.getMessageType().equals("TEXT"))
        {
            Label messageLabel = new Label();
            messageLabel.setFont(new Font("Ebrima", 22));

            messageHolder.setPrefSize(880, 50);
            messageLabel.setPrefHeight(45);
            messageLabel.setLayoutX(5);
            messageLabel.setLayoutY(3);

            if ( message.getSender().equals(Utils.currentUser) )
            {
                messageHolder.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                messageLabel.setText("  " + message.getText() + "  ");
                messageLabel.setStyle("-fx-background-color: #d4d4d4;" +
                        "-fx-background-radius: 18");
            }
            else {
                messageLabel.setText("  " + message.getSender() + ":  " + message.getText() + "  ");
                messageLabel.setStyle("-fx-background-color: #ffffff;" +
                        "-fx-background-radius: 18");
            }

            messageHolder.getChildren().add(messageLabel);
            background.setPrefHeight(background.getPrefHeight() + 50);
        }
        else
        {
            InputStream in = new ByteArrayInputStream( message.getImageBytes() );
            Image img = new Image(in);
            ImageView imageView = new ImageView(img);

            imageView.setFitWidth(350);
            imageView.setFitHeight(350);
            imageView.setLayoutX(10);
            imageView.setLayoutY(10);

            AnchorPane imageHolder = new AnchorPane();
            imageHolder.setPrefSize(370, 370);

            if ( message.getSender().equals(Utils.currentUser) ) {
                messageHolder.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                imageHolder.setStyle("-fx-background-color: #d4d4d4;" +
                        "-fx-background-radius: 18");
            }
            else {
                imageHolder.setStyle("-fx-background-color: #ffffff;" +
                        "-fx-background-radius: 18");
            }

            imageHolder.getChildren().add(imageView);
            imageHolder.setLayoutX(10);
            imageHolder.setLayoutY(10);

            messageHolder.setPrefSize(880, 390);
            messageHolder.getChildren().add(imageHolder);

            background.setPrefHeight(background.getPrefHeight() + 390);
        }

        background.getChildren().add(messageHolder);
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
    void sendFileButtonClickHandler(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        File chosenFile = fileChooser.showOpenDialog(new Stage());

        if (chosenFile != null)
        {
            String filePath = chosenFile.getPath();

            if (chosenFile.length() > Utils.MESSAGE_FILE_MAX_SIZE) {
                errorLabel.setText("Maximum Image Size Is 1 MB.");
            }
            else if (filePath.matches(".+\\.jpe?g"))
            {
                errorLabel.setText("");
                Message message = new Message(Utils.currentUser, chosenFile);
                Request req = new Request(Utils.REQ.MESSAGE, new Data(chatID, message));
                NetworkManager.putRequest(req);
                createMessage(message);
            }
            else {
                errorLabel.setText("You Can Only Send jpg Images.");
            }
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
    void usernameLinkClickHandler(ActionEvent event) {
        CommonClickHandlers.showProfileButton( usernameLink.getText() );
    }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.chatsButton();
    }
}
