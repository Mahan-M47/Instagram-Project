package Server.Controller;

import java.util.concurrent.BlockingQueue;

public class RequestProcessor implements Runnable
{
    private BlockingQueue<Request> queue;
    private ServerJsonHandler SJH;

    public RequestProcessor(BlockingQueue<Request> queue, ServerJsonHandler SJH) {
        this.queue = queue;
        this.SJH = SJH;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Request req = queue.take();
//                process(req);
                SJH.sendToClient( process(req) );
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Response process(Request req) {
        switch ( req.getTitle() ) {
            case "login":
                System.out.println("login");
                return new Response("basicResponse",new Data.Basic("hey","hello"));
            case "follow":
                System.out.println("follow");
                break;
            case "setBio":
                System.out.println("setBio");
                break;
        }
        return null;
    }
}
