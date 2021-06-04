package Server.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PostVideo extends Post {
    private File video;
    private String Id ;
    private Date date ;
    private int like ;
    private String username ;

    public PostVideo(File video , String username , Date date , int like , String Id , ArrayList<String> comments){
        super(username, date, like, Id , comments);
        this.video = video ;
    }

    public PostVideo(File video , String username , Date date){
        super(username, date);
        this.video = video ;
    }

}
