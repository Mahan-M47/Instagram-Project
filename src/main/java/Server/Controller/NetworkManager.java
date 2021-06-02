package Server.Controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NetworkManager
{
    private BlockingQueue<Request> queue;

    public NetworkManager() {
        queue = new ArrayBlockingQueue<>(5);
    }

    public void startServer()
    {
        Get get = new Get(queue);
        Thread getThread = new Thread(get);

        RequestProcessor reqProcessor = new RequestProcessor(queue);
        Thread processThread = new Thread(reqProcessor);

        getThread.start();
        processThread.start();
    }

    public void send() {

    }

}
