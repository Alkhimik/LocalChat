package org.example.Authorization;


import org.example.Data.Repository;
import org.example.Data.SpringConfiguration.SpringConfig;
import java.util.Optional;


//singleton
public class GeneratorClientID {
    private static volatile GeneratorClientID instance;
    private static volatile Integer newClientId;
    static Repository repository = new Repository(new SpringConfig().jdbcTemplate());

    public static GeneratorClientID getInstance(){
        GeneratorClientID localInstance = instance;
        if(localInstance ==  null){
            synchronized (GeneratorClientID.class){
                localInstance = instance;
                if(localInstance == null){
                    Optional<Integer> id = repository.findMaxID();
                    if(id.isEmpty()){
                        newClientId = 1;
                    }else {
                        newClientId = id.get();
                    }

                    instance = localInstance = new GeneratorClientID();
                }
            }
        }
        return localInstance;
    }

    public synchronized Integer getNewID(){
        return ++newClientId;
    }

}