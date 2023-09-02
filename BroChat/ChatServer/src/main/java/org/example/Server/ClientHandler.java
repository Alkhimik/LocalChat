package org.example.Server;

import org.example.Authorization.SignIn;
import org.example.Authorization.SignUp;
import org.example.Authorization.User;
import org.example.Data.Repository;
import org.example.Data.SpringConfiguration.SpringConfig;
import org.example.Storage.Message;
import org.example.Storage.MessageStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientHandler implements Runnable{
    private final PrintWriter outToClient;
    private final BufferedReader inFromClient;
    private Repository repository;

    public ClientHandler(PrintWriter outToClient, BufferedReader inFromClient, int clientId) {
        this.outToClient = outToClient;
        this.inFromClient = inFromClient;
    }

    public void run() {
        MessageStorage storage = MessageStorage.getInstance();
        String clientResponse = null;
        outToClient.println("Hello from Server!");
        repository = new Repository(new SpringConfig().jdbcTemplate());
        Optional<User> user = getLogIn();

        while (user.isEmpty()){
            outToClient.println("Something went wrong. Try again: ");
            user = getLogIn();
        }

        outToClient.println("I know you now! Mister " + user.get().getLogin() + "!");
        outToClient.println("                           Chat started\n----------------------------------------------------------------------");
        UserExistence userHere = new UserExistence();
        Thread sendToAll = new Thread(new SendMessagesToClients(outToClient,user.get(),userHere));
        sendToAll.start();

        while(true){
            try {
                if(!((clientResponse = inFromClient.readLine()) != null))break;
            }catch(IOException ignored){}

            if(clientResponse != null && clientResponse.matches("Exit")){
                userHere.setUserInChat(false);
                storage.addNewMessage(new Message(user.get().getId(), user.get().getLogin(),  user.get().getLogin() + " exited BroChat\n----------------------------------------------------------------------"));
                break;
            }
            if(clientResponse != null){
                storage.addNewMessage(new Message(user.get().getId(), user.get().getLogin(), clientResponse));
            }
        }
    }

    public Optional<User> getLogIn() {
        Future<User> client;
        outToClient.println("signIn or signUp?");
        while(true){
            try {
                String action = inFromClient.readLine();
                if(action.matches("signUp") || action.matches("up")){
                    ExecutorService service = Executors.newFixedThreadPool(1);
                    client = service.submit(new SignUp(repository, outToClient, inFromClient));
                    break;
                }else if(action.matches("signIn") || action.matches("in")){
                    ExecutorService service = Executors.newFixedThreadPool(1);
                    client = service.submit(new SignIn(repository, outToClient, inFromClient));
                    break;
                }else{
                    outToClient.println("Wrong command.\nsignIn or signUp?");
                    throw new IOException("Wrong syntax");
                }
            } catch (Exception ignored) {}
        }
        try {
            return Optional.ofNullable(client.get());
        } catch (ExecutionException | InterruptedException e) {
            return Optional.empty();
        }
    }


}