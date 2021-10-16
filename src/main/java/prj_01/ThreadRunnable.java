// Carlos Leyva Capote carlos.leyva@upr.edu
package prj_01;

/* Defines object containing methods runnable by threads. (Part II) */
public class ThreadRunnable implements Runnable {

    private boolean doStop = false;
    private RoundRobinCLL rr = null;

    /* Constructors */
    public ThreadRunnable() {
    }
    /*
     * @param rr RoundRobin Circular Linked List
     */
    public ThreadRunnable(RoundRobinCLL rr) {
        this.rr = rr;
    }

    /*
     * Method ran by threads.
     */
    @Override
    public void run() {
        System.out.println("Running Thread... This is Thread " + Thread.currentThread().getName());
        if (rr==null) {
            return;
        }
        while  (!rr.stopLoop) {
            // keep doing what this thread should do.
            rr.findEmptySlot();
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " Finished ... Bye Bye");
    }
}
