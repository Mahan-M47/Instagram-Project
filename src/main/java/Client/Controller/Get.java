package Client.Controller;

import java.util.concurrent.BlockingQueue;

public class Get implements Runnable
{
    private BlockingQueue<Response> queue;
    private ClientJsonHandler CJH;

    public Get(BlockingQueue<Response> queue) {
        this.queue = queue;
        this.CJH = NetworkManager.CJH;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                queue.put( CJH.receiveFromServer() );
            } catch (InterruptedException | NullPointerException e) {
                break;
            }
        }
    }

}
