package Server.Controller;

import Server.Model.User;
import Server.Utils;
import com.mongodb.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static MongoClient mongoClient;
    private static DB db;

    public static void startDatabase(String databaseName) {
        mongoClient = new MongoClient();
        db = mongoClient.getDB(databaseName);
    }

    public synchronized static void adduser(User user) {
        DBCollection login = db.getCollection(Utils.DB_LOGIN);
        login.insert(user.getLoginDBObject());
        DBCollection follow = db.getCollection(Utils.DB_FOLLOW);
        follow.insert(user.getFollowDBObject());
        DBCollection Bio = db.getCollection(Utils.DB_BIO);
        Bio.insert(user.getBioDBObject());
    }

    public synchronized static ArrayList<String> getUsers(String collectionName) {
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
        DBCollection collection = db.getCollection(Utils.DB_LOGIN);
        DBObject one = collection.findOne(user.getLoginDBObject());
        if(one == null){
            return false ;
        }
        else{
            return true ;
        }
    }

    public synchronized static ArrayList<String> searchUser(String username) {
        ArrayList<String> allUsers = getUsers(Utils.DB_LOGIN);
        ArrayList<String> results = new ArrayList<>();

        for (String str : allUsers) {
            if (str.contains(username)) {
                results.add(str);
            }
        }
        return results;
    }

    public synchronized static void follow(String befollowing , String befollower){
        User following = getFollowing(befollowing);
        User follower = getFollowing(befollower);
        if(!(following.checkexistinfollowers(befollower) && follower.checkexistinfollowing(befollowing))) {
            following.addFollowers(befollower);
            follower.addFollowing(befollowing);
            DBCollection follow2 = db.getCollection(Utils.DB_FOLLOW);
            DBObject user = new BasicDBObject().append("username", following.getUsername());
            follow2.update(user, following.getFollowDBObject());
            DBObject user2 = new BasicDBObject().append("username", follower.getUsername());
            follow2.update(user2, follower.getFollowDBObject());
        }
    }

    public synchronized static void unfollow(String befollowing , String befollower){
        User following = getFollowing(befollowing);
        User follower = getFollowing(befollower);
        if(!(following.checkexistinfollowers(befollower) && follower.checkexistinfollowing(befollowing))) {
            following.removeFollowers(befollower);
            follower.removeFollowing(befollowing);
            DBCollection follow2 = db.getCollection(Utils.DB_FOLLOW);
            DBObject user = new BasicDBObject().append("username", following.getUsername());
            follow2.update(user, following.getFollowDBObject());
            DBObject user2 = new BasicDBObject().append("username", follower.getUsername());
            follow2.update(user2, follower.getFollowDBObject());
        }
    }

    public synchronized static User assembleUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setBioText  ( getBio(username).getBioText() );
        user.setFollowers( getFollowing(username).getFollowers() );
        user.setFollowing( getFollowing(username).getFollowing() );
        return user;
    }

    public synchronized static User getFollowing(String username) {
        DBCollection collection = db.getCollection(Utils.DB_FOLLOW);
        if(checkIfUserExists(Utils.DB_FOLLOW,username)) {
            BasicDBObject obj = new BasicDBObject().append("username", username);
            DBObject one = collection.findOne(obj);
            return User.parseFollow(one);
        }
        else{
            return null ;
        }
    }

    public synchronized static User getBio (String username)
    {
        DBCollection collection = db.getCollection(Utils.DB_BIO);
        DBObject query = new BasicDBObject("username", username);
        query = collection.findOne(query);
        return User.parseBio(query);

    }

    public synchronized static void setBio(String username , String bioText)
    {
        DBCollection collection = db.getCollection(Utils.DB_BIO);
        DBObject query = new BasicDBObject("username", username);
        query = collection.findOne(query);

        User user = User.parseBio(query);
        user.setBioText(bioText);

        DBObject updatedQuery = user.getBioDBObject();
        collection.findAndModify(query, updatedQuery);
    }
}

