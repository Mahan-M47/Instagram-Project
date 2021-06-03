package Client.Controller;

import java.util.concurrent.BlockingQueue;

public class Get implements Runnable
{
    private BlockingQueue<Response> queue;
    private ClientJsonHandler CJH;

    public Get(BlockingQueue<Response> queue, ClientJsonHandler CJH) {
        this.queue = queue;
        this.CJH = CJH;
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
