package Server.Controller;

import Server.Utils;
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
        queue = new ArrayBlockingQueue<>(Utils.BLOCKING_QUEUE_CAPACITY);
        state = new AtomicBoolean(true);
    }

    public void startServer()
    {
        Get get = new Get(queue, SJH);
        getThread = new Thread(get);

        RequestProcessor reqProcessor = new RequestProcessor(queue, SJH, state);
        processThread = new Thread(reqProcessor);

        getThread.start();
        processThread.start();
        System.out.println("Client Connected to Server.");

        monitor();
    }

    public void monitor() {
        while (state.get())
        {
            try {
                Thread.sleep(Utils.MONITOR_SLEEP_TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stopServer();
    }

    public void stopServer() {
        SJH.close();
        getThread.interrupt();
        processThread.interrupt();
        System.out.println("A Client disconnected From Server.");
    }

}
