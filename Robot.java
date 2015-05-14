/*
 * This is the class for the robot
 * Which moves suspicious bags between segment 3 and scanner
 * */

// Robot is also a form of baggage handling entities
public class Robot extends BaggageHandlingThread {
	 // the belt to be handled
    protected Belt belt;

    // the amount of time it takes to move the belt
    protected final static int MOVE_TIME = 500;

    /**
     * Create a new BeltMover with a belt to move
     */
    public Robot(Belt belt) {
        super();
        this.belt = belt;
    }

    /**
     * Move the belt as often as possible, but only if there 
     * is a bag on the belt which is not in the last position.
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                // spend MOVE_TIME milliseconds moving the belt
                Thread.sleep(MOVE_TIME);
                belt.grab();
            }  catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("BeltMover terminated");
    }

}
