package Server.Controller;

import Server.Model.Post;
import Server.Model.PostImage;
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

    public synchronized static void adduser(User user)
    {
        DBCollection login = db.getCollection(Utils.DB_LOGIN);
        login.insert(user.createLoginDBObject());

        DBCollection follow = db.getCollection(Utils.DB_FOLLOW);
        follow.insert(user.createFollowDBObject());

        DBCollection Bio = db.getCollection(Utils.DB_BIO);
        Bio.insert(user.createBioDBObject());

    }

    public synchronized static void post(Post post){
        DBCollection postcollection = db.getCollection(Utils.DB_POST);
        postcollection.insert(post.getDBObjectpost());
    }

    public synchronized static void like(String postid , String username){
        DBCollection postcollection = db.getCollection(Utils.DB_POST);
        DBObject object = new BasicDBObject()
                .append("ID",postid);
        object = postcollection.findOne(object);
        PostImage post = Post.parsepost(object);
        post.addLike(username);
        postcollection.update(object,post.getDBObjectpost());
    }

    public synchronized static void unlike(String postid , String username){
        DBCollection postcollection = db.getCollection(Utils.DB_POST);
        DBObject object = new BasicDBObject()
                .append("ID",postid);
        object = postcollection.findOne(object);
        PostImage post = Post.parsepost(object);
        post.removeLike(username);
        postcollection.update(object,post.getDBObjectpost());
    }

    public synchronized static void comment(String username , String postid , String commenttext){
        DBCollection postcollection = db.getCollection(Utils.DB_POST);
        DBObject object = new BasicDBObject()
                .append("ID",postid);
        object = postcollection.findOne(object);
        PostImage post = Post.parsepost(object);
        String comment = username + " : " + commenttext ;
        post.addComment(comment);
        postcollection.update(object,post.getDBObjectpost());
    }

    public synchronized static ArrayList<String> getUsers(String collectionName)
    {
        DBCollection collection = db.getCollection(collectionName);
        DBCursor cursor = collection.find();
        ArrayList<String> Users = new ArrayList<>();

        for (DBObject query : cursor) {
            Users.add( User.parseLoginDBObject(query).getUsername() );
        }
        return Users;
    }

    public synchronized static User getUser(String collectionName, String username) {
        DBCollection collection = db.getCollection(collectionName);
        if(checkIfUserExists(collectionName,username)) {
            BasicDBObject obj = new BasicDBObject().append("username", username);
            DBObject one = collection.findOne(obj);
            return User.parseLoginDBObject(one);
        }
        else{
            return null ;
        }
    }

    public synchronized static boolean checkIfUserExists(String collectionName, String username)
    {
        DBCollection collection = db.getCollection(collectionName);
        DBObject query = new BasicDBObject("username", username);
        DBObject existingQuery = collection.findOne(query);

        if (existingQuery == null){
            return false;
        }
        else {
            return true;
        }
    }

    public synchronized static boolean checkLogin(String username, String password)
    {
        DBCollection collection = db.getCollection(Utils.DB_LOGIN);
        User user = new User(username, password);

        DBObject query = collection.findOne( user.createLoginDBObject() );
        if (query == null){
            return false ;
        }
        else {
            return true ;
        }
    }

    public synchronized static ArrayList<String> searchUser(String username)
    {
        ArrayList<String> allUsers = getUsers(Utils.DB_LOGIN);
        ArrayList<String> results = new ArrayList<>();

        for (String str : allUsers) {
            if (str.contains(username)) {
                results.add(str);
            }
        }
        return results;
    }

    public synchronized static void follow(String followerUsername, String followingUsername)
    {
        DBCollection collection = db.getCollection(Utils.DB_FOLLOW);
        User followerUser  = getFollowData(followerUsername);
        User followingUser = getFollowData(followingUsername);

        followerUser.addFollowing(followingUsername);
        followingUser.addFollower(followerUsername);

        DBObject followingUserQuery = new BasicDBObject("username", followingUser.getUsername());
        collection.update(followingUserQuery, followingUser.createFollowDBObject());

        DBObject followerUserQuery = new BasicDBObject("username", followerUser.getUsername());
        collection.update(followerUserQuery, followerUser.createFollowDBObject());
    }

    public synchronized static void unfollow(String followerUsername, String followingUsername)
    {
        DBCollection collection = db.getCollection(Utils.DB_FOLLOW);
        User followerUser  = getFollowData(followerUsername);
        User followingUser = getFollowData(followingUsername);

        followerUser.removeFollowing(followingUsername);
        followingUser.removeFollowers(followerUsername);

        DBObject followingUserQuery = new BasicDBObject("username", followingUser.getUsername());
        collection.update(followingUserQuery, followingUser.createFollowDBObject());

        DBObject followerUserQuery = new BasicDBObject("username", followerUser.getUsername());
        collection.update(followerUserQuery, followerUser.createFollowDBObject());
    }

    public synchronized static User assembleUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setBioText  ( getBioData(username).getBioText() );
        user.setFollowers( getFollowData(username).getFollowers() );
        user.setFollowing( getFollowData(username).getFollowing() );
        user.setPosts(getposts(username));
        return user;
    }

    public synchronized static ArrayList<Post> getposts(String username){
        DBCollection postcollection = db.getCollection(Utils.DB_POST);
        DBObject object = new BasicDBObject()
                .append("username",username);
        ArrayList<Post> posts = new ArrayList<>();
        DBCursor Cursor = postcollection.find(object);
        for (DBObject dbObject : Cursor) {
            posts.add(Post.parsepost(dbObject));
        }
        return posts;
    }

    public synchronized static User getFollowData(String username) {
        DBCollection collection = db.getCollection(Utils.DB_FOLLOW);
        DBObject query = new BasicDBObject("username", username);
        query = collection.findOne(query);
        return User.parseFollowDBObject(query);
    }

    public synchronized static User getBioData(String username) {
        DBCollection collection = db.getCollection(Utils.DB_BIO);
        DBObject query = new BasicDBObject("username", username);
        query = collection.findOne(query);
        return User.parseBioDBObject(query);
    }

    public synchronized static void setBio(String username , String bioText)
    {
        DBCollection collection = db.getCollection(Utils.DB_BIO);
        User user = getBioData(username);
        user.setBioText(bioText);

        DBObject query = new BasicDBObject("username", username);
        collection.update(query, user.createBioDBObject());
    }
}

