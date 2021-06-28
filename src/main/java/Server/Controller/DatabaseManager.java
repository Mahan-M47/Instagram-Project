package Server.Controller;

import Server.Model.Post;
import Server.Model.User;
import Server.Utils;
import com.mongodb.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

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

    public synchronized static void createPost(Post post)
    {
        DBCollection collection = db.getCollection(Utils.DB_POST);

        try {
            Files.createDirectories( Paths.get(Utils.DIR_POSTS) );
            File savedFile = new File( post.getServerFilePath() );
            FileOutputStream out = new FileOutputStream( savedFile );
            out.write( post.getFileBytes() );
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        post.emptyFileBytes();
        collection.insert(post.createPostDBObject());
    }

    public synchronized static Post like(String username, String postID)
    {
        DBCollection collection = db.getCollection(Utils.DB_POST);
        DBObject query = new BasicDBObject("ID",postID);
        DBObject object = collection.findOne(query);

        Post post = Post.parsePost(object);
        post.addLike(username);
        collection.update(query, post.createPostDBObject());

        return post;
    }

    public synchronized static void unlike(String username, String postID)
    {
        DBCollection collection = db.getCollection(Utils.DB_POST);
        DBObject query = new BasicDBObject("ID",postID);
        DBObject object = collection.findOne(query);

        Post post = Post.parsePost(object);
        post.removeLike(username);
        collection.update(query, post.createPostDBObject());
    }

    public synchronized static Post comment(String username, String postID, String commentText)
    {
        DBCollection collection = db.getCollection(Utils.DB_POST);
        DBObject query = new BasicDBObject("ID",postID);
        DBObject object = collection.findOne(query);

        Post post = Post.parsePost(object);
        String comment = username + ": " + commentText;
        post.addComment(comment);
        collection.update(query, post.createPostDBObject());

        return post;
    }

    public synchronized static ArrayList<Post> assembleTimeline(String username)
    {
        ArrayList<Post> timelineData = new ArrayList<>( getPostData(username) );
        User user = getFollowData(username);

        for (String followingUsername : user.getFollowing() ) {
            ArrayList<Post> followingPosts = getPostData(followingUsername);
            timelineData.addAll(followingPosts);
        }

        timelineData.sort(Collections.reverseOrder());
        return timelineData;
    }

    public synchronized static User assembleUser(String username) {
        User user = new User();
        user.setUsername       ( username );
        user.setPosts          ( getPostData(username) );
        user.setBioText        ( getBioData(username).getBioText() );
        user.setFollowers      ( getFollowData(username).getFollowers() );
        user.setFollowing      ( getFollowData(username).getFollowing() );
        user.setProfilePicture ( user.getServerFilePath() );
        return user;
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

    public synchronized static ArrayList<Post> getPostData(String username)
    {
        DBCollection collection = db.getCollection(Utils.DB_POST);
        DBObject query = new BasicDBObject("username", username);
        Cursor cursor = collection.find(query);

        ArrayList<Post> posts = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            Post post = Post.parsePost(obj);
            posts.add(0, post);
        }
        return posts;
    }

    public synchronized static void setBio(User userData)
    {
        DBCollection collection = db.getCollection(Utils.DB_BIO);

        User user = getBioData(userData.getUsername());
        user.setBioText(userData.getBioText());

        DBObject query = new BasicDBObject("username", userData.getUsername());
        collection.update(query, user.createBioDBObject());
    }

    public synchronized static void setProfilePicture(User userData)
    {
        try {
            Files.createDirectories( Paths.get(Utils.DIR_PROFILE_PICTURES) );
            File savedProfilePicture = new File( userData.getServerFilePath() );
            FileOutputStream out = new FileOutputStream(savedProfilePicture);
            out.write(userData.getProfilePicture());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

