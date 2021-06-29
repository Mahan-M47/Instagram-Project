package Client.View;


import Client.Model.ChatPersonal;
import Client.Utils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatListController implements Initializable {


    @FXML
    private VBox itemHolder;

    private List<ChatPersonal> chats = new ArrayList<>();

    public static String username;
    public static ChatPersonal chat;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chats = Utils.personalChats;
        if (chats.size() != 0) {
            for (int i = 0; i < chats.size(); i++) {
                final int j = i;
                username = chats.get(i).getMembers().get(0).equals(Utils.currentUser)
                        ? chats.get(i).getMembers().get(1)
                        : chats.get(i).getMembers().get(0);
                Label label = new Label("    " + username);

                label.setFont(new Font("ebrime", 20));
                label.setPrefSize(1300, 90);
                label.setStyle("-fx-background-color: white");
                label.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        label.setStyle("-fx-background-color: #dadada");
                    }
                });
                label.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        label.setStyle("-fx-background-color: white");
                    }
                });
                label.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        username = label.getText();
                        chat = chats.get(j);
                        Starter.changeScene("ChatPage");
                    }
                });
                itemHolder.getChildren().add(label);
                itemHolder.setPrefHeight(itemHolder.getPrefHeight() + 90);

            }
        }


    }
}
