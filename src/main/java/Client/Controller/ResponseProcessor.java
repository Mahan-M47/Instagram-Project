package Client.Controller;

import java.util.concurrent.BlockingQueue;

public class ResponseProcessor implements Runnable
{
    private BlockingQueue<Response> queue;

    public ResponseProcessor(BlockingQueue<Response> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Response response = queue.take();
                MainManager.process(response);
            } catch (InterruptedException | NullPointerException e) {
                break;
            }
        }
    }

}
