package Server.Controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class user {
    private String username;
    private String password;

    public user() {
    }

    public user(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DBObject getDBObject() {
        return new BasicDBObject()
                .append("username", getUsername())
                .append("password", getPassword());
    }

    public static user parsePlayer(DBObject object) {
        user user = new user();
        user.setUsername((String) object.get("username"));
        user.setPassword((String) object.get("password"));
        return user;
    }



}
