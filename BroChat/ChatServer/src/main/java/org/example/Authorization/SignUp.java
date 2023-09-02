package org.example.Authorization;

import org.example.Data.Repository;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.Callable;

public class SignUp implements Callable {
    private final PrintWriter outToClient;
    private final BufferedReader inFromClient;
    private final Repository repository;

    public SignUp(Repository repository, PrintWriter outToClient, BufferedReader inFromClient){
        this.repository = repository;
        this.outToClient = outToClient;
        this.inFromClient = inFromClient;
    }

    @Override
    public User call() throws Exception {
        outToClient.println("Signing up then!");
        String login;
        String password;
        while (true) {
            outToClient.println("Please create your login(just type something)");
            login = inFromClient.readLine();
            outToClient.println("Please create your password(don't overthink it. It's just you anyway)");
            password = inFromClient.readLine();
            if (repository.read(login, password) == null) {
                break;
            }
            outToClient.println("Client with such login/password already exists");
        }
        GeneratorClientID id = GeneratorClientID.getInstance();
        User newUser = new User(id.getNewID(), login, password);
        repository.create(newUser);
        return newUser;
    }
}
