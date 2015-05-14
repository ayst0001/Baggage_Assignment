/**
 * The baggage belt
 */
public class Belt {

    // the items in the belt segments
    protected Bag[] segment;
    
    // the items in the scanner
    // considering scanner as part of the belt here
    protected Bag scanner;

    // the length of this belt
    protected int beltLength = 5;

    // the animation to keep up to date
    protected Animation a;

    // the belt type (for including in exception messages)
    // protected String type;

    //indentation for output layout
    final private static String indentation = "                 ";

    /**
     * Create a new, empty belt, initialized as empty
     */
    public Belt(Animation a) {
        segment = new Bag[beltLength];
        for (int i = 0; i < segment.length; i++) {
            segment[i] = null;
        }
        this.a = a;
    }

    /**
     * Put a bag on the belt.
     * 
     * @param bag
     *            the bag to put onto the belt.
     * @param index
     *            the place to put the bag
     * @throws InterruptedException
     *             if the thread executing is interrupted.
     */
    public synchronized void put(Bag bag, int index)
            throws InterruptedException {
        // while there is another bag in the way, block this thread
        while (segment[index] != null) {
            wait();
        }

        // insert the element at the specified location
        segment[index] = bag;

        // make a note of the event
        if (bag.isSuspicious()) {
            System.out.println(bag.getId() + " arrived (sus)");
        } else {
            System.out.println(bag.getId() + " arrived");
        }

        // notify any waiting threads that the belt has changed
        notifyAll();
    }

    /**
     * Take a bag off the end of the belt
     * 
     * @return the removed bag
     * @throws InterruptedException
     *             if the thread executing is interrupted
     */
    public synchronized Bag getEndBelt() throws InterruptedException {

        Bag bag;

        while (segment[segment.length-1] == null) {
            wait();
        }

        // get the next item
        bag = segment[segment.length-1];
        segment[segment.length-1] = null;

        // make a note of the event
        System.out.print(indentation);
        if (!bag.isClean()) {
            System.out.println(bag.getId() + " departed -- unclean!!!");
        } else {
            System.out.println(bag.getId() + " departed");
        }

        // notify any waiting threads that the belt has changed
        notifyAll();
        return bag;
    }

    /**
     * Move the belt along one segment
     * 
     * @throws OverloadException
     *             if there is a bag at position beltLength.
     * @throws InterruptedException
     *             if the thread executing is interrupted.
     */
    public synchronized void move() 
            throws InterruptedException, OverloadException {
    	// if there is something at the end of the belt, or the belt is empty,
        // or something needs to be picked up for a scan, do not move the belt
        while (isEmpty() || segment[segment.length-1] != null ||
        		(segment[2] != null && segment[2].isSuspicious()) ) {
            wait();
        }

        // double check that a bag cannot fall of the end
        if (segment[segment.length-1] != null) {
            String message = "Bag fell off end of " + " belt";
            throw new OverloadException(message);
        }

        // move the elements along, making position 0 null
        for (int i = segment.length-1; i > 0; i--) {
            segment[i] = segment[i-1];
        }
        segment[0] = null;
        a.animateMove(this);

        // notify any waiting threads that the belt has changed
        notifyAll();
    }

    /*
     * Check the segment 3 if the bag there is suspicious
     */
    
    public synchronized Bag sense() throws InterruptedException {
    	//When segment 3 is empty or the bag there is clean, wait
    	while (segment[2] == null ||
    			(segment[2]!=null && segment[2].isClean())){
    		wait();
    	}
    	
    	//Double check, when segment 3 is not empty
    	//and bag on segment 3 is suspicious
    	if (segment[2] != null && segment[2].isSuspicious()){
    		System.out.println("bag "+ segment[2].getId()
    				+ " on segment 3 is suspicious, "
    				+ "waiting to be moved");
    		segment[2].set_ready_out();
    	}
    	return segment[2];
    }
    
    /*
     * Robot grabs and moves suspicious bag out off segment[2]
     */
    
    public synchronized Bag grab() throws InterruptedException {
    	//If there's nothing to be moved between the scanner and the belt
    	// or if the destination is occupied
    	while (nothing_to_grab() || cannot_grab()){
    		wait();
    	}
    	//Double check, when segment 3 is not empty
    	//and bag on segment 3 is ready to be moved
    	if (segment[2] != null && segment[2].ready_out() && scanner == null){
    		grab_to_scanner();
    	}
    	
    	else if (scanner != null && scanner.ready_back() && segment[2] == null){
    		grab_back();
    	}
    	return segment[2];
    }
    
    /*
     * Robot grabs and moves suspicious bag out off segment[2]
     */
    
    // To move suspicious bags to the scanner
    private void grab_to_scanner() {
    	System.out.println("Grabing out suspicious bag");
		scanner = segment[2];
		segment[2] = null;
		System.out.println("bag " + scanner.getId() + " is moved to scanner");
	}

	// To specify if there's anything need to be moved by the robot
    private boolean nothing_to_grab() {
    	// if segment 3 is empty or not identified as suspicious
    	if (segment[2] == null ||
    			(segment[2]!=null && segment[2].not_ready_out())){
    		// also if scanner is empty or have not been cleaned
    		if (scanner == null || (scanner != null && scanner.not_ready_back())){
    			return true;
    			}
    		else return false;
    	}
    	else return false;
	}

	public synchronized Bag scan() throws InterruptedException {
    	//When scanner is null, wait
    	while (scanner == null || (scanner != null && scanner.isClean())){
    		wait();
    	}
    	
    	//Double check, when segment 3 is not empty
    	//and bag on segment 3 is ready to be moved
    	if (scanner != null && scanner.isSuspicious()){
    		System.out.println("Start scanning bag");
    		scanner.clean();
    		System.out.println("bag " + scanner.getId() + " is cleaned");
    		
    	}
    	return scanner;
    }
    
    
    
    /**
     * @return the maximum size of this belt
     */
    public int length() {
        return beltLength;
    }

    /**
     * Peek at what is at a specified segment
     * 
     * @param index
     *            the index at which to peek
     * @return the bag in the segment (or null if the segment is empty)
     */
    public Bag peek(int index) {
        Bag result = null;
        if (index >= 0 && index < beltLength) {
            result = segment[index];
        }
        return result;
    }

    // check whether the belt is currently empty
    private boolean isEmpty() {
        for (int i = 0; i < segment.length; i++) {
            if (segment[i] != null) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return java.util.Arrays.toString(segment);
    }

    /*
     * @return the final position on the belt
     */
    public int getEndPos() {
        return beltLength-1;
    }
}
