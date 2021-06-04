package Server.Controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkManager
{
    private BlockingQueue<Request> queue;
    private ServerJsonHandler SJH;
    private Thread getThread, processThread;
    private AtomicBoolean state;


    public NetworkManager(ServerJsonHandler SJH) {
        this.SJH = SJH;
        queue = new ArrayBlockingQueue<>(20);
        state = new AtomicBoolean(true);
    }

    public void startServer()
    {
        Get get = new Get(queue, SJH);
        getThread = new Thread(get);

        RequestProcessor reqProcessor = new RequestProcessor(queue, SJH);
        processThread = new Thread(reqProcessor);

        getThread.start();
        processThread.start();
    }

    public void stopServer() {
        SJH.close();
        getThread.interrupt();
        processThread.interrupt();
    }



}
