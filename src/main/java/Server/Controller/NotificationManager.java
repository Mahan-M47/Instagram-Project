package Server.Controller;

import Server.Model.Notification;
import Server.Model.Post;
import Server.Utils;
import java.util.ArrayList;
import java.util.Collections;

public class NotificationManager
{
    // This class contains method necessary for sending Notifications using the Active Clients List in Main Manager.
    public static synchronized void followNotification(String newFollower, String followedUser)
    {
        Notification notification = new Notification("New Follower!", newFollower + " Followed You");
        Response response = new Response( Utils.REQ.NOTIFICATION, new Data(notification) );

        for (ActiveClient client : MainManager.activeClients)
        {
            if ( client.getUsername().equals(followedUser) ) {
                sendNotification(client, response);
                System.out.println("Follow Notification Sent.");
                break;
            }
        }

    }

    public static synchronized void likeNotification(String likedBy, Post post)
    {
        if (! likedBy.equals(post.getUsername()) )
        {
            Notification notification = new Notification("New Like!", likedBy + " Liked Your Post");
            Response response = new Response( Utils.REQ.NOTIFICATION, new Data(notification) );

            for (ActiveClient client : MainManager.activeClients)
            {
                if ( client.getUsername().equals(post.getUsername()) ) {
                    sendNotification(client, response);
                    System.out.println("Like Notification Sent.");
                    break;
                }
            }
        }
    }

    public static synchronized void commentNotification(String commentedBy, Post post)
    {
        if (! commentedBy.equals(post.getUsername()) )
        {
            Notification notification = new Notification("New Comment!", commentedBy + " Commented on Your Post");
            Response response = new Response( Utils.REQ.NOTIFICATION, new Data(notification) );

            for (ActiveClient client : MainManager.activeClients)
            {
                if ( client.getUsername().equals(post.getUsername()) ) {
                    sendNotification(client, response);
                    System.out.println("Comment Notification Sent.");
                    break;
                }
            }
        }
    }

    public static synchronized void postNotification(String postedBy)
    {
        Notification notification = new Notification("New Post!", postedBy + " Just Made a New Post");
        Response response = new Response( Utils.REQ.NOTIFICATION, new Data(notification) );

        ArrayList<String> followers = DatabaseManager.getFollowData(postedBy).getFollowers();
        Collections.sort(followers);

        for (ActiveClient client : MainManager.activeClients)
        {
            int index = Collections.binarySearch(followers, client.getUsername());

            if (index > -1) {
                sendNotification(client, response);
                System.out.println("Post Notification Sent.");
            }
        }
    }

    public static synchronized void messageNotification(String sender, ArrayList<String> members)
    {
        Notification notification = new Notification("New Message!", sender + " Sent a Message");
        Response response = new Response( Utils.REQ.NOTIFICATION, new Data(notification) );

        members.remove(sender);
        Collections.sort(members);

        for (ActiveClient client : MainManager.activeClients)
        {
            int index = Collections.binarySearch(members, client.getUsername());

            if (index > -1) {
                sendNotification(client, response);
                System.out.println("Message Notification Sent.");
            }
        }
    }

    public static void sendNotification(ActiveClient client, Response response)
    {
        try {
            client.getQueue().put(response);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
