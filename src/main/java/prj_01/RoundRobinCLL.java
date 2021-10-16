// Carlos Leyva Capote carlos.leyva@upr.edu
package prj_01;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Defines Node class for CLL
*/
class Node {
    public int id;
    public Node next;
    public Node previous;
    public Boolean proccessed_flag;

    public Node (int id) {
        this.id = id;
        proccessed_flag = true;
    }
}

/**
 * Defines RoundRobin interface with abstract methods
*/
interface RoundRobinCLLInterface {
    abstract void findEmptySlot();
    abstract void findFilledSlot();
}

/**
 * Defines RoundRobin Circular Linked List and its methods
*/
public class RoundRobinCLL implements RoundRobinCLLInterface {
    private int num_nodes = 5;
    public Node head = null;
    public Node tail = null;
    public Boolean stopLoop = false;
    private int termination_limit;

    /* 
     * Holds thread for a random amount of time of range (500, 3000)
     */
    private void holdon() {
        try{
            Thread.currentThread().sleep(ThreadLocalRandom.current().nextInt(500, 3000));
        }
        catch(Exception e){
            System.out.println("Something went wrong.");
        }
    }

    /**
     * Returns a representation of the Nodes' state in the RoundRobinCLL of the current thread 
     * @return String
     */
    @Override
    public String toString () {
        String s = new String(""+ Thread.currentThread().getName() + " ");
        Node node = head;
        s+= "(Node-1: " + node.proccessed_flag + ")";
        s+= " ==> ";

        for (int i=1; i<num_nodes; i++) {
            node = node.next;
            s+= "(Node-"+(i+1)+": "+node.proccessed_flag + ")";
            if (i<num_nodes-1)
                s+= " ==> ";
        }
        return s;
    }

    /**
     * The current thread changes the processed state of the node
     * @param node whose processed state will be changed
     * @param set_slot state of processed to change to
     */
    private synchronized void holdRR(Node node, Boolean set_slot) {
        System.out.println("Thread " + Thread.currentThread().getName() + " Holding Resources");
        node.proccessed_flag = set_slot ;
        System.out.println("Thread " + Thread.currentThread().getName() + " Releasing Resources");
        if (set_slot) holdon();
    }

    /**
     * Worker process traversal.
     * Finds first processed node and changes it to unprocessed.
     */
    public void findEmptySlot() {
        holdon();
        /* STARTING FROM THE FIRST NODE IN THE LINKED LIST */
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/
        /*** TO AVOID RACE CONDITION ***/
        Node curr = head;
        // Checks all nodes, starting from head
        for (int i = 0; i < this.num_nodes; i++) {
        	// If node has not been processed
        	if (curr.proccessed_flag == true) {
        		// Change processed state in parallel
        		holdRR(curr, false);
        		// Exits the method after finding first not processed node
        		return;
        	}
        	curr = curr.next;
        }
    }

    /**
     * Main process traversal through Circular Linked List
     * Looks for unprocessed nodes.
     * Changes unprocessed nodes to processed.
     */
    public void findFilledSlot() {
        /* PUT YOUR CODE HERE TO FIND THE FILLED SLOTS */
        /* FOR THE MAIN PROCESS                        */
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/
    	holdon();
    	int count = 0 ;
        Node curr = head;
        while (!stopLoop) {
        	// If finished a traversal and add to counter
        	if (curr == tail) {
        		count++;
        	}
        	// If current node has not been processed, process
        	if (curr.proccessed_flag == false) {
        		holdRR(curr, true);
        	}
            // If reached termination limit reached, set while condition to false
            if (count>termination_limit) stopLoop = !stopLoop;
            System.out.println("Main Move No.: " + count%num_nodes + "\t" + toString());
            // Change to next node
            curr = curr.next;
            //count++;
        }
    }

    /* 
     * Initiate circular linked list with desired amount of nodes (as set by num_nodes) 
     */  	
    private void fillRoundRubin () {
    	// If only one node, needs more (at least head and tail)
    	if (num_nodes <= 1) {
    		System.out.println("Needs more than one node.");
    		return;
    	}
    	// If only two nodes, add head and tail
    	else if (num_nodes == 2) {
    		head = new Node(0);
        	tail = new Node(1);
        	// Set node references
        	head.next = tail;
        	head.previous = tail;
        	tail.next = head;
        	tail.previous = head;
        	// Exit function
        	return;
    	}
    	// Set first two nodes
    	head = new Node(0);
    	head.next = new Node(1);
    	head.next.previous = head;
    	Node curr = head.next; 
    	// Add rest of nodes
    	for (int i = 2; i < this.num_nodes - 1; i++) {
    		curr.next = new Node(i);
    		curr.next.previous = curr;
    		curr = curr.next;
    	}
    	// Add tail
    	tail = new Node(this.num_nodes-1);
    	curr.next = tail;
    	tail.previous = curr;
    	tail.next = head;
    	head.previous = tail;
    }
    
    /* Constructors 
     * @param num_nodes number of nodes created
     * @param termination_limit number of traversal over nodes
     */
    public RoundRobinCLL(int num_nodes, int termination_limit) {
        this.num_nodes = num_nodes;
        this.termination_limit = termination_limit;
        fillRoundRubin();
    }
    /* Constructors 
     * @param num_nodes number of nodes created
     */
    public RoundRobinCLL(int num_nodes) {
        this.num_nodes = num_nodes;
        fillRoundRubin();
    }
    /* Constructor */
    public RoundRobinCLL() {
        fillRoundRubin();
    }

}
