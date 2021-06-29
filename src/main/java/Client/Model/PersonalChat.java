package Client.Model;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.UUID;

public class PersonalChat
{
    private String chatID;
    private ArrayList<String> members;
    private ArrayList<Message> messageList;

    public PersonalChat(String user1, String user2) {
        messageList = new ArrayList<>();
        members = new ArrayList<>(2);
        members.add(user1);
        members.add(user2);
        chatID = IDBuilder();
    }

    public PersonalChat() {}

    public void addMessage(Message message) { messageList.add(message); }

    public ArrayList<String> getMembers() { return members; }

    public ArrayList<Message> getMessageList() { return messageList; }

    public String getChatID() { return chatID; }

    public void setChatID(String chatID) { this.chatID = chatID; }

    public void setMembers(ArrayList<String> members) { this.members = members; }

    public void setMessageList(ArrayList<Message> messageList) { this.messageList = messageList; }

    public String IDBuilder() { return members.get(0) + "-" + members.get(1) + UUID.randomUUID().toString(); }

    public DBObject createChatPersonalDBObject()
    {
        Gson gson = new Gson();
        ArrayList<String> jsonMessageList = new ArrayList<>();

        for (Message message : messageList) {
            jsonMessageList.add( gson.toJson(message) );
        }

        return new BasicDBObject()
                .append("ChatID", chatID)
                .append("Members", members)
                .append("MessageList", jsonMessageList);
    }

    public static PersonalChat parsePersonalChatDBObject(DBObject object)
    {
        PersonalChat chat = new PersonalChat();
        chat.setChatID((String) object.get("ChatID"));
        chat.setMembers((ArrayList<String>) object.get("Members"));

        Gson gson = new Gson();
        chat.setMessageList(new ArrayList<>());
        ArrayList<String> jsonMessageList = (ArrayList<String>) object.get("MessageList");

        for (String jsonMessage : jsonMessageList) {
            chat.addMessage( gson.fromJson(jsonMessage, Message.class) );
        }

        return chat;
    }

}
