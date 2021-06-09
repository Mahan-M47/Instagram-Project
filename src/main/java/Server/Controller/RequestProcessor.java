package Server.Controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestProcessor implements Runnable
{
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
                queueResponse.put(response);
            }
            catch (InterruptedException | NullPointerException e) {
                break;
            }
        }
    }

}
