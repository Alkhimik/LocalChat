package org.example.ServerCom;

import java.io.PrintWriter;
import java.util.Scanner;

public class SendToServer implements Runnable{
    private final PrintWriter outToServer;
    private final Scanner scan;
    public SendToServer(PrintWriter outToServer, Scanner scan){
      this.outToServer = outToServer;
      this.scan = scan;
    }

    @Override
    public void run() {
        String toServer;
        while(true){
            toServer = scan.nextLine();
            outToServer.println(toServer);
            if(toServer.matches("Exit")){
                System.out.println("                         You left BroChat\n----------------------------------------------------------------------");
                System.exit(0);
            }
        }
    }
}
