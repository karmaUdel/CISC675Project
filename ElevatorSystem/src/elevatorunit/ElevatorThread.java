/**
 * 
 */
package elevatorunit;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Aditya
 *
 */
public class ElevatorThread extends Thread {
	
	private IMotor motor;
	private IDoor door;
    private static int count = 0; //  increments on every object creation
    private int elevatorId; // value assigned when every time new object is created
    private ILights lights;
	private int location; // default value is 0
	private int destination; // default value is -1
	private int direction; // default value is 0, 1: Going up, 0: Stationary, -1: Going Down
	private ArrayList<Integer> destinationList;
	public ArrayList<Integer> getDestinationList() {
		return sort(destinationList);
	}

	public void setDestinationList(ArrayList<Integer> destinationList) {
		this.destinationList = sort(destinationList);
	}

	public int getElevatorId() {
		return elevatorId;
	}
	
	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
		if(this.direction == 0) {
			move("stop");
		}else {
			move("start");
		}
	}
	
	/**
	 * Default Constructor
	 */
	public ElevatorThread() {
		super();
		this.location = 0;
		this.destination = -1;
		this.direction = 0;
		this.elevatorId = count++;
		this.destinationList = new ArrayList<Integer>();		
		this.motor = new MotorImpl();
		this.lights = new LightImpl(10);
		this.door = new DoorImpl();
	}
	/**
	 * Specialized constructor
	 * @param location
	 * @param destination
	 * @param direction
	 */
	public ElevatorThread(int location,int destination,int direction,int floors) {
		super();
		this.location = location;
		this.destination = destination;
		this.direction = direction;
		this.elevatorId = count++;
		this.destinationList = new ArrayList<Integer>();
		this.motor = new MotorImpl();
		this.lights = new LightImpl(floors);
		this.door = new DoorImpl();
	}
	public boolean isMoving() {
		if(this.getDirection()==0) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * Sort the destinatin List
	 * @param list
	 * @return
	 */
	public ArrayList<Integer> sort(ArrayList<Integer> list) {
		Collections.sort(list);// sort ascending 0,1,2,3....
		if(this.getDirection()== -1){
			Collections.reverse(list); // if direction is -1 then reverse the order
		}
		return list;
	}
	/**
	 * Turn off or Turn on the motor for an elevator
	 * @param perform
	 * @return
	 */
	public boolean move(String perform) {
		if ("stop".equalsIgnoreCase(perform)){
			return this.motor.stopMotor();
		}{
			return this.motor.startMotor();
		}
	}
	/**
	 * Perform change in lights based on operation
	 * @param perform
	 * @param button
	 * @return
	 */
	public boolean changeLights(String perform, int button) {
		if ("off".equalsIgnoreCase(perform)){
			return this.lights.turnOff(button);
		}{
			return this.lights.turnOn(button);
		}
	}
	/**
	 * Look if door open or close ?
	 * @return
	 */
	public boolean checkDoors() {
		if(this.door.operate()) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * Close and Open door
	 */
	public boolean operateDoors() {
		if(checkDoors()) {
			return false;
		}else {
			return true;
		}
	}
}
