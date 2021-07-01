package Server.Controller;

import Server.Utils;
import java.util.concurrent.BlockingQueue;

public class Send implements Runnable
{
    // This Thread is always ready to transmit Requests to the Client.
    private BlockingQueue<Response> queue;
    private ServerIO serverIO;

    public Send(BlockingQueue<Response> queue, ServerIO serverIO) {
        this.queue = queue;
        this.serverIO = serverIO;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Response response = queue.take();
                submitActiveClient(response);
                serverIO.sendToClient(response);
                System.out.println("Sent a Response to Client.");
            }
            catch (InterruptedException | NullPointerException e) {
                break;
            }
        }
    }

    public void submitActiveClient(Response response)
    {
        if ( response.getTitle().equals(Utils.REQ.LOGIN) || response.getTitle().equals(Utils.REQ.SIGNUP) )
        {
            if (response.getData().flag) {
                ActiveClient client = new ActiveClient(response.getData().clientUsername, queue);
                MainManager.addClient(client);
            }
        }
    }

}
