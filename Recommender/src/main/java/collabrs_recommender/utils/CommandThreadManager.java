/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.utils;

import java.util.HashMap;
import java.util.Set;
import collabrs_recommender.wrappers.nodejs.githubAPIAccess.CommandService;

/**
 *
 * @author 
 */
public class CommandThreadManager {
    
    private static final CommandThreadManager instance = new CommandThreadManager();
    private final HashMap<String,Thread> threads = new HashMap<String, Thread>();
    private final HashMap<String,CommandService> commands = new HashMap<String, CommandService>();
    
    private CommandThreadManager(){
        
    }
    
    public static CommandThreadManager getInstance(){
        return instance;
    }
    
    public void addThread(String key, CommandService service){
        threads.put(key, new Thread(service));
        commands.put(key, service);
    }
    
    public Thread getThread(String key){
        return threads.get(key);
    }
    
    public CommandService getCommandService(String key){
        return commands.get(key);
    }
    
    public boolean isAlive(String key){
        return threads.get(key).isAlive();
    }
    
    public void interrupt(String key){
        commands.get(key).setBreakFlag(true);
    }
    
    public void join(String key) throws InterruptedException{
        threads.get(key).join();
    }
    
    public void start(String key){
        threads.get(key).setName(key);
        threads.get(key).start();
    }
    
    public Set<String> keySet(){
        return threads.keySet();
    }
    
}
