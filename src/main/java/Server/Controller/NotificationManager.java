package Server.Controller;

import Server.Model.Post;
import Server.Utils;
import java.util.ArrayList;
import java.util.Collections;

public class NotificationManager
{
    public static synchronized void followNotification(String newFollower, String followedUser)
    {
        Response response = new Response(Utils.REQ.NOTIF_FOLLOW, new Data(newFollower));

        for (ActiveClient client : MainManager.activeClients)
        {
            if ( client.getUsername().equals(followedUser) ) {
                sendNotification(client, response);
                System.out.println("FOLLOW");
                break;
            }
        }

    }

    public static synchronized void likeNotification(String likedBy, Post post)
    {
        if (! likedBy.equals(post.getUsername()) ) {
            Response response = new Response(Utils.REQ.NOTIF_LIKE, new Data(likedBy));

            for (ActiveClient client : MainManager.activeClients)
            {
                if ( client.getUsername().equals(post.getUsername()) ) {
                    sendNotification(client, response);
                    System.out.println("LIKE");
                    break;
                }
            }
        }
    }

    public static synchronized void commentNotification(String commentedBy, Post post)
    {
        if (! commentedBy.equals(post.getUsername()) )
        {
            Response response = new Response(Utils.REQ.NOTIF_COMMENT, new Data(commentedBy));

            for (ActiveClient client : MainManager.activeClients)
            {
                if ( client.getUsername().equals(post.getUsername()) ) {
                    sendNotification(client, response);
                    System.out.println("COMMENT");
                    break;
                }
            }
        }
    }

    public static synchronized void postNotification(String postedBy)
    {
        Response response = new Response(Utils.REQ.NOTIF_POST, new Data(postedBy));

        ArrayList<String> followers = DatabaseManager.getFollowData(postedBy).getFollowers();
        Collections.sort(followers);

        for (ActiveClient client : MainManager.activeClients)
        {
            int index = Collections.binarySearch(followers, client.getUsername());

            if (index != -1) {
                sendNotification(client, response);
                System.out.println("POST");
            }
        }
    }

    public static synchronized void messageNotification() {

    }

    public static void sendNotification(ActiveClient client, Response response)
    {
        try {
            client.getQueue().put(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
