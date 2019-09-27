/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.wrappers.nodejs.githubAPIAccess.queueController;

import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author 
 */
public class QueueController {
    protected Queue<String> queue;
    protected HashSet<String> executed;

    public QueueController() {
        this.queue = new LinkedBlockingQueue<String>();
        this.executed = new HashSet<String>();
    }
    
    public void put(String value){
        if(!this.executed.contains(value)){
            this.executed.add(value);
            this.queue.add(value);
        }
    }
    
    public String pop(){
        String value = this.queue.poll();
        if(value==null)return null;
        System.out.println(value);
        return value;
    }
    
    public boolean isEmpty(){
        return queue.isEmpty();
    }
    
}
