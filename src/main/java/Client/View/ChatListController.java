package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.GroupChat;
import Client.Model.PersonalChat;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatListController implements Initializable
{
    @FXML
    private VBox itemHolder;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, groupChatButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // Personal chats:
        List<PersonalChat> personalChats = Utils.personalChats;

        for (PersonalChat chat : personalChats)
        {
            String chatTitle;
            List<String> members = chat.getMembers();

            if ( members.get(0).equals(Utils.currentUser) ) {
                chatTitle = members.get(1);
            }
            else chatTitle = members.get(0);

            Label chatLabel = new Label("    " + chatTitle);
            createChatLabel(chatLabel);

            chatLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ChatPageController.setPersonalChat(chat);
                    Starter.changeScene(Utils.GUI.CHAT_PAGE);
                }
            });

            itemHolder.getChildren().add(chatLabel);
            itemHolder.setPrefHeight(itemHolder.getPrefHeight() + 90);
        }

        //Group Chats:
        List<GroupChat> groupChats = Utils.groupChats;

        for (GroupChat chat : groupChats)
        {
            StringBuilder chatTitle = new StringBuilder("(Group) ");
            List<String> members = chat.getMembers();

            for (String member : members) {
                chatTitle.append(member).append(", ");
            }
            chatTitle.deleteCharAt( chatTitle.lastIndexOf(", "));

            Label chatLabel = new Label("    " + chatTitle.toString());
            createChatLabel(chatLabel);

            chatLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ChatPageController.setGroupChat(chat);
                    Starter.changeScene(Utils.GUI.CHAT_PAGE);
                }
            });

            itemHolder.getChildren().add(chatLabel);
            itemHolder.setPrefHeight(itemHolder.getPrefHeight() + 90);
        }

    }

    public void createChatLabel(Label chatLabel)
    {
        chatLabel.setFont(new Font("ebrime", 20));
        chatLabel.setPrefSize(700, 90);
        chatLabel.setStyle("-fx-background-color: white");

        chatLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chatLabel.setStyle("-fx-background-color: #dadada");
            }
        });

        chatLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chatLabel.setStyle("-fx-background-color: white");
            }
        });
    }

    @FXML
    void groupChatButtonClickHandler(ActionEvent event) {
        Request req = new Request(Utils.REQ.GROUP_CHAT, new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void homeButtonClickHandler(ActionEvent event) { CommonClickHandlers.homeButton(); }
    @FXML
    void profileButtonClickHandler(ActionEvent event) { CommonClickHandlers.myProfileButton(); }
    @FXML
    void searchButtonClickHandler(ActionEvent event) { CommonClickHandlers.searchButton(); }
    @FXML
    void postButtonClickHandler(ActionEvent event) { CommonClickHandlers.postButton(); }
    @FXML
    void chatsButtonClickHandler(ActionEvent event) { CommonClickHandlers.chatsButton(); }


}
