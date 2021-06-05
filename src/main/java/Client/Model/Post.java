package Client.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Post
{
    private String username ;
    private String ID;
    private int likes;
    private List<String> likedBy;
    private Date date;
    private ArrayList<String> comments ;

    public Post(String username , Date date , int likes, String ID, ArrayList<String> comments, ArrayList<String> likedBy) {
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
        likes = 0 ;
        comments = new ArrayList<>() ;
        likedBy = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public String getID() {
        return ID;
    }

    public int getLikes() {
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
