package Server.Controller;

import java.util.concurrent.BlockingQueue;

public class Get implements Runnable
{
    private BlockingQueue<Request> queue;
    private ServerJsonHandler SJH;

    public Get(BlockingQueue<Request> queue) {
        this.queue = queue;
        SJH = new ServerJsonHandler();
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                queue.put( SJH.receiveFromClient() );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
