/*
 * This is the class for scanner
 * which is located along side the segment 3 of the main belt
 * It scans all suspicious baggs
 * In this case, they will all be clean
 * To make it potentially extensible, interface for handling unclean bags
 * will be provided as well
 * */
public class Scanner extends BaggageHandlingThread {
	  // the belt to be handled
    protected Belt belt;

    // the amount of time it takes to move the belt
    protected final static int MOVE_TIME = 900;

    /**
     * Create a new BeltMover with a belt to move
     */
    public Scanner(Belt belt) {
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
                belt.scan();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("BeltMover terminated");
    }
}
