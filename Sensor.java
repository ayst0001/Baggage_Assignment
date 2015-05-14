/*
 * This is the class for the sensor
 * Which is located at segment 3 of the main belt
 * it detects the suspicious bags
 * */
public class Sensor extends BaggageHandlingThread {
	 // the belt to be handled
    protected Belt belt;

    // the amount of time it takes to check segment 3
    // It has to be slightly smaller than belt moving time
    // Which is 900
    protected final static int SENSE_TIME = 800;

    /**
     * Create a new Sensor with a belt to check
     */
    public Sensor(Belt belt) {
        super();
        this.belt = belt;
    }

    /**
     * 
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                // spend SENSE_TIME milliseconds until next check
                Thread.sleep(SENSE_TIME);
                belt.sense();
            }  catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("BeltSensor terminated");
    }
}
