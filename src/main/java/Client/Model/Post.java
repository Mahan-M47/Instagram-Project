package Client.Model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Post
{
    private String username, ID, caption;
    private Date date;
    private List<String> likedBy;
    private ArrayList<String> comments;
    private File file;

    public Post() {
    }

    public Post(String username, String caption) {
        ID = IDBuilder(username);
        date = new Date() ;
        this.username = username;
        this.caption = caption;
        comments = new ArrayList<>() ;
        likedBy = new ArrayList<>();
    }

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

    public File getFile() {
        return file;
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

    public void setFile(File file) {
        this.file = file;
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

    public DBObject createPostDBObject(){
        return new BasicDBObject()
                .append("createPost",file)
                .append("Caption",caption)
                .append("ID",ID)
                .append("username",username)
                .append("Comments",comments)
                .append("Date",date)
                .append("LikedBy",likedBy);
    }

    public static PostImage parsePost(DBObject object)
    {
        PostImage post = new PostImage();
        post.setFile((File) object.get("createPost"));
        post.setCaption((String) object.get("Caption"));
        post.setUsername((String) object.get("username"));
        post.setLikedBy((ArrayList<String>) object.get("LikedBy"));
        post.setComments((ArrayList<String>) object.get("Comments"));
        post.setDate((Date) object.get("Date"));
        post.setID((String) object.get("ID"));
        return post;
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public String IDBuilder(String username) { return username + System.currentTimeMillis(); }
}
