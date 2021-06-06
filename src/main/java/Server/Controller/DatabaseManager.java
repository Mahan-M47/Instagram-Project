package Server.Controller;

import Server.Model.User;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static MongoClient mongoClient;
    private static DB db;

    public DatabaseManager(String database) {
        mongoClient = new MongoClient();
        db = mongoClient.getDB(database);
    }

    public synchronized static void insertToCollection(String collectionName, DBObject data) {
        DBCollection collection = db.getCollection(collectionName);
        collection.insert(data);
    }

    public synchronized static List<User> getUsers(String collectionName){
            DBCollection collection = db.getCollection(collectionName);
            DBCursor dbObjects = collection.find();
            List<User> Users = new ArrayList<>();
            for (DBObject dbObject : dbObjects) {
                Users.add(User.parseUser(dbObject));
            }
            return Users;
    }

    public synchronized static User getUser(String collectionName, String username) {
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject obj = new BasicDBObject().append("username", username);
        DBObject one = collection.findOne(obj);
        return User.parseUser(one);
    }


}
