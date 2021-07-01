package Server.Controller;

import java.util.concurrent.BlockingQueue;

public class ActiveClient
{
    // Active Client objects are used to store logged-in users and to send Notifications to Online users.
    private String username;
    private BlockingQueue<Response> queue;

    public ActiveClient(String username, BlockingQueue<Response> queue) {
        this.username = username;
        this.queue = queue;
    }

    public BlockingQueue<Response> getQueue() {
        return queue;
    }

    public String getUsername() {
        return username;
    }

}
