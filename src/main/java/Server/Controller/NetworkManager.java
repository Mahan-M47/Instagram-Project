package Server.Controller;

import Server.Utils;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkManager implements Runnable
{
    private BlockingQueue<Request>  queueRequest;
    private BlockingQueue<Response> queueResponse;
    private ServerIO serverIO;
    private Thread getThread, processThread, sendThread;
    private AtomicBoolean state;

    public NetworkManager(Socket socket) {
        queueRequest  = new ArrayBlockingQueue<>(Utils.BLOCKING_QUEUE_CAPACITY);
        queueResponse = new ArrayBlockingQueue<>(Utils.BLOCKING_QUEUE_CAPACITY);
        serverIO = new ServerIO(socket);
        state = new AtomicBoolean(true);
    }

    @Override
    public void run()
    {
        Get get = new Get(queueRequest, serverIO);
        getThread = new Thread(get);

        RequestProcessor reqProcessor = new RequestProcessor(queueRequest, queueResponse, state);
        processThread = new Thread(reqProcessor);

        Send send = new Send(queueResponse, serverIO);
        sendThread = new Thread(send);

        getThread.start();
        processThread.start();
        sendThread.start();

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

    public void stopServer()
    {
        getThread.interrupt();
        processThread.interrupt();
        sendThread.interrupt();

        serverIO.close();
        MainManager.removeClient(queueResponse);

        System.out.println("A Client Disconnected From Server.");
    }
}
