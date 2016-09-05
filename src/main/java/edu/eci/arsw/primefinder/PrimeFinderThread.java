package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimeFinderThread extends Thread{

	
	int a,b,c;
	static Object key;
	private List<Integer> primes=new LinkedList<Integer>();
       static  boolean control = true;
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
                key = new Object();
                System.out.println("|_____________________________________|\n"
                                +  "| Programa para validar si los numeros|\n"
                                 + "| son primos de manera concurrente    |\n"
                                 + "|_____________________________________|\n");
                
	}

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public void run() {
        System.out.println("|____________________________________|\n");
        for (int i = a; i <= b; i++) {

            if (control) {
                if (isPrime(i)) {
                    primes.add(i);
                    System.out.println("|Numero  ----> "+i + "Es primo|\n");

                } 
            }else{
                try {
            /*        System.out.println("Estoy con hilos Sincronizados");*/
                    synchronized(key){
                    key.wait();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrimeFinderThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("|____________________________________|\n");

    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    boolean isPrime(int n) {
      
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
          c++;
        return true;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

}
