package org.example.Server;

import org.example.Authorization.User;
import org.example.Storage.Message;
import org.example.Storage.MessageStorage;

import java.io.PrintWriter;


public class SendMessagesToClients implements Runnable{
    private PrintWriter outToClient;
    private User client;
    private UserExistence userInChat;
    public SendMessagesToClients(PrintWriter outToClient, User client, UserExistence userInChat) {
        this.outToClient = outToClient;
        this.client = client;
        this.userInChat = userInChat;
    }
    @Override
    public void run() {
        int index = 0;
        Message income;
        MessageStorage storage = MessageStorage.getInstance();

        while (true){
            income = storage.getNewMessage(index);
            while(income!=null){
                if(income.getClientId() != client.getId()){
                    //server commands
                    if(income.getClientId() == -1){
                        outToClient.println("Server is down");
                    }
                    //messages
                    outToClient.println(income.getSenderName() + ": " + income.getMessage());
                }
                index++;
                income = storage.getNewMessage(index);
            }
            if(userInChat.getUserInChat() == false){
                break;
            }
        }
    }
}