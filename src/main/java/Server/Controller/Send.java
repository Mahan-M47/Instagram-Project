package Server.Controller;

import java.util.concurrent.BlockingQueue;

public class Send implements Runnable
{
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
        if ( response.getTitle().equals("login") || response.getTitle().equals("signup") )
        {
            if (response.getData().flag) {
                ActiveClient client = new ActiveClient(response.getData().clientUsername, queue);
                MainManager.addClient(client);
            }
        }
    }

}
