package Client.Controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NetworkManager
{
    private BlockingQueue<Response> queue;
    private ClientJsonHandler CJH;
    private Thread getThread, processThread;

    public NetworkManager() {
        queue = new ArrayBlockingQueue<>(20);
        CJH = new ClientJsonHandler();
    }

    public void startClient()
    {
        Get get = new Get(queue, CJH);
        getThread = new Thread(get);

        ResponseProcessor reqProcessor = new ResponseProcessor(queue);
        processThread = new Thread(reqProcessor);

        getThread.start();
        processThread.start();
    }

    public void stopClient() {
        CJH.close();
        getThread.interrupt();
        processThread.interrupt();
    }
}
