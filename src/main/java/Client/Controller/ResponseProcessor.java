package Client.Controller;

import java.util.concurrent.BlockingQueue;

public class ResponseProcessor implements Runnable
{
    // This thread takes Responses from a Blocking Queue and processes them using the Main Manager's Process method.
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
