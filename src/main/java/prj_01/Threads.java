// Carlos Leyva Capote carlos.leyva@upr.edu
package prj_01;
import java.util.*;

/** 
 * Defines Threads as used in the RSS.
 * Creates object containing an ArrayList threads to hold created Thread objects containing ThreadRunnable object.
*/
public class Threads {
    public ArrayList<Thread> threads = new ArrayList<Thread>();
    
    /* Constructors */
    /*
     * @param noThreads number of threads to be created
     */
    public Threads(int noThreads){
       for (int i=0; i<noThreads; i++){
           ThreadRunnable runnable = new ThreadRunnable();
           System.out.println("Creating Thread " + (i+1));
           threads.add(new Thread(runnable, ""+i));
       }
    }
    /*
     * @param noThreads number of threads to be created
     * @param runnable process run by threads
     */
    public Threads(int noThreads, ThreadRunnable runnable){
        for (int i=0; i<noThreads; i++){
            System.out.println("Creating Thread " + (i+1));
            threads.add(new Thread(runnable, ""+i));
        }
    }
}
