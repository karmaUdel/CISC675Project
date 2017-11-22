/**
 * 
 */
package simulator;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author Aditya Karmarkar
 * Job : This is main class which controls everyhting 
 */
public class Simulator {
	
	private Scheduler scheduler; // scheduler 
    private int numberOfFloors; // how many floors building has
    public int getNumberOfFloors() {
		return numberOfFloors;
	}
	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}
	private int numberOfElevators; // how many elevators building has
    public int getNumberOfElevators() {
		return numberOfElevators;
	}
	public void setNumberOfElevators(int numberOfElevators) {
		this.numberOfElevators = numberOfElevators;
	}
	public AtomicIntegerArray getElevatorPosition() {
		return elevatorPosition;
	}
	public void setElevatorPosition(AtomicIntegerArray elevatorPosition) {
		this.elevatorPosition = elevatorPosition;
	}
	public AtomicIntegerArray elevatorPosition; // position map for every elevator
    public AtomicIntegerArray requestFromFloor; // position of request received it toggles 0-1 
    public AtomicIntegerArray destinationOfElevator; // destination of elevator which is motion
    // TODO data structure which stores all entered location for lift 
    
    /**
	 * Constructor for default configuration
	 */
	public Simulator() {
		super();
		this.numberOfFloors = 10;
		this.numberOfElevators = 5;
		this.elevatorPosition = new AtomicIntegerArray(this.numberOfElevators);
		this.requestFromFloor =  new AtomicIntegerArray(this.numberOfFloors);
		this.destinationOfElevator = new AtomicIntegerArray(this.numberOfElevators); 
		this.scheduler = new Scheduler();
	}
	/**
	 * Constructor for Specific configuration
	 * @param floors
	 * @param elevators
	 */
	public Simulator(int floors, int elevators) {
		this.numberOfFloors = floors;
		this.numberOfElevators = elevators;
		this.elevatorPosition = new AtomicIntegerArray(this.numberOfElevators);
		this.requestFromFloor =  new AtomicIntegerArray(this.numberOfFloors);
		this.destinationOfElevator = new AtomicIntegerArray(this.numberOfElevators);		
		this.scheduler = new Scheduler();
	}
	/**
	 * Calls scheduler to schedule 
	 * @return int
	 */
	public int schedule() {
		scheduler.schedule(this);
		return 0;
	}

}
