package org.example.Storage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//Singleton
public class MessageStorage {
    private static volatile MessageStorage instance;
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
        syncList.add(message);
    }
    public synchronized Message getNewMessage(int index) {
            if (index < syncList.size()) {
                return syncList.get(index);
            }
            return null;
    }

}
