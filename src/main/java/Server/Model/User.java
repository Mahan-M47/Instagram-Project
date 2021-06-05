package Server.Model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class User {
        private String username;
        private String password;

        public User() {
        }

        public User(String username, String password) {
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

        public static User parsePlayer(DBObject object) {
            User user = new User();
            user.setUsername((String) object.get("username"));
            user.setPassword((String) object.get("password"));
            return user;
        }


    }
