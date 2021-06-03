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
                process(response);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void process(Response response) {
        switch ( response.getTitle() ) {
            case "basicResponse":
                System.out.println("basic");
        }
        //each case in switch statement calls a method from GUIManager.

    }
}
