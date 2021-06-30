package Client.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

public class Message
{
    private String messageType, sender, text;
    private Date date;
    private String imageBytes;

    public Message(String sender, String text) {
        messageType = "TEXT";
        this.sender = sender;
        this.text = text;
        date = new Date();
    }

    public Message(String sender, File image) {
        messageType = "IMAGE";
        this.sender = sender;
        date = new Date();
        createImageBytes(image.getPath());
    }

    public void createImageBytes(String filePath)
    {
        try {
            File savedFile = new File(filePath);
            FileInputStream in = new FileInputStream(savedFile);
            byte[] bytes = new byte[(int) savedFile.length()];
            in.read(bytes);
            in.close();
            this.imageBytes = new String(Base64.getEncoder().encode(bytes), "UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessageType() { return messageType; }

    public String getSender() { return sender; }

    public String getText() { return text; }

    public Date getDate() { return date; }

    public byte[] getImageBytes() { return Base64.getDecoder().decode(imageBytes); }

    public void setMessageType(String messageType) { this.messageType = messageType; }

    public void setSender(String sender) { this.sender = sender; }

    public void setText(String text) { this.text = text; }

    public void setDate(Date date) { this.date = date; }

    public void setImageBytes(String imageBytes) { this.imageBytes = imageBytes; }
}
