package org.example.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private final ServerSocket server;
    private int clientId;

    public Server(String port, Scanner scanner) {
        this.server = portCheck(port,scanner);
        new Thread(new ServerCommands(scanner)).start();
    }

    public void serverStart() {
        while(true){
            try{
                Socket client = server.accept();
                clientId++;
                System.out.println("Server got a new guest!");

               new Thread(new ClientHandler(new PrintWriter(client.getOutputStream(),true),
                       new BufferedReader(new InputStreamReader(client.getInputStream())),
                       clientId)).start();

            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public ServerSocket portCheck(String port, Scanner scanner){
        ServerSocket server;
        while(true){
            try{
                if(port.matches("^[0-9]+$")){
                    server = new ServerSocket(Integer.parseInt(port));
                    System.out.println("Server is deployed under port: " + port + "\n Success!");
                    break;
                } else {
                    throw new IOException("Port is incorrect. Try again:");
                }
            }catch(IOException e){
                System.out.println(e.getMessage());
                port = scanner.nextLine();
            }
        }
        return server;
    }
}
