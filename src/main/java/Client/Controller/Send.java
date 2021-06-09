package Client.Controller;

import java.util.concurrent.BlockingQueue;

public class Send implements Runnable
{
    private BlockingQueue<Request> queue;
    private ClientIO clientIO;

    public Send(BlockingQueue<Request> queue, ClientIO clientIO) {
        this.queue = queue;
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