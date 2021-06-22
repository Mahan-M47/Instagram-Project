package Server.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class PostImage extends Post {

    private File image ;

    public PostImage(String username, String caption) {
        super(username, caption);
    }

    public PostImage(File image , String username , Date date , AtomicInteger likes , String Id , ArrayList<String> comments, ArrayList<String> likedBy){
        super(username, date, likes, Id , comments, likedBy);
        this.image = image ;
    }

}
