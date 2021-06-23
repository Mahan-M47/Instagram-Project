package Client.Model;

import java.io.File;

public class PostImage extends Post {

    private File image ;

    public PostImage(String username, String caption) {
        super(username, caption);
    }

    public PostImage(){
        super();
    }


}
