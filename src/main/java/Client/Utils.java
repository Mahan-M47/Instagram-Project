package Client;

import Client.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils
{
    //the currently logged in user
    public static String currentUser = "";
    public static User currentUserObj = null;


    //General constants
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 6000;
    public static final int BLOCKING_QUEUE_CAPACITY = 20;
    public static final long MAIN_SLEEP_TIMER = 100;
    public static final long DISCONNECT_SLEEP_TIMER = 1000;


    //GUI page titles
    public static final String GUI_CREATE_POST = "CreatePostPage";
    public static final String GUI_EDIT_PROFILE = "EditProfilePage";
    public static final String GUI_FOLLOWERS = "FollowersPage";
    public static final String GUI_FOLLOWING = "FollowingPage";
    public static final String GUI_LOGIN = "LoginPage";
    public static final String GUI_MY_FOLLOWERS = "MyFollowersPage";
    public static final String GUI_MY_FOLLOWING = "MyFollowingPage";
    public static final String GUI_MY_PROFILE = "MyProfilePage";
    public static final String GUI_PROFILE = "ProfilePage";
    public static final String GUI_SEARCH = "SearchPage";
    public static final String GUI_SIGN_UP = "SignUpPage";
    public static final String GUI_TIMELINE = "Timeline";


    //GUI stuff
    public static String SIGNUP_ERROR_TEXT = "";
    public static String LOGIN_ERROR_TEXT = "";

    public static void resetErrorTexts() {
        SIGNUP_ERROR_TEXT = "";
        LOGIN_ERROR_TEXT = "";
    }

    public static User receivedUserObj = null;
    public static List<String> searchResults = new ArrayList<>();
}
