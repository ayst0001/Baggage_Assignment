/**
 * The driver of the simulation 
 */

public class Sim {
    /**
     * Create all components and start all of the threads
     */
    public static void main(String[] args) {
        
        Animation a = new Animation();
        Belt belt = new Belt(a);
        Producer producer = new Producer(belt);
        Consumer consumer = new Consumer(belt);
        BeltMover mover = new BeltMover(belt);

        consumer.start();
        producer.start();
        mover.start();

        while (consumer.isAlive() && 
               producer.isAlive() && 
               mover.isAlive() )
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                BaggageHandlingThread.terminate(e);
            }

        // interrupt other threads
        consumer.interrupt();
        producer.interrupt();
        mover.interrupt();

        System.out.println("Sim terminating");
        System.out.println(BaggageHandlingThread.getTerminateException());
        System.exit(0);
    }
}
