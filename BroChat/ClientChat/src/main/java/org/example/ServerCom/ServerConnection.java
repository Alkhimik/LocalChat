package org.example.ServerCom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnection {
    private int serverPort;
    private PrintWriter outToServer;
    private BufferedReader inFromServer;
    private Boolean connectedToServer = false;
    private final Scanner scan;
    public ServerConnection(int serverPort, Scanner scan){
        this.serverPort = serverPort;
        this.scan = scan;
    }

    public Scanner getScan() {
        return scan;
    }

    public PrintWriter getOutToServer() {
        return outToServer;
    }
    public BufferedReader getInFromServer() {
        return inFromServer;
    }

    public void getConnection() {
        while(true) {
            try{
                Socket socket = new Socket("localhost", serverPort);
                inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String connectToOurServer = inFromServer.readLine();
                if (connectToOurServer == null || !connectToOurServer.contains("Hello from Server!")) {
                    throw new IOException("Wrong server. Try another port: ");
                }
                outToServer = new PrintWriter(socket.getOutputStream(), true);
                connectedToServer = true;
                System.out.println("Successfully connected to the server!");
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                String str;
                while (true) {
                    if ((str = scan.nextLine()).matches("^[0-9]+$")) {
                        serverPort = Integer.parseInt(str);
                        break;
                    } else {
                        System.out.println("Wrong port value. Try again: ");
                    }
                }
            }
        }
    }

    public void sendMessagesToServer(String str){
        if(!connectedToServer){
        System.out.println("Not connected to the server. Connect first.");
            return;
        }
        if(str == null){return;}
        outToServer.println(str);
    }

}
