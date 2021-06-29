package Client.View;

import Client.Model.PersonalChat;
import Client.Model.Message;
import Client.Utils;
import Server.Model.GroupChat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatPageController implements Initializable {

    @FXML
    private Label chatLabel;
    @FXML
    private TextField messageTF;
    @FXML
    private Button sendMessageButton;
    @FXML
    private VBox background;

    private static PersonalChat personalChat;
    private static GroupChat groupChat;
    public static void setPersonalChat(PersonalChat chosenChat) {
        personalChat = chosenChat;
        groupChat = null;
    }
    public static void setGroupChat(GroupChat chosenChat) {
        groupChat = chosenChat;
        personalChat = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        List<String> members = personalChat.getMembers();

        if ( members.get(0).equals(Utils.currentUser) ) {
            chatLabel.setText( members.get(1) );
        }
        else chatLabel.setText( members.get(0) );

        for (Message message : personalChat.getMessageList()) {
            createMessage(message);
        }

    }

    public void createMessage(Message message)
    {
        AnchorPane labelHolder = new AnchorPane();
        Label messageLbl = new Label("  " + message.getText() + "  ");
        labelHolder.setStyle("-fx-background-color: white;");
        messageLbl.setFont(new Font("Ebrima", 16));

        if ( message.getSender().equals(Utils.currentUser) )
        {
            labelHolder.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            messageLbl.setStyle("-fx-background-color: #efefef;" +
                    "-fx-background-radius: 18");
        }
        else
        {
            messageLbl.setStyle("-fx-border-radius: 18;" +
                    "-fx-border-color: #efefef;" +
                    "-fx-background-radius: 18");
        }

        labelHolder.setPrefSize(890, 40);
        messageLbl.setPrefHeight(35);
        messageLbl.setLayoutX(5);
        messageLbl.setLayoutY(3);

        labelHolder.getChildren().add(messageLbl);
        background.setPrefHeight(background.getPrefHeight() + 40);
        background.getChildren().add(labelHolder);
    }

    @FXML
    void sendButtonClickHandler(ActionEvent event) {
        if (!messageTF.getText().matches("\\s*")){
            String messageText = messageTF.getText();
            Message message = new Message(Utils.currentUser, messageText);
            createMessage(message);
            personalChat.addMessage(message);
            messageTF.setText("");
        }
    }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.chatsButton();
    }
}
