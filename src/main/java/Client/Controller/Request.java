package Client.Controller;

public class Request
{
    private long reqID;
    private String request;
    private Data data;

    public Request (String title, Data data) {
        this.request = title;
        this.data = data;
    }

}
