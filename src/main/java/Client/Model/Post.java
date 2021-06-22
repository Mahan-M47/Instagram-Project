package Client.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Post
{
    private String username, ID, caption;
    private AtomicInteger likes;
    private Date date;
    private List<String> likedBy;
    private ArrayList<String> comments;

    public Post(String username , Date date , AtomicInteger likes, String ID, ArrayList<String> comments, ArrayList<String> likedBy) {
        this.ID = ID;
        this.date = date ;
        this.username = username ;
        this.likes = likes;
        this.comments = comments ;
        this.likedBy = likedBy;
    }

    public Post(String username, String caption) {
        ID = IDBuilder(username);
        date = new Date() ;
        this.username = username;
        this.caption = caption;
        likes = new AtomicInteger(0);
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

    public AtomicInteger getLikes() {
        return likes;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public void addLike(String username) {
        likes.incrementAndGet();
        likedBy.add(username);
    }

    public void removeLike(String username) {
        likes.decrementAndGet();
        likedBy.remove(username);
    }

    public void addComment(String comment) {
        this.comments.add(comment);
    }

    public String IDBuilder(String username){
        return null ;
    }
}
