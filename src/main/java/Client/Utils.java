package Client;

import Client.Model.GroupChat;
import Client.Model.PersonalChat;
import Client.Model.Post;
import Client.Model.User;
import java.util.ArrayList;
import java.util.List;

public class Utils
{
    //the currently logged in user
    public static String currentUser = "";
    public static User currentUserObj = null;
    public static List<Post> timelineData = new ArrayList<>();
    public static List<PersonalChat> personalChats = new ArrayList<>();
    public static List<GroupChat> groupChats = new ArrayList<>();


    //Received User
    public static User receivedUserObj = null;
    public static List<String> searchResults = new ArrayList<>();


    //Network constants
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 6000;
    public static final int BLOCKING_QUEUE_CAPACITY = 20;
    public static final long MAIN_SLEEP_TIMER = 100;
    public static final long DISCONNECT_SLEEP_TIMER = 500;

    //Key Bytes Was generated using the keyGenerator class in another program. the Key Bytes for Client and Server are identical.
    public static final byte[] ENCRYPTION_KEY_BYTES = { -85 ,-83 ,-6 ,82 ,93 ,125 ,-36 ,-35 ,-104 ,33 ,4 ,96, 49 ,-75,
            -125, 113, -96, 114, -51, -109, 18, 33, -98, 27, 18, -79, -39, 65, 71, 76, 42, -116 };


    //Post types
    public static final String POST_IMAGE = ".jpg";
    public static final String POST_VIDEO = ".mp4";


    //Maximum file size
    public static final int POST_FILE_MAX_SIZE = 15000000;
    public static final int MESSAGE_FILE_MAX_SIZE = 1000000;


    //PlayButton CSS string
    public static final String PLAY_BUTTON_CSS = "  -fx-background-color: #ffffff;" +
            "  -fx-background-radius: 15;" +
            "  -fx-opacity: 0.6;" +
            "  -fx-font: 40pt \"System\";" +
            "  -fx-font-weight: bold;";


    //Error texts
    public static String SIGNUP_ERROR_TEXT = "";
    public static String LOGIN_ERROR_TEXT = "";
    public static String SEARCH_ERROR_TEXT = "";
    public static String ADD_MEMBER_ERROR_TEXT = "";

    public static void resetErrorTexts() {
        SIGNUP_ERROR_TEXT = "";
        LOGIN_ERROR_TEXT = "";
        SEARCH_ERROR_TEXT = "";
        ADD_MEMBER_ERROR_TEXT = "";
    }


    //File directories
    public static final String DIR_SERVER_PROFILE_PICTURES = "src/main/java/Server/Resources/Profile_Pictures/";
    public static final String DIR_SERVER_POSTS = "src/main/java/Server/Resources/";
    public static final String DIR_CLIENT_POST_VIDEOS = "src/main/java/Client/Resources/Post_Videos/";


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
        public static final String CHAT_LIST = "ChatList";
        public static final String CHAT_PAGE = "ChatPage";
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
        public static final String DELETE_POST = "deletePost";
        public static final String TIMELINE = "getTimeline";
        public static final String NOTIFICATION = "notification";
        public static final String PERSONAL_CHAT = "getPersonalChat";
        public static final String GROUP_CHAT = "getGroupChat";
        public static final String ALL_CHATS = "getAllChats";
        public static final String MESSAGE = "addMessage";
        public static final String ADD_MEMBER = "addMember";
        public static final String TERMINATE = "terminate";
    }

}
