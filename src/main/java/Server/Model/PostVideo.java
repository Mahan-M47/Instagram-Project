package Server.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PostVideo extends Post {

    private File video;

    public PostVideo(File video , String username , Date date , int like , String Id , ArrayList<String> comments){
        super(username, date, like, Id , comments);
        this.video = video ;
    }
}
