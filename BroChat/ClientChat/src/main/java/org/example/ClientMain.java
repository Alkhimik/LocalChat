package org.example;

import org.example.ServerCom.ServerConnection;
import org.example.ServerCom.SendToServer;
import org.example.ServerCom.ServerMessages;
import org.example.parsingPort.Parsing;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Parsing com = new Parsing(args);
        String port = com.getPort();
        Scanner scan = new Scanner(System.in);

        if(!port.matches("^[0-9]+$")){
            port = portCheck(port, scan);
        }
        ServerConnection serverConnect = new ServerConnection(Integer.parseInt(port),scan);
        serverConnect.getConnection();

        ServerMessages serverMessages = new ServerMessages(serverConnect.getInFromServer());

        Thread serverListener = new Thread(serverMessages);
        serverListener.start();

        Thread messageToServer = new Thread(new SendToServer(serverConnect.getOutToServer(), serverConnect.getScan()));
        messageToServer.start();
    }

    public static String portCheck(@NotNull String port, Scanner scanner){
        while(true){
            try{
                if(port.matches("^[0-9]+$")){
                    break;
                } else {
                    throw new IOException("Port is incorrect. Try again:");
                }
            }catch(IOException e){
                System.out.println(e.getMessage());
                port = scanner.nextLine();
            }
        }
        return port;
    }
}