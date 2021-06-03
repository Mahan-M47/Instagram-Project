package Client.Controller;

public abstract class Data
{
    public String clientUsername;

    public static class BasicRequest extends Data
    {
        public String str;
        public BasicRequest(String clientUsername, String str) {
            super.clientUsername = clientUsername;
            this.str = str;
        }
    }

    public static class Login extends Data
    {
        public String username, password;
        public Login(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }


    public static class Heartbeat extends Data
    {
        public Heartbeat() {}
    }

    // need to add more...
}
