package Server.Model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatGroup
{
    String chatID;
    ArrayList<String> members;
    ArrayList<Message> messageList;

    public ChatGroup(String firstMember) {
        members = new ArrayList<>();
        members.add(firstMember);
        chatID = IDBuilder();
    }

    public ChatGroup() {
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
                .append("MessageList",getMessageList())
                ;
    }

    public static ChatGroup parseGroupChatDBObject(DBObject object) {
        ChatGroup chat = new ChatGroup();
        chat.setChatID((String) object.get("ChatID"));
        chat.setMembers((ArrayList<String>) object.get("Members"));
        chat.setMessageList((ArrayList<Message>) object.get(("MessageList")));
        return chat;
    }

}
