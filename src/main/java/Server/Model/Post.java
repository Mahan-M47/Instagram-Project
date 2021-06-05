package Server.Model;

import java.util.ArrayList;
import java.util.Date;

public abstract class Post
{
    private String username ;
    private String ID;
    private int likes;
    private Date date ;
    private ArrayList<String> comments ;

    public Post(String username , Date date , int likes, String ID, ArrayList<String> comments) {
        this.ID = ID;
        this.date = date ;
        this.username = username ;
        this.likes = likes;
        this.comments = comments ;
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

    public String IDBuilder(String username){
        return null ;
    }
}
