package Client.Controller;

import java.util.concurrent.BlockingQueue;

public class Get implements Runnable
{
    // This Thread constantly listens for any response sent from the Server.
    private BlockingQueue<Response> queue;
    private ClientIO clientIO;

    public Get(BlockingQueue<Response> queue, ClientIO clientIO) {
        this.queue = queue;
        this.clientIO = clientIO;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Response response = clientIO.receiveFromServer();
                queue.put(response);
            }
            catch (InterruptedException | NullPointerException e) {
                break;
            }
        }
    }

}
