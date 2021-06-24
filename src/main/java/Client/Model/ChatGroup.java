package Client.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatGroup
{
    String chatID;
    List<String> members;
    List<Message> messageList;

    public ChatGroup(String firstMember) {
        members = new ArrayList<>();
        members.add(firstMember);
        chatID = IDBuilder();
    }

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public void addMember(String user) { members.add(user); }

    public List<String> getMembers() {
        return members;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public String getChatID() { return chatID; }

    public void setChatID(String chatID) { this.chatID = chatID; }

    public void setMembers(List<String> members) { this.members = members; }

    public void setMessageList(List<Message> messageList) { this.messageList = messageList; }

    public String IDBuilder() {
        return UUID.randomUUID().toString();
    }

}
