package org.example.Authorization;

import org.example.Data.Repository;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
public class SignIn implements Callable {
    private PrintWriter outToClient;
    private BufferedReader inFromClient;
    private Repository repository;
    public SignIn(Repository repository, PrintWriter outToClient, BufferedReader inFromClient){
        this.repository = repository;
        this.outToClient = outToClient;
        this.inFromClient = inFromClient;
    }

    @Override
    public User call() throws Exception {
        User user;
        while(true){
                outToClient.println("Please write down your login");
                String login = inFromClient.readLine();
                outToClient.println("Please write down your password");
                String password = inFromClient.readLine();
                user = repository.read(login,password);
                if(user != null){break;}
                outToClient.println("Client with such login/password doesn't exists");
        }
        return user;
    }
}
