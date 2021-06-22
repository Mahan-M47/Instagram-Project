package Server.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Post
{
    private String username, ID;
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

    public Post(String username){
        ID = IDBuilder(username);
        date = new Date() ;
        this.username = username ;
        likes.set(0);
        comments = new ArrayList<>() ;
        likedBy = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public String getID() {
        return ID;
    }

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

    public String IDBuilder(String username){
        return null ;
    }
}
