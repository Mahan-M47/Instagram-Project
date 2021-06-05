package Server.Controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestProcessor implements Runnable
{
    private BlockingQueue<Request> queue;
    private ServerJsonHandler SJH;
    private DatabaseManager mongo;
    private AtomicBoolean state;

    public RequestProcessor(BlockingQueue<Request> queue, ServerJsonHandler SJH,
                            DatabaseManager mongo, AtomicBoolean state)
    {
        this.queue = queue;
        this.SJH = SJH;
        this.mongo = mongo;
        this.state = state;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Request req = queue.take();
                SJH.sendToClient( process(req) );
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Response process(Request req)
    {
        switch ( req.getTitle() ) {
            case "login":
                return null;
            case "follow":
                return null;
            case "setBio":
                return null;
            case "terminate":
                state.set(false);
        }

        return null;

        //each Request.title corresponds to a case in the switch statement which summons a method from mongo
        //to process the Request. These methods should all return a Response object which is sent back to the client.
    }
}
