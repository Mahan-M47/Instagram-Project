package Server.Controller;

import java.util.concurrent.BlockingQueue;

public class RequestProcessor implements Runnable
{
    private BlockingQueue<Request> queue;

    public RequestProcessor(BlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true)
        {
            try {
                Request req = queue.take();
                process(req);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void process(Request req) {
        switch ( req.getTitle() ) {
            case "login":
                System.out.println("login");
                break;
            case "follow":
                System.out.println("follow");
                break;
            case "setBio":
                System.out.println("setBio");
                break;
            default:
                System.out.println("hehehe");
        }
    }
}
