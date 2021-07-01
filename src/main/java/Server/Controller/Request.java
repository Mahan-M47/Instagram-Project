package Server.Controller;

import Server.Utils;

public class Request
{
    // Request Objects are messages sent from the Client to the Server, after being converted to Json Strings.
    private String title;
    private Data data = null;

    public Request (String title) {
        this.title = title;
    }

    public Request (String title, Data data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() { return title; }

    public Data getData() { return data; }


    public static class Termination extends Request
    {
        public Termination() {
            super(Utils.REQ.TERMINATE);
        }
    }
}
