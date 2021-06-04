package Client.Controller;

import Client.Utils;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NetworkManager
{
    private BlockingQueue<Response> queue;
    private ClientJsonHandler CJH;
    private Thread getThread, processThread;

    public NetworkManager(ClientJsonHandler CJH) {
        queue = new ArrayBlockingQueue<>(Utils.BLOCKING_QUEUE_CAPACITY);
        this.CJH = CJH;
    }

    public void startClient()
    {
        Get get = new Get(queue, CJH);
        getThread = new Thread(get);

        ResponseProcessor reqProcessor = new ResponseProcessor(queue);
        processThread = new Thread(reqProcessor);

        getThread.start();
        processThread.start();

        //for testing purposes:
//        Request req = new Request("login", new Data.Empty());
//        while (true) {
//            CJH.sendToServer(req);
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        Request req2 = new Request("login");
//        CJH.sendToServer(req2);
//        CJH.sendToServer(req2);
//        CJH.sendToServer(req2);
//
//        Request req = new Request.Termination();
//        CJH.sendToServer(req);

    }

    public void stopClient() {
        CJH.close();
        getThread.interrupt();
        processThread.interrupt();
    }
}
