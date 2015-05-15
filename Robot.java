/*
 * This is the class for the robot
 * Which moves suspicious bags between segment 3 and scanner
 * 
 * */

// Robot is also a form of baggage handling entities
public class Robot extends BaggageHandlingThread {
	 // the belt to be handled
    protected Belt belt;

    // the amount of time it takes to check and grab bags
    protected final static int GRAB_TIME = 500;

    /**
     * Create a new Robot with a belt to operate on
     */
    public Robot(Belt belt) {
        super();
        this.belt = belt;
    }

    /**
     * Grab the bags according to the set interval
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                // spend GRAB_TIME milliseconds moving the belt
                Thread.sleep(GRAB_TIME);
                belt.grab();
            }  catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("Robot terminated");
    }

}
