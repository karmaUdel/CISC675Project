/**
 * 
 */
package operations;

import java.util.ArrayList;

import elevatorunit.ElevatorThread;
import scheduler.IScheduler;
import scheduler.SchedulerImpl;

/**
 * @author Aditya
 *
 */

/**
 * 
 * TODO : 1. Stores maps  -- > Holds object of Scheduler and ElevatorUnit[]
 * TODO : 2. Calls updates --> Updates the operations and maps
 * TODO : 3. Calls schedule --> Calls Scheduler to provide an elevatorUnit to be called
 * TODO : 4. Calls storeValues --> Called when GUI animation terminates and time to store endResult on termination 
 * TODO : 5. Calls addDestination --> adds if inside buttons of elevators are clicked 
 * TODO : 6. Calls clearSignals --> kills returns kill the thread signal back to GUI, acts as the stop the motor
 * @author Aditya
 *
 */
public class Operations implements IOperation{
	
	IScheduler scheduler ;// Scheduler object
	ElevatorThread threads [];// Elevator Threads
	// TODO: Set of Constant Values
	public Operations() {
		super();
	}
	public Operations(int numberOfFloors, int numberofElevators, int schedulingAlgo ) {
		super();
		// assigning appropriate Values
		initiate(numberOfFloors, numberofElevators, schedulingAlgo);
	}
	@Override
	public ArrayList<Integer> update(int floor,int elevator, int button, String operation) {
		// TODO update the destination and service an elevator
		ArrayList<Integer> updatedValues = new ArrayList<Integer>(); // empty
		// call schedule
		if(floor != -1) {
			elevator = this.scheduler.scheduler(floor);
			updatedValues.add(elevator); //  add the elevatorId
			updatedValues.add(floor); // add Floor as request which needs to be served
		} // if request is made from outside of an elevator
		else {
			floor= button;
			updatedValues.add(elevator); // add elevator as it's an inside request
			updatedValues.add(floor); // this acts as floor request which is made inside of an elevator
		}
		// get ElevatorId
		// elevator related information
		this.threads[elevator].setDestination(floor);
		int direction = floor - this.threads[elevator].getLocation();
		if(direction > 1) {
			direction = 1;
		}else if(direction <0){
			direction = -1;
		}else {
			direction = 0;
		}
		this.threads[elevator].setDirection(direction); // as of now sending everyone up
		return updatedValues;
	}
	
	@Override
	public boolean storeMaps() {
		// TODO Reflect the changes
		return false;
	}
	@Override
	public Integer storeValues(ArrayList<Integer> values) {
		// TODO this is called when elevator animation ends 
		int elevator = values.get(0);
		int location = values.get(1);
		this.threads[elevator].setLocation(location);
		return null;
	}
	@Override
	public boolean clearSignals() {
		// TODO stop elevator abruptly
		return false;
	}
	
	/**
	 * this falls inside constructor
	 * TODO : It's job is to create elevator threads
	 * And create Scheduler object
	 * @param numberOfFloors
	 * @param numberOfElevators
	 */
	public void initiate( int numberOfFloors, int numberOfElevators, int schedulingAlgo) {
		this.threads = new ElevatorThread [numberOfElevators];
		for(int i = 0;i<numberOfElevators;i++) {
			this.threads[i] = new ElevatorThread(0, -1, 0);
		}
		this.scheduler = new SchedulerImpl();
		this.scheduler.setSettings(this.threads,schedulingAlgo);
	}
}
