package Server.Controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestProcessor implements Runnable
{
    // This thread takes Requests from a Blocking Queue and processes them using the Main Manager's Process method.
    private BlockingQueue<Request> queueRequest;
    private BlockingQueue<Response> queueResponse;
    private AtomicBoolean state;

    public RequestProcessor(BlockingQueue<Request> queueRequest, BlockingQueue<Response> queueResponse, AtomicBoolean state)
    {
        this.queueRequest = queueRequest;
        this.queueResponse = queueResponse;
        this.state = state;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Request req = queueRequest.take();
                Response response = MainManager.process(req, state);
                if (response != null) {
                    queueResponse.put(response);
                }
                else System.out.println("Response Was null For A Request.");
            }
            catch (InterruptedException | NullPointerException e) {
                break;
            }
        }
    }

}
