package Client.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PostImage extends Post {

    private File image ;

    public PostImage(File image , String username , Date date , int likes , String Id , ArrayList<String> comments, ArrayList<String> likedBy){
        super(username, date, likes, Id , comments, likedBy);
        this.image = image ;
    }

    public PostImage(File image , String username , Date date){
        super(username);
        this.image = image ;
    }



}
