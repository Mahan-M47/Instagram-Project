package Server.Controller;

import Server.Model.*;
import Server.Utils;
import com.mongodb.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class DatabaseManager
{
    private static MongoClient mongoClient;
    private static DB db;

    public static void startDatabase(String databaseName) {
        mongoClient = new MongoClient();
        db = mongoClient.getDB(databaseName);
    }

    //--------------------------------------------Authentication & Search-----------------------------------------------

    public synchronized static void adduser(User user)
    {
        DBCollection login = db.getCollection(Utils.DB.LOGIN);
        login.insert(user.createLoginDBObject());

        DBCollection follow = db.getCollection(Utils.DB.FOLLOW);
        follow.insert(user.createFollowDBObject());

        DBCollection Bio = db.getCollection(Utils.DB.BIO);
        Bio.insert(user.createBioDBObject());

        DBCollection chatID = db.getCollection(Utils.DB.CHAT_ID);
        DBObject obj = new BasicDBObject()
                .append("Username", user.getUsername())
                .append("ChatIDs", new ArrayList<String>());
        chatID.insert(obj);
    }

    public synchronized static boolean checkIfUserExists(String username)
    {
        DBCollection collection = db.getCollection(Utils.DB.LOGIN);
        DBObject query = new BasicDBObject("Username", username);
        DBObject existingQuery = collection.findOne(query);

        if (existingQuery == null) {
            return false;
        } else {
            return true;
        }
    }

    public synchronized static boolean checkLogin(String username, String password)
    {
        DBCollection collection = db.getCollection(Utils.DB.LOGIN);
        User user = new User(username, password);

        DBObject query = collection.findOne(user.createLoginDBObject());
        if (query == null) {
            return false;
        } else {
            return true;
        }
    }

    public synchronized static ArrayList<String> searchUser(String searchText)
    {
        DBCollection collection = db.getCollection(Utils.DB.LOGIN);
        ArrayList<String> results = new ArrayList<>();
        DBCursor allUsers = collection.find();

        for (DBObject query : allUsers)
        {
            String username = User.parseLoginDBObject(query).getUsername();

            if (username.contains(searchText)) {
                results.add(username);
            }
        }

        return results;
    }

    //-------------------------------------------------Follow and Bio---------------------------------------------------

    public synchronized static void follow(String followerUsername, String followingUsername)
    {
        DBCollection collection = db.getCollection(Utils.DB.FOLLOW);
        User followerUser = getFollowData(followerUsername);
        User followingUser = getFollowData(followingUsername);

        if (! followerUser.getFollowing().contains(followingUsername) ) {
            followerUser.addFollowing(followingUsername);
        }
        if (! followingUser.getFollowers().contains(followerUsername) ) {
            followingUser.addFollower(followerUsername);
        }

        DBObject followingUserQuery = new BasicDBObject("Username", followingUser.getUsername());
        collection.update(followingUserQuery, followingUser.createFollowDBObject());

        DBObject followerUserQuery = new BasicDBObject("Username", followerUser.getUsername());
        collection.update(followerUserQuery, followerUser.createFollowDBObject());
    }

    public synchronized static void unfollow(String followerUsername, String followingUsername)
    {
        DBCollection collection = db.getCollection(Utils.DB.FOLLOW);
        User followerUser = getFollowData(followerUsername);
        User followingUser = getFollowData(followingUsername);

        followerUser.removeFollowing(followingUsername);
        followingUser.removeFollowers(followerUsername);

        DBObject followingUserQuery = new BasicDBObject("Username", followingUser.getUsername());
        collection.update(followingUserQuery, followingUser.createFollowDBObject());

        DBObject followerUserQuery = new BasicDBObject("Username", followerUser.getUsername());
        collection.update(followerUserQuery, followerUser.createFollowDBObject());
    }

    public synchronized static void setBio(User userData)
    {
        DBCollection collection = db.getCollection(Utils.DB.BIO);

        User user = getBioData(userData.getUsername());
        user.setBioText(userData.getBioText());

        DBObject query = new BasicDBObject("Username", userData.getUsername());
        collection.update(query, user.createBioDBObject());
    }

    public synchronized static void setProfilePicture(User userData)
    {
        try {
            Files.createDirectories( Paths.get(Utils.DIR_SERVER_PROFILE_PICTURES) );
            File savedProfilePicture = new File( userData.getServerFilePath() );
            FileOutputStream out = new FileOutputStream(savedProfilePicture);
            out.write(userData.getProfilePicture());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------Posts----------------------------------------------------------

    public synchronized static void createPost(Post post)
    {
        DBCollection collection = db.getCollection(Utils.DB.POST);

        try {
            Files.createDirectories( Paths.get(Utils.DIR_SERVER_POSTS) );
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
        DBCollection collection = db.getCollection(Utils.DB.POST);
        DBObject query = new BasicDBObject("ID", postID);
        DBObject object = collection.findOne(query);

        Post post = Post.parsePost(object);
        post.addLike(username);
        collection.update(query, post.createPostDBObject());

        return post;
    }

    public synchronized static void unlike(String username, String postID)
    {
        DBCollection collection = db.getCollection(Utils.DB.POST);
        DBObject query = new BasicDBObject("ID", postID);
        DBObject object = collection.findOne(query);

        Post post = Post.parsePost(object);
        post.removeLike(username);
        collection.update(query, post.createPostDBObject());
    }

    public synchronized static Post comment(String username, String postID, String commentText)
    {
        DBCollection collection = db.getCollection(Utils.DB.POST);
        DBObject query = new BasicDBObject("ID", postID);
        DBObject object = collection.findOne(query);

        Post post = Post.parsePost(object);
        String comment = username + ": " + commentText;
        post.addComment(comment);
        collection.update(query, post.createPostDBObject());

        return post;
    }

    //------------------------------------------------Database Access---------------------------------------------------

    public synchronized static User assembleUser(String username)
    {
        User user = new User();
        user.setUsername       ( username );
        user.setPosts          ( getPostData(username) );
        user.setBioText        ( getBioData(username).getBioText() );
        user.setFollowers      ( getFollowData(username).getFollowers() );
        user.setFollowing      ( getFollowData(username).getFollowing() );
        user.setProfilePicture ( user.getServerFilePath() );
        return user;
    }

    public synchronized static ArrayList<Post> assembleTimeline(String username)
    {
        ArrayList<Post> timelineData = new ArrayList<>(getPostData(username));
        User user = getFollowData(username);

        for (String followingUsername : user.getFollowing()) {
            ArrayList<Post> followingPosts = getPostData(followingUsername);
            timelineData.addAll(followingPosts);
        }

        timelineData.sort(Collections.reverseOrder());
        return timelineData;
    }

    public synchronized static User getFollowData(String username)
    {
        DBCollection collection = db.getCollection(Utils.DB.FOLLOW);
        DBObject query = new BasicDBObject("Username", username);
        query = collection.findOne(query);
        return User.parseFollowDBObject(query);
    }

    public synchronized static User getBioData(String username)
    {
        DBCollection collection = db.getCollection(Utils.DB.BIO);
        DBObject query = new BasicDBObject("Username", username);
        query = collection.findOne(query);
        return User.parseBioDBObject(query);
    }

    public synchronized static ArrayList<Post> getPostData(String username)
    {
        DBCollection collection = db.getCollection(Utils.DB.POST);
        DBObject query = new BasicDBObject("Username", username);
        Cursor cursor = collection.find(query);

        ArrayList<Post> posts = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            Post post = Post.parsePost(obj);
            posts.add(0, post);
        }

        return posts;
    }

    //-------------------------------------------Personal and Group Chats-----------------------------------------------

    public synchronized static String checkIfPersonalChatExists(String user1, String user2)
    {
        String chatID = null;
        ArrayList<PersonalChat> chats = getAllPersonalChats(user1);

        for (PersonalChat chat : chats) {
            if ( chat.getMembers().contains(user2) ) {
                chatID = chat.getChatID();
                break;
            }
        }
        return chatID;
    }

    public synchronized static void updateChatIDCollection(String member, String chatID)
    {
        DBCollection collection = db.getCollection(Utils.DB.CHAT_ID);

        ArrayList<String> chatIDs = getChatIDs(member);
        chatIDs.add(chatID);

        DBObject query = new BasicDBObject("Username", member);
        DBObject obj = new BasicDBObject("Username", member)
                .append("ChatIDs", chatIDs);

        collection.update(query, obj);
    }

    public synchronized static ArrayList<String> getChatIDs(String username)
    {
        DBCollection collection = db.getCollection(Utils.DB.CHAT_ID);
        DBObject query = new BasicDBObject("Username",username);
        DBObject obj = collection.findOne(query);
        return (ArrayList<String>) obj.get("ChatIDs");
    }

    public synchronized static PersonalChat createPersonalChat(String member1, String member2)
    {
        PersonalChat chat = new PersonalChat(member1, member2);
        updateChatIDCollection(member1, chat.getChatID());
        updateChatIDCollection(member2, chat.getChatID());

        DBCollection collection = db.getCollection(Utils.DB.PERSONAL_CHAT);
        collection.insert( chat.createChatPersonalDBObject() );

        return chat;
    }

    public synchronized static PersonalChat getPersonalChat(String chatID)
    {
        DBCollection collection = db.getCollection(Utils.DB.PERSONAL_CHAT);
        DBObject query = new BasicDBObject("ChatID", chatID);
        DBObject dbObject = collection.findOne(query);
        return PersonalChat.parsePersonalChatDBObject(dbObject);
    }

    public synchronized static ArrayList<PersonalChat> getAllPersonalChats(String username)
    {
        DBCollection collection = db.getCollection(Utils.DB.PERSONAL_CHAT);
        ArrayList<String> chatIDs = getChatIDs(username);
        ArrayList<PersonalChat> personalChats = new ArrayList<>();

        for (String chatID : chatIDs)
        {
            DBObject query = new BasicDBObject("ChatID", chatID);
            DBObject obj = collection.findOne(query);

            if (obj != null) {
                personalChats.add( PersonalChat.parsePersonalChatDBObject(obj) );
            }
        }

        return personalChats;
    }

    public synchronized static GroupChat createGroupChat(String firstMember)
    {
        GroupChat chat = new GroupChat(firstMember);
        updateChatIDCollection(firstMember, chat.getChatID());

        DBCollection collection = db.getCollection(Utils.DB.GROUP_CHAT);
        collection.insert( chat.createChatGroupDBObject() );

        return chat;
    }

    public synchronized static GroupChat getGroupChat(String chatID)
    {
        DBCollection collection = db.getCollection(Utils.DB.GROUP_CHAT);
        DBObject query = new BasicDBObject("ChatID", chatID);
        DBObject dbObject = collection.findOne(query);
        return GroupChat.parseGroupChatDBObject(dbObject);
    }

    public synchronized static ArrayList<GroupChat> getAllGroupChats(String username)
    {
        DBCollection collection = db.getCollection(Utils.DB.GROUP_CHAT);
        ArrayList<String> chatIDs = getChatIDs(username);
        ArrayList<GroupChat> groupChats = new ArrayList<>();

        for (String chatID : chatIDs)
        {
            DBObject query = new BasicDBObject("ChatID", chatID);
            DBObject obj = collection.findOne(query);

            if (obj != null) {
                groupChats.add( GroupChat.parseGroupChatDBObject(obj) );
            }
        }

        return groupChats;
    }

    public synchronized static ArrayList<String> addMessage(String chatID, Message message)
    {
        DBCollection collection = db.getCollection(Utils.DB.PERSONAL_CHAT);
        DBObject dbObject = new BasicDBObject("ChatID", chatID);
        DBObject query = collection.findOne(dbObject);

        if ( query != null )
        {
            collection = db.getCollection(Utils.DB.PERSONAL_CHAT);
            PersonalChat chat = getPersonalChat(chatID);
            chat.addMessage(message);
            query = new BasicDBObject("ChatID", chatID);
            collection.update( query, chat.createChatPersonalDBObject() );

            return chat.getMembers();
        }
        else
        {
            collection = db.getCollection(Utils.DB.GROUP_CHAT);
            GroupChat chat = getGroupChat(chatID);
            chat.addMessage(message);
            query = new BasicDBObject("ChatID", chatID);
            collection.update( query, chat.createChatGroupDBObject() );

            return chat.getMembers();
        }
    }

    public synchronized static GroupChat addMember(String member, String chatID)
    {
        GroupChat chat = getGroupChat(chatID);
        chat.addMember(member);
        updateChatIDCollection(member, chatID);

        DBCollection collection = db.getCollection(Utils.DB.GROUP_CHAT);
        DBObject query = new BasicDBObject("ChatID", chatID);
        collection.update( query, chat.createChatGroupDBObject() );

        return chat;
    }

}

