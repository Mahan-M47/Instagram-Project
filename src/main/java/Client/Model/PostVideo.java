package Client.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PostVideo extends Post {

    private File video;

    public PostVideo(File video , String username , Date date , int likes , String Id , ArrayList<String> comments, ArrayList<String> likedBy){
        super(username, date, likes, Id , comments, likedBy);
        this.video = video ;
    }

    public PostVideo(File video , String username , Date date){
        super(username);
        this.video = video ;
    }

}
