package Server.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public abstract class Post {

    private String username ;
    private String Id ;
    private int like ;
    private Date date ;
    private ArrayList<String> comments ;

    public Post(String username , Date date , int like , String Id , ArrayList<String> comments){
        this.Id = Id ;
        this.date = date ;
        this.username = username ;
        this.like = like ;
        this.comments = comments ;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return Id;
    }

    public int getLike() {
        return like;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public String Idbuilder(String username){
        return null ;
    }
}
