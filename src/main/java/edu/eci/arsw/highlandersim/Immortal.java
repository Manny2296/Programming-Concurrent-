package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Immortal extends Thread {

    private int health;
    
    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());

    boolean pause = false;
    static boolean control = true;
    static Object lock;

    public void pause() {
        pause = true;
    }

    public void cont() {
        pause = false;
    }

    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue) {
        super(name);
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        lock = new Object();
        this.defaultDamageValue=defaultDamageValue;
    }

    public void run() {
         
        while (true) {
            
            try {
                Immortal im;
                
                int myIndex = immortalsPopulation.indexOf(this);
                
                int nextFighterIndex = r.nextInt(immortalsPopulation.size());
                
                //avoid self-fight
                if (nextFighterIndex == myIndex) {
                    nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
                }
                
                im = immortalsPopulation.get(nextFighterIndex);
         
             
                this.fight(im);
                
               
                      
                
                
                
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Immortal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public  Object getLock() {
        return lock;
    }

    public  void setLock(Object lock) {
        Immortal.lock = lock;
    }

    public void fight(Immortal i2) throws InterruptedException {
        
     synchronized(lock){
        if(control){ 
        if (i2.getHealth() > 0) {
            i2.changeHealth(i2.getHealth() - defaultDamageValue);
            this.health += defaultDamageValue;
            System.out.println("Fight: " + this + " vs " + i2);
        } else {
            System.out.println(this + " says:" + i2 + " is already dead!");
        }}
        else{
           // System.out.println(".");
          lock.wait();
        }
     
     }   
  
    }

    

    public synchronized void changeHealth(int v) {
        health = v;
    }

    public synchronized  int getHealth() {
        return health;
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

}
