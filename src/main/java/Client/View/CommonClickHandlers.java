package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.Post;
import Client.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

public class CommonClickHandlers
{
    public static void homeButton() {
        Request req = new Request(Utils.REQ.TIMELINE, new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    public static void myProfileButton() {
        Request req = new Request(Utils.REQ.MY_PROFILE, new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    public static void searchButton() { Starter.changeScene(Utils.GUI.SEARCH); }

    public static void postButton() { Starter.changeScene(Utils.GUI.CREATE_POST); }

    public static void chatsButton() { }

    public static void editProfileButton() {
        Starter.changeScene(Utils.GUI.EDIT_PROFILE);
    }

    public static void showProfileButton(String username)
    {
        if (username != null)
        {
            Request req;

            if ( username.equals(Utils.currentUser) ) {
                req = new Request(Utils.REQ.MY_PROFILE, new Data(username) );
            }
            else {
                req = new Request(Utils.REQ.PROFILE, new Data(username) );
            }
            NetworkManager.putRequest(req);
        }
    }

    public static void likeButton(Button likeButton, Label likeLabel, Post post)
    {
        Request req;
        if (! post.getLikedBy().contains(Utils.currentUser) )
        {
            req = new Request(Utils.REQ.LIKE, new Data(Utils.currentUser, post.getID()));
            post.addLike(Utils.currentUser);
            likeButton.setText("Liked!");
            likeButton.setStyle("-fx-background-color: #90c181");
        }
        else {
            req = new Request(Utils.REQ.UNLIKE, new Data(Utils.currentUser, post.getID()));
            post.removeLike(Utils.currentUser);
            likeButton.setText("Like");
            likeButton.setStyle("-fx-background-color: #d4d4d4");
        }

        likeLabel.setText("" + post.getLikedBy().size());
        NetworkManager.putRequest(req);
    }

    public static void commentsButton(ScrollPane commentsScrollPane, Button sendButton, TextField commentsTF)
    {
        if (commentsScrollPane.isVisible())
        {
            commentsScrollPane.setVisible(false);
            sendButton.setVisible(false);
            commentsTF.setVisible(false);
            sendButton.setDefaultButton(false);
        }
        else {
            commentsScrollPane.setVisible(true);
            sendButton.setVisible(true);
            commentsTF.setVisible(true);
            sendButton.setDefaultButton(true);
        }
    }

    public static void playButton(MediaPlayer mediaPlayer)
    {
        if (mediaPlayer != null)
        {
            if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                mediaPlayer.pause();
            }
            else mediaPlayer.play();
        }
    }

    public static void sendButton(VBox commentsVBox, TextField commentsTF, Label commentsLabel, Post post)
    {
        if (! commentsTF.getText().matches("\\s*") )
        {
            String commentText = Utils.currentUser + ": " + commentsTF.getText();
            post.addComment(commentText);
            Request req = new Request(Utils.REQ.COMMENT, new Data( Utils.currentUser, post.getID() ,commentsTF.getText() ));
            NetworkManager.putRequest(req);

            Label comment = createCommentLabel(commentText);
            commentsVBox.getChildren().add(comment);
            commentsLabel.setText("" + post.getComments().size());
            commentsTF.setText("");
        }
    }

    public static Label createCommentLabel(String commentText)
    {
        Label comment = new Label(commentText);
        comment.setPrefSize(200,60);
        comment.setLayoutX(3);
        comment.setLayoutY(5);
        comment.setFont( new Font("System",16) );
        comment.setWrapText(true);
        return comment;
    }
}
