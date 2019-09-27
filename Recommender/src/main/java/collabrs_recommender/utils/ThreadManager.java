/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collabrs_recommender.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class ThreadManager implements Runnable {

    private final List<Runnable> threads;
    private final List<Thread> running;
    private static final int MAX_THREADS = 15;
    private boolean halt;
    private final String name;

    public ThreadManager(String name) {
        halt = false;
        threads = new ArrayList<Runnable>();
        running = new ArrayList<Thread>();
        this.name = name;
    }

    public void run() {
        while (!halt) {
            int count = MAX_THREADS;
            ArrayList<Thread> stopped = new ArrayList<Thread>();
            for (Thread thread : running) {
                if (!thread.isAlive()) {
                    stopped.add(thread);
                }else{
                    count--;
                }
            }
            for(Thread thread : stopped){
                thread.interrupt();
                running.remove(thread);
            }
            for (int i = 0; i < count; i++) {
                if (threads.size() >= 1) {
                    Runnable runnable = threads.get(0);
                    threads.remove(0);
                    Thread thread = new Thread(runnable);
                    thread.setName(name);
                    running.add(thread);
                    thread.start();
                }else break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Thread thread : running) {
            thread.interrupt();
        }
    }

    public boolean isAlive() {
        return !running.isEmpty() || !threads.isEmpty() ;
    }

    public void interrupt() {
        halt = true;
    }
    
    public void add(Runnable runnable){
        threads.add(runnable);
    }

}
