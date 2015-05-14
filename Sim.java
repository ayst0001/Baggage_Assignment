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
        Sensor sensor = new Sensor(belt);
        Robot robot = new Robot(belt);
        Scanner scanner = new Scanner(belt);

        consumer.start();
        producer.start();
        mover.start();
        sensor.start();
        robot.start();
        scanner.start();

        while (consumer.isAlive() && 
               producer.isAlive() && 
               mover.isAlive() &&
               sensor.isAlive() &&
               robot.isAlive() &&
               scanner.isAlive())
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                BaggageHandlingThread.terminate(e);
            }

        // interrupt other threads
        consumer.interrupt();
        producer.interrupt();
        mover.interrupt();
        sensor.interrupt();
        robot.interrupt();
        scanner.interrupt();

        System.out.println("Sim terminating");
        System.out.println(BaggageHandlingThread.getTerminateException());
        System.exit(0);
    }
}
