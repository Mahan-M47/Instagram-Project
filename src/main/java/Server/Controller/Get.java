package Server.Controller;

import java.util.concurrent.BlockingQueue;

public class Get implements Runnable
{
    private BlockingQueue<Request> queue;
    private ServerJsonHandler SJH;

    public Get(BlockingQueue<Request> queue, ServerJsonHandler SJH) {
        this.queue = queue;
        this.SJH = SJH;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                queue.put( SJH.receiveFromClient() );
                System.out.println("Received a Request From Client.");
            } catch (InterruptedException | NullPointerException e) {
                break;
            }
        }
    }

}
