package Client.Model;

import Client.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Post implements Comparable<Post>
{
    private String postType, username, ID, caption;
    private Date date;
    private List<String> likedBy;
    private ArrayList<String> comments;
    private String fileBytes;

    public Post() {
    }

    public Post(String username, String caption, String clientFilePath) {
        ID = IDBuilder(username);
        date = new Date() ;
        this.username = username;
        this.caption = caption;
        comments = new ArrayList<>() ;
        likedBy = new ArrayList<>();
        setFileBytes(clientFilePath);
    }

    public void setPostType(String postType) { this.postType = postType; }

    public String getPostType() { return postType; }

    public Date getDate() {
        return date;
    }

    public String getID() {
        return ID;
    }

    public String getCaption() { return caption; }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public byte[] getFileBytes() {
        return Base64.getDecoder().decode(fileBytes);
    }

    public void setFileBytes(String filePath) {
        try {
            File savedFile = new File(filePath);
            FileInputStream in = new FileInputStream(savedFile);
            byte[] bytes = new byte[(int) savedFile.length()];
            in.read(bytes);
            this.fileBytes = new String(Base64.getEncoder().encode(bytes), "UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void emptyFileBytes() {
        this.fileBytes = "";
    }

    public String getServerFilePath() {
        return Utils.DIR_SERVER_POSTS + ID + postType;
    }

    public void addLike(String username) {
        likedBy.add(username);
    }

    public void removeLike(String username) {
        likedBy.remove(username);
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DBObject createPostDBObject() {
        return new BasicDBObject()
                .append("PostType", postType)
                .append("Caption", caption)
                .append("ID",ID)
                .append("Username", username)
                .append("Comments", comments)
                .append("Date", date)
                .append("LikedBy", likedBy);
    }

    public static Post parsePost(DBObject object)
    {
        Post post = new Post();
        post.setPostType((String) object.get("PostType"));
        post.setCaption((String) object.get("Caption"));
        post.setUsername((String) object.get("Username"));
        post.setLikedBy((ArrayList<String>) object.get("LikedBy"));
        post.setComments((ArrayList<String>) object.get("Comments"));
        post.setDate((Date) object.get("Date"));
        post.setID((String) object.get("ID"));
        return post;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public String IDBuilder(String username) { return username + UUID.randomUUID().toString(); }

    @Override
    public int compareTo(Post post) {
        return getDate().compareTo(post.getDate());
    }

}
