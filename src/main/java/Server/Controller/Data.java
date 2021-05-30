package Server.Controller;

public class Data
{
    public static class Login extends Data
    {
        public String username, password;
        public Login(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public static class Follow extends Data
    {
        public String username;
        public Follow(String username) {
            this.username = username;
        }
    }

    public static class SetBio extends Data
    {
        public String bioText;
        public SetBio(String bioText) {
            this.bioText = bioText;
        }
    }

    public static class Heartbeat extends Data
    {
        public Heartbeat() {}
    }

    // need to add more...
}
