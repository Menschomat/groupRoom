package model;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {
    private String sender = "unknown";
    private String target = "all";
    private String timestamp = String.valueOf(new Date().getTime());
    private String messageBody = "";

    public ChatMessage(String sender, String target, String timestamp, String messageBody) {
        this.sender = sender;
        this.target = target;
        this.timestamp = timestamp;
        this.messageBody = messageBody;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public String getTarget() {
        return target;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessageBody() {
        return messageBody;
    }
}
