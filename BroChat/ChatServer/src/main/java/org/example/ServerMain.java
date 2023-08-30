package org.example;

import org.example.Server.Server;
import org.example.Server.parsingPort.Parsing;
import java.io.IOException;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        Parsing com = new Parsing(args);
        String port = com.getPort();
        Scanner scanner = new Scanner(System.in);
        Server serv = new Server(port,scanner);
        serv.serverStart();
    }
}