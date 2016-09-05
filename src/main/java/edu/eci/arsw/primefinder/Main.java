package edu.eci.arsw.primefinder;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static PrimeFinderThread pft, pft2, pft3;

    public static void EntertoStart() throws IOException {

        //Scanner teclado = new Scanner(System.in);
        boolean validator = true;
        //char tecla = (char)System.in.read();
        System.out.println("|––––––––––––––––––––––––––––––––|\n"
                + "| Han pasado 5 Segundos ,Digite  |\n"
                + "| ENTER para continuar           |\n"
                + "|________________________________|\n");
        while (validator) {

            if (System.in.read() == KeyEvent.VK_ENTER) {

                PrimeFinderThread.control = true;
                validator = false;

            } else {
                System.out.println("No puedo continuar si no digita ENTER");
            }
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException {

        pft = new PrimeFinderThread(0, 10000000);
        pft2 = new PrimeFinderThread(10000000, 20000000);
        pft3 = new PrimeFinderThread(20000000, 30000000);

        pft.start();
        pft2.start();
        pft3.start();
        Thread.sleep(4000);
        pft.setControl(false);
        pft2.setControl(false);
        pft3.setControl(false);
         EntertoStart();
        /*Thread.sleep(5000);*/

       

        synchronized (PrimeFinderThread.key) {
            pft.getKey().notify();
            pft2.getKey().notify();
            pft3.getKey().notify();
        }

    }

}
