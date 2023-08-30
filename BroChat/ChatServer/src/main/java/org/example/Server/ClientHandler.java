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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientHandler implements Runnable{
    private PrintWriter outToClient;
    private BufferedReader inFromClient;
    private int clientId;
    private Repository repository;

    public ClientHandler(PrintWriter outToClient, BufferedReader inFromClient, int clientId) {
        this.outToClient = outToClient;
        this.inFromClient = inFromClient;
        this.clientId = clientId;
    }

    public void run() {
        MessageStorage storage = MessageStorage.getInstance();
        String clientResponse;
        outToClient.println("Hello from Server!");
        repository = new Repository(new SpringConfig().jdbcTemplate());
        User user = getLogIn();

        outToClient.println("I know you now! Mister " + user.getLogin() + "!");
        outToClient.println("                           Chat started\n----------------------------------------------------------------------");

        UserExistence userHere = new UserExistence();
        Thread sendToAll = new Thread(new SendMessagesToClients(outToClient,user,userHere));
        sendToAll.start();

        while(true){
            try {
                if(!((clientResponse = inFromClient.readLine()) != null))break;
            }catch(IOException e){throw new RuntimeException(e);}
            if(clientResponse.matches("Exit")){
                userHere.setUserInChat(false);
                storage.addNewMessage(new Message(user.getId(), user.getLogin(),  user.getLogin() + " exited BroChat\n----------------------------------------------------------------------"));
                break;
            }
            storage.addNewMessage(new Message(user.getId(), user.getLogin(), clientResponse));
        }
    }

    public User getLogIn() {
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
            } catch (IOException e) {} catch (Exception e) {}
        }
        try {
            return client.get();
        } catch (ExecutionException e) {
            return null;
        } catch (InterruptedException e) {
            return null;
        }
    }


}