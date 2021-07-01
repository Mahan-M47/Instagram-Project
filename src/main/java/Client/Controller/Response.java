package Client.Controller;

public class Response
{
    // Request Objects are messages sent from the Server to the Client, after being converted to Json Strings.
    private String title;
    private Data data;

    public Response(String title) {
        this.title = title;
    }

    public Response(String title, Data data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public Data getData() {
        return data;
    }
}
