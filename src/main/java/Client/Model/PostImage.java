package Client.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class PostImage extends Post {

    private File image ;

    public PostImage(File image , String username , Date date , int like , String Id , ArrayList<String> comments){
        super(username, date, like, Id , comments);
        this.image = image ;
    }

    public PostImage(File image , String username , Date date){
        super(username);
        this.image = image ;
    }



}
