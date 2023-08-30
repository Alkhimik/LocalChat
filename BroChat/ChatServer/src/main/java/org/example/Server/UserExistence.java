package org.example.Server;

public class UserExistence {
    private volatile Boolean userInChat = true;
    public Boolean getUserInChat() {
        return userInChat;
    }
    public synchronized void setUserInChat(Boolean userInChat) {
        this.userInChat = userInChat;
    }
}
