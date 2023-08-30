package org.example.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Message {
    private String senderName;
    private String message;
    private int clientId;

    public void setSenderId(String senderName) {
        this.senderName = senderName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getClientId() {
        return clientId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }


    public Message(int clientId,String senderName, String message) {
        this.senderName = senderName;
        this.message = message;
        this.clientId = clientId;
    }
}
