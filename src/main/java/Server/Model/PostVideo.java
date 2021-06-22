package Server.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class PostVideo extends Post {

    private File video;

    public PostVideo(String username, String caption) {
        super(username, caption);
    }

    public PostVideo(File video , String username , Date date , AtomicInteger like , String Id , ArrayList<String> comments, ArrayList<String> likedBy){
        super(username, date, like, Id , comments, likedBy);
        this.video = video ;
    }
}
