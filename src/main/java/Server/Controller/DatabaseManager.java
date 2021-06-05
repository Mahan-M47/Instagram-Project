package Server.Controller;

import Server.Model.User;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    MongoClient mongoClient;
    DB db;


    public DatabaseManager(String database) {
        mongoClient = new MongoClient();
        db = mongoClient.getDB(database);
    }

    public void insertToCollection(String collectionName, DBObject data) {
        DBCollection collection = db.getCollection(collectionName);
        collection.insert(data);
    }

    public List<User> getusers(String collectionName){
            DBCollection collection = db.getCollection(collectionName);
            DBCursor dbObjects = collection.find();
            List<User> Users = new ArrayList<>();
            for (DBObject dbObject : dbObjects) {
                Users.add(User.parsePlayer(dbObject));
            }
            return Users;
    }

    public User getPlayer(String collectionName, String username) {
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject obj = new BasicDBObject().append("username", username);
        DBObject one = collection.findOne(obj);
        return User.parsePlayer(one);
    }


}
