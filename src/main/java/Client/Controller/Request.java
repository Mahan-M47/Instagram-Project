package Client.Controller;

public class Request
{
    private String title;
    private Data data;

    public Request(String title) {
        this.title = title;
    }

    public Request(String title, Data data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() { return title; }

    public Data getData() { return data; }


    public static class Termination extends Request
    {
        public Termination() {
            super("terminate");
        }
    }
}
