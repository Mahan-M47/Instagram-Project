package Server.Controller;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    MongoClient mongoClient;
    DB db;


    public DatabaseManager(String database) throws UnknownHostException {
        mongoClient = new MongoClient();
        db = mongoClient.getDB(database);
    }

    public void insertToCollection(String collectionName, DBObject data) {
        DBCollection collection = db.getCollection(collectionName);
        collection.insert(data);
    }

    public List<user> getusers(String collectionName){
            DBCollection collection = db.getCollection(collectionName);
            DBCursor dbObjects = collection.find();
            List<user> users = new ArrayList<>();
            for (DBObject dbObject : dbObjects) {
                users.add(user.parsePlayer(dbObject));
            }
            return users ;
    }

    public user getPlayer(String collectionName, String username) {
        DBCollection collection = db.getCollection(collectionName);
        BasicDBObject obj = new BasicDBObject().append("username", username);
        DBObject one = collection.findOne(obj);
        return user.parsePlayer(one);
    }


}
