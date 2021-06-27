package Server.Controller;

import Server.Model.*;
import Server.Utils;
import com.mongodb.*;
import java.util.ArrayList;
import java.util.Collections;

public class DatabaseManager {

    private static MongoClient mongoClient;
    private static DB db;

    public static void startDatabase(String databaseName) {
        mongoClient = new MongoClient();
        db = mongoClient.getDB(databaseName);
    }

    public synchronized static void adduser(User user) {
        DBCollection login = db.getCollection(Utils.DB_LOGIN);
        login.insert(user.createLoginDBObject());

        DBCollection follow = db.getCollection(Utils.DB_FOLLOW);
        follow.insert(user.createFollowDBObject());

        DBCollection Bio = db.getCollection(Utils.DB_BIO);
        Bio.insert(user.createBioDBObject());

    }

    public synchronized static ArrayList<String> getUsers(String collectionName) {
        DBCollection collection = db.getCollection(collectionName);
        DBCursor cursor = collection.find();
        ArrayList<String> Users = new ArrayList<>();

        for (DBObject query : cursor) {
            Users.add(User.parseLoginDBObject(query).getUsername());
        }
        return Users;
    }

    public synchronized static User getUser(String collectionName, String username) {
        DBCollection collection = db.getCollection(collectionName);
        if (checkIfUserExists(collectionName, username)) {
            BasicDBObject obj = new BasicDBObject().append("username", username);
            DBObject one = collection.findOne(obj);
            return User.parseLoginDBObject(one);
        } else {
            return null;
        }
    }

    public synchronized static boolean checkIfUserExists(String collectionName, String username) {
        DBCollection collection = db.getCollection(collectionName);
        DBObject query = new BasicDBObject("username", username);
        DBObject existingQuery = collection.findOne(query);

        if (existingQuery == null) {
            return false;
        } else {
            return true;
        }
    }

    public synchronized static boolean checkLogin(String username, String password) {
        DBCollection collection = db.getCollection(Utils.DB_LOGIN);
        User user = new User(username, password);

        DBObject query = collection.findOne(user.createLoginDBObject());
        if (query == null) {
            return false;
        } else {
            return true;
        }
    }

    public synchronized static boolean checkchatid(String chatid, String searchcollection) {
        DBCollection collection = db.getCollection(searchcollection);
        DBObject dbObject = new BasicDBObject()
                .append("ChatID", chatid);

        DBObject query = collection.findOne(dbObject);
        if (query == null) {
            return false;
        } else {
            return true;
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

    public synchronized static void follow(String followerUsername, String followingUsername) {
        DBCollection collection = db.getCollection(Utils.DB_FOLLOW);
        User followerUser = getFollowData(followerUsername);
        User followingUser = getFollowData(followingUsername);

        followerUser.addFollowing(followingUsername);
        followingUser.addFollower(followerUsername);

        DBObject followingUserQuery = new BasicDBObject("username", followingUser.getUsername());
        collection.update(followingUserQuery, followingUser.createFollowDBObject());

        DBObject followerUserQuery = new BasicDBObject("username", followerUser.getUsername());
        collection.update(followerUserQuery, followerUser.createFollowDBObject());
    }

    public synchronized static void unfollow(String followerUsername, String followingUsername) {
        DBCollection collection = db.getCollection(Utils.DB_FOLLOW);
        User followerUser = getFollowData(followerUsername);
        User followingUser = getFollowData(followingUsername);

        followerUser.removeFollowing(followingUsername);
        followingUser.removeFollowers(followerUsername);

        DBObject followingUserQuery = new BasicDBObject("username", followingUser.getUsername());
        collection.update(followingUserQuery, followingUser.createFollowDBObject());

        DBObject followerUserQuery = new BasicDBObject("username", followerUser.getUsername());
        collection.update(followerUserQuery, followerUser.createFollowDBObject());
    }

    public synchronized static void createPost(Post post) {
        DBCollection collection = db.getCollection(Utils.DB_POST);
        collection.insert(post.createPostDBObject());
    }

    public synchronized static void like(String username, String postID) {
        DBCollection collection = db.getCollection(Utils.DB_POST);
        DBObject query = new BasicDBObject("ID", postID);
        DBObject object = collection.findOne(query);

        if (object != null) {
            Post post = Post.parsePost(object);
            post.addLike(username);
            collection.update(query, post.createPostDBObject());
        }
    }

    public synchronized static void unlike(String username, String postID) {
        DBCollection collection = db.getCollection(Utils.DB_POST);
        DBObject query = new BasicDBObject("ID", postID);
        DBObject object = collection.findOne(query);

        if (object != null) {
            Post post = Post.parsePost(object);
            post.removeLike(username);
            collection.update(query, post.createPostDBObject());
        }
    }

    public synchronized static void comment(String username, String postID, String commentText) {
        DBCollection collection = db.getCollection(Utils.DB_POST);
        DBObject query = new BasicDBObject("ID", postID);
        DBObject object = collection.findOne(query);

        if (object != null) {
            Post post = Post.parsePost(object);
            String comment = username + ": " + commentText;
            post.addComment(comment);
            collection.update(query, post.createPostDBObject());
        }
    }

    public synchronized static ArrayList<Post> assembleTimeline(String username) {
        ArrayList<Post> timelineData = new ArrayList<>(getPostData(username));
        User user = getFollowData(username);

        for (String followingUsername : user.getFollowing()) {
            ArrayList<Post> followingPosts = getPostData(followingUsername);
            timelineData.addAll(followingPosts);
        }

        timelineData.sort(Collections.reverseOrder());
        return timelineData;
    }

    public synchronized static User assembleUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPosts(getPostData(username));
        user.setBioText(getBioData(username).getBioText());
        user.setFollowers(getFollowData(username).getFollowers());
        user.setFollowing(getFollowData(username).getFollowing());
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

    public synchronized static ArrayList<Post> getPostData(String username) {
        DBCollection collection = db.getCollection(Utils.DB_POST);
        DBObject query = new BasicDBObject("username", username);
        Cursor cursor = collection.find(query);

        ArrayList<Post> posts = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            posts.add(0, Post.parsePost(obj));
        }
        return posts;
    }

    public synchronized static void setBio(String username, String bioText) {
        DBCollection collection = db.getCollection(Utils.DB_BIO);
        User user = getBioData(username);
        user.setBioText(bioText);

        DBObject query = new BasicDBObject("username", username);
        collection.update(query, user.createBioDBObject());
    }

    public synchronized static void CreateChatIDs(String username , String chatid){
        DBCollection collection = db.getCollection(Utils.DB_CHATID);
        ArrayList<String> chatids = new ArrayList<>();
        chatids.add(chatid);
        DBObject dbObject = new BasicDBObject()
                .append("username",username)
                .append("ChatIDs",chatids);
        collection.insert(dbObject);
    }

    public synchronized static ArrayList<String> getChatids(String username){
        DBCollection collection = db.getCollection(Utils.DB_CHATID);
        DBObject dbObject = new BasicDBObject()
                .append("username",username);
        DBObject quary = collection.findOne(dbObject);
        ArrayList<String> chatids = (ArrayList<String>) quary.get("ChatIDs");
        return  chatids ;
    }

    public synchronized static void CreatePersonalChat(ChatPersonal chat) {
        DBCollection collection = db.getCollection(Utils.DB_PERSONALCHAT);
        collection.insert(chat.createChatPersonalDBObject());
        collection = db.getCollection(Utils.DB_CHATID);
        ArrayList<String> members = chat.getMembers();
        for(int i = 0 ; i < members.size() ; i++){
            if(!checkIfUserExists(Utils.DB_CHATID,members.get(i))){
                CreateChatIDs(members.get(i),chat.getChatID());
            }
            else{
                DBObject dbObject = new BasicDBObject()
                        .append("username",members.get(i));
                ArrayList<String> chatids = getChatids(members.get(i));
                chatids.add(chat.getChatID());
                DBObject quary = new BasicDBObject()
                        .append("usename",members.get(i))
                        .append("ChatIDs",chatids);
                collection.update(dbObject,quary);
            }
        }
    }

    public synchronized static ChatPersonal getPersonalChat(String chatID) {
        DBCollection collection = db.getCollection(Utils.DB_PERSONALCHAT);
        DBObject query = new BasicDBObject("ChatID", chatID);
        DBObject dbObject = collection.findOne(query);
        return ChatPersonal.parsePersonalChatDBObject(dbObject);
    }

    public synchronized static void CreateGroupChat(ChatGroup chat) {
        DBCollection collection = db.getCollection(Utils.DB_GROUPCHAT);
        collection.insert(chat.createChatGroupDBObject());
        collection = db.getCollection(Utils.DB_CHATID);
        ArrayList<String> members = chat.getMembers();
        for(int i = 0 ; i < members.size() ; i++){
            if(!checkIfUserExists(Utils.DB_CHATID,members.get(i))){
                CreateChatIDs(members.get(i),chat.getChatID());
            }
            else{
                DBObject dbObject = new BasicDBObject()
                        .append("username",members.get(i));
                ArrayList<String> chatids = getChatids(members.get(i));
                chatids.add(chat.getChatID());
                DBObject quary = new BasicDBObject()
                        .append("usename",members.get(i))
                        .append("ChatIDs",chatids);
                collection.update(dbObject,quary);
            }
        }
    }

    public synchronized static ChatGroup getGroupChat(String chatID) {
        DBCollection collection = db.getCollection(Utils.DB_GROUPCHAT);
        DBObject query = new BasicDBObject("ChatID", chatID);
        DBObject dbObject = collection.findOne(query);
        return ChatGroup.parseGroupChatDBObject(dbObject);
    }

    public synchronized static void addMessage(String ChatID, Message message) {
        if (checkchatid(ChatID, Utils.DB_PERSONALCHAT)) {
            DBCollection collection = db.getCollection(Utils.DB_PERSONALCHAT);
            ChatPersonal chat = getPersonalChat(ChatID);
            chat.addMessage(message);
            DBObject query = new BasicDBObject("ChatID", ChatID);
            collection.update(query, chat.createChatPersonalDBObject());
        } else {
            DBCollection collection = db.getCollection(Utils.DB_GROUPCHAT);
            ChatGroup chat = getGroupChat(ChatID);
            chat.addMessage(message);
            DBObject query = new BasicDBObject("ChatID", ChatID);
            collection.update(query, chat.createChatGroupDBObject());
        }
    }

    public synchronized static void addMember(String ChatID, String member) {
        if (checkchatid(ChatID, Utils.DB_PERSONALCHAT)) {
            DBCollection collection = db.getCollection(Utils.DB_PERSONALCHAT);
            ChatPersonal chat = getPersonalChat(ChatID);
            chat.addMember(member);
            DBObject query = new BasicDBObject("ChatID", ChatID);
            collection.update(query, chat.createChatPersonalDBObject());
        } else {
            DBCollection collection = db.getCollection(Utils.DB_GROUPCHAT);
            ChatGroup chat = getGroupChat(ChatID);
            chat.addMember(member);
            DBObject query = new BasicDBObject("ChatID", ChatID);
            collection.update(query, chat.createChatGroupDBObject());
        }
    }

    
    public synchronized static ArrayList<ChatPersonal> getALLChatPersonal(String username) {
        ArrayList<String> chatids = getChatids(username);
        ArrayList<ChatPersonal> chatpersonals = new ArrayList<>();

        DBCollection collection = db.getCollection(Utils.DB_GROUPCHAT);
        for(int i = 0 ; i < chatids.size() ; i++){
            chatpersonals.add(ChatPersonal.parsePersonalChatDBObject(collection.findOne(new BasicDBObject()
                    .append("ChatID",chatids.get(i)))))
            ;
        }
        return chatpersonals;
    }

    public synchronized static ArrayList<ChatGroup> getALLChatGroups(String username) {
        ArrayList<String> chatids = getChatids(username);
        ArrayList<ChatGroup> chatGroups = new ArrayList<>();

        DBCollection collection = db.getCollection(Utils.DB_GROUPCHAT);
        for(int i = 0 ; i < chatids.size() ; i++){
            chatGroups.add(ChatGroup.parseGroupChatDBObject(collection.findOne(new BasicDBObject()
                            .append("ChatID",chatids.get(i)))))
                    ;
        }
        return chatGroups;
    }

}

