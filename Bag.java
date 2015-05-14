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
    
    /**
     * mark "ready to be grabed out" if identified by the sensor
     */
    public void set_ready_out(){
    	ready_out = true;
    	System.out.println("bag " + this.id + " is ready to be grabed out");
    }

    public String toString() {
        return "Bag(" + id + ")";
    }

    /**
     * return true when ready_out is false
     */
	public boolean not_ready_out() {
		if (ready_out == false){
			return true;
		}
		else return false;
	}

	public boolean ready_out() {
		return ready_out;
	}
}
