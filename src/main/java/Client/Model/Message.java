package Client.Model;

import java.io.File;
import java.util.Date;

public class Message
{
    private String messageType, sender, text;
    private Date date;
    private File file = null;

    public Message(String sender, String text) {
        messageType = "TEXT";
        this.sender = sender;
        this.text = text;
        date = new Date();
    }

    public Message(String sender, File file) {
        messageType = "FILE";
        this.sender = sender;
        this.file = file;
        date = new Date();
    }

    public String getMessageType() { return messageType; }

    public String getSender() { return sender; }

    public String getText() { return text; }

    public Date getDate() { return date; }

    public File getFile() { return file; }

    public void setMessageType(String messageType) { this.messageType = messageType; }

    public void setSender(String sender) { this.sender = sender; }

    public void setText(String text) { this.text = text; }

    public void setDate(Date date) { this.date = date; }

    public void setFile(File file) { this.file = file; }
}
