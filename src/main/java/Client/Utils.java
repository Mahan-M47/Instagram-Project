package Client;

import Client.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils
{
    //the currently logged in user
    public static String currentUser = "";
    public static User currentUserObj = new User("mahan", "1234", "Hey! It's Mahan!");


    //General constants
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 6000;
    public static final int BLOCKING_QUEUE_CAPACITY = 20;
    public static final long MAIN_SLEEP_TIMER = 100;
    public static final long DISCONNECT_SLEEP_TIMER = 1000;


    //GUI stuff
    public static String SIGNUP_ERROR_TEXT = "";
    public static String LOGIN_ERROR_TEXT = "";

    public static void resetErrorTexts() {
        SIGNUP_ERROR_TEXT = "";
        LOGIN_ERROR_TEXT = "";
    }

    public static User receivedUser =  new User("taylor", "1234", "folklore.");
    public static List<String> searchResults = new ArrayList<>();
}
