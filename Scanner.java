/*
 * This is the class for scanner
 * which is located along side the segment 3 of the main belt
 * It scans all suspicious bags
 * In this case, they will all be marked ad clean when scanned
 * */

// Scanner is also a form of baggage handling entities
public class Scanner extends BaggageHandlingThread {
	  // the belt to be handled
    protected Belt belt;

    // the amount of time it takes to scan a bag
    protected final static int SCAN_TIME = 900;

    /**
     * Create a new Scanner with a belt to operate with
     */
    public Scanner(Belt belt) {
        super();
        this.belt = belt;
    }

    /**
     * Scan the bag when a suspicious bag arrives
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                // spend SCAN_TIME milliseconds moving the belt
                Thread.sleep(SCAN_TIME);
                belt.scan();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("Scanner terminated");
    }
}
