package Server.Model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.UUID;

public class GroupChat
{
    private String chatID;
    private ArrayList<String> members;
    private ArrayList<Message> messageList;

    public GroupChat(String firstMember) {
        messageList = new ArrayList<>();
        members = new ArrayList<>();
        members.add(firstMember);
        chatID = IDBuilder();
    }

    public GroupChat() {
    }

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public void addMember(String user) { members.add(user); }

    public ArrayList<String> getMembers() {
        return members;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public String getChatID() { return chatID; }

    public void setChatID(String chatID) { this.chatID = chatID; }

    public void setMembers(ArrayList<String> members) { this.members = members; }

    public void setMessageList(ArrayList<Message> messageList) { this.messageList = messageList; }

    public String IDBuilder() {
        return UUID.randomUUID().toString();
    }

    public DBObject createChatGroupDBObject() {
        return new BasicDBObject()
                .append("ChatID",getChatID())
                .append("Members",getMembers())
                .append("MessageList",getMessageList());
    }

    public static GroupChat parseGroupChatDBObject(DBObject object) {
        GroupChat chat = new GroupChat();
        chat.setChatID((String) object.get("ChatID"));
        chat.setMembers((ArrayList<String>) object.get("Members"));
        chat.setMessageList((ArrayList<Message>) object.get(("MessageList")));
        return chat;
    }

}
