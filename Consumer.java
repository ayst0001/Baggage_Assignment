import java.util.Random;

/**
 * A consumer continually tries to take bags from the end of a belt
 */

public class Consumer extends BaggageHandlingThread {

    // the maximum amount of time the consumer waits
    protected final static int MAX_SLEEP = 2800;

    // the belt from which the consumer takes the bags
    protected Belt belt;

    /**
     * Create a new Consumer that consumes from a belt
     */
    public Consumer(Belt belt) {
        super();
        this.belt = belt;
    }

    /**
     * Loop indefinitely trying to get bags from the baggage belt
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                Bag bag = belt.getEndBelt();

                // let some time pass ...
                Random random = new Random();
                int sleepTime = 500 + random.nextInt(MAX_SLEEP - 500);
                sleep(sleepTime);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("Consumer terminated");
    }
}
