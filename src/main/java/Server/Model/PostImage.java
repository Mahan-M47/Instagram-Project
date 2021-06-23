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

    public PostImage(){
        super();
    }


}
