package Server.Controller;

import Server.Model.User;
import Server.Utils;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static MongoClient mongoClient;
    private static DB db;
    private static ArrayList<String> Alluser = new ArrayList<>();
    private static ArrayList<String> Searchuser = new ArrayList<>();

    public static void startDatabase(String databaseName) {
        mongoClient = new MongoClient();
        db = mongoClient.getDB(databaseName);
    }

    public synchronized static void adduser(User user) {
            DBCollection collection = db.getCollection(Utils.LOGIN);
            collection.insert(user.getDBObject());
    }

    public synchronized static ArrayList<String> getUsers(String collectionName){
            DBCollection collection = db.getCollection(collectionName);
            DBCursor dbObjects = collection.find();
            ArrayList<String> Users = new ArrayList<>();
            for (DBObject dbObject : dbObjects) {
                Users.add(User.parseUser(dbObject).getUsername());
            }
            return Users;
    }

    public synchronized static User getUser(String collectionName, String username) {
        DBCollection collection = db.getCollection(collectionName);
        if(checkIfUserExists(collectionName,username)) {
            BasicDBObject obj = new BasicDBObject().append("username", username);
            DBObject one = collection.findOne(obj);
            return User.parseUser(one);
        }
        else{
            return null ;
        }
    }

    public synchronized static boolean checkIfUserExists(String collectionName, String username) {
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject obj = new BasicDBObject().append("username", username);
        DBObject one = collection.findOne(obj);
        if(one == null){
            return false ;
        }
        else {
            return true ;
        }
    }

    public synchronized static boolean checkLogin(User user) {
        DBCollection collection = db.getCollection(Utils.LOGIN);
        DBObject one = collection.findOne(user.getDBObject());
        if(one == null){
            return false ;
        }
        else{
            return true ;
        }
    }

    public synchronized static ArrayList<String> searchuser(String username){
        Alluser = getUsers(Utils.LOGIN);
        for(int i = 0 ; i < Alluser.size() ; i++){
            if(Alluser.get(i).contains(username)){
                Searchuser.add(Alluser.get(i));
            }
        }
        return Searchuser ;
    }

    

}
