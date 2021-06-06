package Client.Controller;

import java.util.concurrent.BlockingQueue;

public class Send implements Runnable
{
    private BlockingQueue<Request> queue;
    private ClientIO clientIO;

    public Send(ClientIO clientIO) {
        this.queue = NetworkManager.queueRequest;
        this.clientIO = clientIO;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Request req = queue.take();
                clientIO.sendToServer(req);
            }
            catch (InterruptedException | NullPointerException e) {
                break;
            }
        }
    }

}