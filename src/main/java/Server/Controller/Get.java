package Server.Controller;

import java.util.concurrent.BlockingQueue;

public class Get implements Runnable
{
    // This Thread constantly listens for any response sent from the Client.
    private BlockingQueue<Request> queue;
    private ServerIO serverIO;

    public Get(BlockingQueue<Request> queue, ServerIO serverIO) {
        this.queue = queue;
        this.serverIO = serverIO;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Request req = serverIO.receiveFromClient();
                queue.put(req);
                System.out.println("Received a Request From Client.");
            }
            catch (InterruptedException | NullPointerException e) {
                break;
            }
        }
    }

}
