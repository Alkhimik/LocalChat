package org.example.Server;

import org.example.Storage.Message;
import org.example.Storage.MessageStorage;

import java.util.Scanner;

public class ServerCommands implements Runnable{
    Scanner scanner;

    public ServerCommands(Scanner scanner){
        this.scanner = scanner;
    }
    @Override
    public void run() {
        System.out.println("To close Server type 'Exit'");
        String command = scanner.nextLine();
        MessageStorage mes = MessageStorage.getInstance();
        while(true){
            if(command.matches("Exit")){
                mes.addNewMessage(new Message(-1,"server","Server is down"));
                System.out.println("server closed.");
                System.exit(0);
            }

            command = scanner.nextLine();
        }

    }
}
