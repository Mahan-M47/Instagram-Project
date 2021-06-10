package Client.Controller;

import Client.Utils;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NetworkManager
{
    private static BlockingQueue<Request> queueRequest;
    private ClientIO clientIO;
    private BlockingQueue<Response> queueResponse;
    private Thread getThread, processThread, sendThread;


    public NetworkManager(Socket socket) {
        queueResponse = new ArrayBlockingQueue<>(Utils.BLOCKING_QUEUE_CAPACITY);
        queueRequest  = new ArrayBlockingQueue<>(Utils.BLOCKING_QUEUE_CAPACITY);
        clientIO = new ClientIO(socket);

        startClient();
    }

    public void startClient()
    {
        Get get = new Get(queueResponse, clientIO);
        getThread = new Thread(get);

        ResponseProcessor reqProcessor = new ResponseProcessor(queueResponse);
        processThread = new Thread(reqProcessor);

        Send send = new Send(queueRequest, clientIO);
        sendThread = new Thread(send);

        getThread.start();
        processThread.start();
        sendThread.start();
    }

    public static void putRequest(Request req) {
        try {
            queueRequest.put(req);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void stopClient() {
        getThread.interrupt();
        processThread.interrupt();
        sendThread.interrupt();
        clientIO.close();
    }
}
