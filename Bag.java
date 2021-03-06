import java.util.*;

/**
 * A class representing a bag moving through baggage control
 */
public class Bag {
    protected static Random r = new Random();

    // specifies whether the bag needs checking
    protected boolean suspicious = false;

    // specifies whether the bag is clean
    protected boolean clean = true;
    
    // specify whether the bag is ready to be grabbed to be scanned
    protected boolean ready_out = false;

    // specify whether the bag is ready to be grabbed back to the main belt
    protected boolean ready_back = false;
    
    // the ID of this bag
    protected int id;

    // the next ID that can be allocated
    protected static int nextId = 1;

    // create a new bag with a given ID
    private Bag(int id) {
        this.id = id;
        if (r.nextFloat() < 0.1) {
            suspicious = true;
            clean = false;
        }
    }

    /**
     * Get a new Bag instance with its unique ID
     */
    public static Bag getInstance() {
        return new Bag(nextId++);
    }

    /**
     * @return the ID of this bag
     */
    public int getId() {
        return id;
    }

    /**
     * Mark this bag as clean
     */
    public void clean() {
        clean = true;
        suspicious = false;
        // prevent the bag to be scanned again
        // not needed when the short is working 
        	// set_not_ready_out();
        // specify the bag is checked and clean
        // not needed when the short belt is working
        	// set_ready_back();
    }

    /**
     * @return true if and only if this bag is marked as suspicious
     */
    public boolean isSuspicious() {
        return suspicious;
    }

    /**
     * Mark this bag as suspicious
     */
    public void setSuspicious() {
        suspicious = true;
    }

    /**
     * @return true if and only if this bag is marked as clean
     */
    public boolean isClean() {
        return clean;
    }

    public String toString() {
        return "Bag(" + id + ")";
    }

    // return true when ready_out is false
    // indicates the bag in segment 3 is not ready to be grabbed out
	public boolean not_ready_out() {
		if (ready_out == false){
			return true;
		}
		else return false;
	}

	//return true when ready_out is true
	public boolean is_ready_out() {
		return ready_out;
	}

	// return true when ready_back is false
	// indicates the bag in scanner is not ready to be grabbed back
	public boolean not_ready_back() {
		if (ready_back == false){
			return true;
		}
		else return false;
	}

	// check if bag at the scanner is ready to be grabbed back
	public boolean is_ready_back() {
		return ready_back;
	}

	// specify the bag is checked and clean
	// and ready to be moved back
	// not needed when a shout belt is working
	public void set_ready_back() {
    	ready_back = true;
    	System.out.println("bag " + this.id + 
    			" is ready to be grabed back");
	}

	// specify the bag should not be moved by the robot
	// even it is at segment 3
	public void set_not_ready_out() {
		ready_out = false;	
	}
	
	// set the bag as "ready out" when it is detected by the sensor
    public void set_ready_out(){
    	ready_out = true;
    }
}
