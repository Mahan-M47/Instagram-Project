package Server;

import Server.Controller.DatabaseManager;
import Server.Controller.user;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        String tableName = "login";
        user player2 = new user("ali", "qwertyhn");
        String databaseName = "instagram";
        DatabaseManager mongo = new DatabaseManager(databaseName);
        mongo.insertToCollection(tableName, player2.getDBObject());

    }
}
