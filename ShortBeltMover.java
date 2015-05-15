/*
 * A shortbeltmover moves the short belt along as often as possible
 */

// ShortBeltMover is also a form of baggage handling entities
public class ShortBeltMover extends BaggageHandlingThread {

    // the belt to be handled
    protected Belt belt;

    // the amount of time it takes to move the belt
    protected final static int MOVE_TIME = 900;

    /**
     * Create a new ShortBeltMover with a belt to operate
     */
    public ShortBeltMover(Belt belt) {
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
                // spend MOVE_TIME milliseconds moving the short belt
                Thread.sleep(MOVE_TIME);
                belt.shortmove();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("ShortBeltMover terminated");
    }
}
