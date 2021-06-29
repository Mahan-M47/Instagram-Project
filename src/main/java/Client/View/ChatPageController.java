package Client.View;

import Client.Model.PersonalChat;
import Client.Model.Message;
import Client.Utils;
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

    private static PersonalChat chat;
    private static String chatTitle;

    public static void setPersonalChat(PersonalChat chosenChat, String chosenChatTitle) {
        chat = chosenChat;
        chatTitle = chosenChatTitle;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        chatLabel.setText(chatTitle);

        for (Message message : chat.getMessageList()) {
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
            chat.addMessage(message);
            messageTF.setText("");
        }
    }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.chatsButton();
    }
}
