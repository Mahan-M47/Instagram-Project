package Client;

import Client.Model.ChatPersonal;
import Client.Model.Post;
import Client.Model.User;
import java.util.ArrayList;
import java.util.List;

public class Utils
{
    //the currently logged in user
    public static String currentUser = "";
    public static User currentUserObj = null;
    public static List<ChatPersonal> personalChats = null;

    //General constants
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 6000;
    public static final int BLOCKING_QUEUE_CAPACITY = 20;
    public static final long MAIN_SLEEP_TIMER = 100;
    public static final long DISCONNECT_SLEEP_TIMER = 1000;


    //GUI stuff
    public static String SIGNUP_ERROR_TEXT = "";
    public static String LOGIN_ERROR_TEXT = "";
    public static String SEARCH_ERROR_TEXT = "";

    public static void resetErrorTexts() {
        SIGNUP_ERROR_TEXT = "";
        LOGIN_ERROR_TEXT = "";
        SEARCH_ERROR_TEXT = "";
    }

    public static User receivedUserObj = null;
    public static List<String> searchResults = new ArrayList<>();
    public static List<Post> timelineData = new ArrayList<>();


    //GUI page titles
    public static class GUI {
        public static final String CREATE_POST = "CreatePostPage";
        public static final String EDIT_PROFILE = "EditProfilePage";
        public static final String FOLLOWERS = "FollowersPage";
        public static final String FOLLOWING = "FollowingPage";
        public static final String LOGIN = "LoginPage";
        public static final String MY_FOLLOWERS = "MyFollowersPage";
        public static final String MY_FOLLOWING = "MyFollowingPage";
        public static final String MY_PROFILE = "MyProfilePage";
        public static final String PROFILE = "ProfilePage";
        public static final String SEARCH = "SearchPage";
        public static final String SIGNUP = "SignUpPage";
        public static final String TIMELINE = "Timeline";
        public static final String MY_POST = "ViewMyPost";
        public static final String POST = "ViewPost";
    }


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
