package Client.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatPersonal
{
    String chatID;
    List<String> members;
    List<Message> messageList;

    public ChatPersonal(String user1, String user2) {
        members = new ArrayList<>(2);
        members.add(user1);
        members.add(user2);
        chatID = IDBuilder();
    }

    public void addMessage(Message message) { messageList.add(message); }

    public List<String> getMembers() { return members; }

    public List<Message> getMessageList() { return messageList; }

    public String getChatID() { return chatID; }

    public void setChatID(String chatID) { this.chatID = chatID; }

    public void setMembers(List<String> members) { this.members = members; }

    public void setMessageList(List<Message> messageList) { this.messageList = messageList; }

    public String IDBuilder() { return members.get(0) + " " + members.get(1) + UUID.randomUUID().toString(); }
}
