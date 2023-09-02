package org.example.Storage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//Singleton
public class MessageStorage {
    private static volatile MessageStorage instance;

    public static volatile boolean serverIsDown = false;
    private volatile List<Message> syncList = Collections.synchronizedList(new LinkedList<>());
    public static MessageStorage getInstance(){
        MessageStorage localInstance = instance;
        if(localInstance ==  null){
            synchronized (MessageStorage.class){
                localInstance = instance;
                if(localInstance == null){
                    instance = localInstance = new MessageStorage();
                }
            }
        }
        return localInstance;
    }
    public synchronized void addNewMessage(Message message){
//        if(message.getClientId() == -1){
//            syncList.add(message);
//        }
        syncList.add(message);
    }
    public synchronized Message getNewMessage(int index) {
            if (index >= 0 && index < syncList.size()) {
                return syncList.get(index);
            }
            return null;
    }
}
