// Carlos Leyva Capote carlos.leyva@upr.edu
package prj_01;

public class RRScheduler {
	/**
	 * Main method. Acts as the main processes in the scheduler simulator.
	 * Initiates the RoundRobin CLL and threads (Part II). 
	 * Command line argument parser created.
	 * 
	 * @param -t || --termination : amount of main process iterations performed over the CLL. Default: 100.
	 * @param -p || --processes : amount of threads to create. Default: 5.
	 * @param -s || --prjstep : part of project to run (1 || 2). Default: 1.
	*/
    public static void main(String[] args){
        int termination_limit = 100;
        int no_threads = 5;
        int project_step = 2;
        // Parses over command line argument
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("-t") || args[i].equals("--termination")) {
                termination_limit = Integer.valueOf(args[++i]);
            }
            else if (args[i].equals("-p") || args[i].equals("--processes")) {
                no_threads = Integer.valueOf(args[++i]);
            }
            else if (args[i].equals("-s") || args[i].equals("--prjstep")) {
                project_step = Integer.valueOf(args[++i]);
                if (project_step!=1 && project_step!=2) {
                    System.out.println("Project Step value is 1 or 2 (" + project_step + " given).");
                    System.exit(1);
                }
            }
        }

        System.out.println("Starting Program...");


        RoundRobinCLL roundRobine = null;
        if (project_step==2) {
            roundRobine =  new RoundRobinCLL(12, termination_limit);
        }
        
        // Creates ThreadRunnable object containing ("has a" relationship) the RoundRobinCLL object
        ThreadRunnable rrRunnable = new ThreadRunnable(roundRobine);
        // Creates Threads object containing an ArrayList of created Thread objects each containing ThreadRunnable object
        Threads threads = new Threads(no_threads, rrRunnable);

        for (int i=0; i<threads.threads.size(); i++) {
        	// in threads object's threads ArrayList, get thread at index i and .start()
        	// .start() creates thread of execution to run all thread .run() methods in parallel
            threads.threads.get(i).start();
        }

        if (roundRobine!=null) roundRobine.findFilledSlot() ;

        System.out.println("Main Finished ... Bye Bye");

        if (roundRobine!=null) roundRobine.stopLoop = true;

    }
}
