package Server.Model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.io.File;
import java.util.ArrayList;

public class User
{
    private String username, password, bioText;
    private ArrayList<String> followers, following;
    private ArrayList<Post> posts;
    private ArrayList<Chat> chats;
    private File profilePicture;

    public User() {
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        followers = new ArrayList<>();
        following = new ArrayList<>();
        posts = new ArrayList<>();
        chats = new ArrayList<>();
        bioText = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }

    public boolean checkexistinfollowing(String username){
        return following.contains(username);
    }

    public boolean checkexistinfollowers(String username){
        return followers.contains(username);
    }

    public void addFollowing(String username) {
        following.add(username);
    }

    public void addFollowers(String username) {
        followers.add(username);
    }

    public void removeFollowing(String username) {
        following.remove(username);
    }

    public void removeFollowers(String username) {
        followers.remove(username);
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

    public String getBioText() {
        return bioText;
    }

    public void setBioText(String bioText) {
        this.bioText = bioText;
    }

    public DBObject getLoginDBObject() {
        return new BasicDBObject()
                .append("username", getUsername())
                .append("password", getPassword());
    }

    public DBObject getFollowDBObject() {
        return new BasicDBObject()
                .append("username", getUsername())
                .append("Following", following)
                .append("Followers", followers);
    }

    public DBObject getBioDBObject() {
        return new BasicDBObject()
                .append("username", getUsername())
                .append("Bio", bioText);
    }

    public static User parseUser(DBObject object) {
        User user = new User();
        user.setUsername((String) object.get("username"));
        user.setPassword((String) object.get("password"));
        return user;
    }

    public static User parseFollow(DBObject object) {
        User user = new User();
        user.setUsername((String) object.get("username"));
        user.setFollowing((ArrayList<String>)object.get("Following"));
        user.setFollowers((ArrayList<String>)object.get("Followers"));
        return user;
    }

    public static User parseBio(DBObject object) {
        User user = new User();
        user.setUsername((String) object.get("username"));
        user.setBioText((String) object.get("Bio"));
        return user;
    }

}
