package org.example.ServerCom;

import java.io.BufferedReader;
import java.io.IOException;

public class ServerMessages implements Runnable{
    private BufferedReader fromServer;
    public ServerMessages(BufferedReader fromServer) {
        this.fromServer = fromServer;
    }

    @Override
    public void run() {
        System.out.println("Listening for new messages...");
        String serverResponse;
        while(true){
            try{
            if(!((serverResponse = fromServer.readLine()) != null)) break;
            }catch(IOException e){throw new RuntimeException(e);}
            if(serverResponse.matches("Server is down")){
                System.out.println("Server closed. Chat ended.");
                System.exit(0);
            }
            System.out.println(serverResponse);
        }
    }
}
