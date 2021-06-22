package Server;

public class Utils
{
    public static final int PORT = 6000;
    public static final int BLOCKING_QUEUE_CAPACITY = 20;
    public static final long MONITOR_SLEEP_TIMER = 1000;


    //Database
    public static final String DATABASE_NAME = "instagram";
    public static final String DB_LOGIN = "login";
    public static final String DB_FOLLOW = "follow";
    public static final String DB_BIO = "bio";


    //Request titles
    public static class REQ {
        public static final String LOGIN = "login";
        public static final String SIGNUP = "signup";
        public static final String SEARCH = "search";
        public static final String MY_PROFILE = "showMyProfile";
        public static final String PROFILE = "showProfile";
        public static final String FOLLOW = "follow";
        public static final String UNFOLLOW = "unfollow";
        public static final String BIO = "setBio";
        public static final String LOGOUT = "logout";
        public static final String LIKE = "like";
        public static final String UNLIKE = "unlike";
        public static final String COMMENT = "comment";
        public static final String CREATE_POST = "createPost";
        public static final String TIMELINE = "getTimeline";
        public static final String TERMINATE = "terminate";
    }
}
