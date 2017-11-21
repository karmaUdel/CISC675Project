/**
 * 
 */
package simulator;

/**
 * @author Aditya
 *
 */
public class ElevatorThread extends Thread {
	
    private static int count = 0; //  increments on every object creation
    private int elevatorId; // value assigned when every time new object is created

	private int location; // default value is 0
	private int destination; // default value is -1
	private int direction; // default value is 0, 1: Going up, 0: Stationary, -1: Going Down
	
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
	}
	
	/**
	 * 
	 */
	public ElevatorThread() {
		super();
		this.location = 0;
		this.destination = -1;
		this.direction = 0;
		this.elevatorId = count++;
	}

	/**
	 * @param arg0
	 */
	public ElevatorThread(Runnable arg0) {
		super(arg0);
		this.location = 0;
		this.destination = -1;
		this.direction = 0;
		this.elevatorId = count++;
	}

	/**
	 * @param arg0
	 */
	public ElevatorThread(String arg0) {
		super(arg0);
		this.location = 0;
		this.destination = -1;
		this.direction = 0;
		this.elevatorId = count++;
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ElevatorThread(ThreadGroup arg0, Runnable arg1) {
		super(arg0, arg1);
		this.location = 0;
		this.destination = -1;
		this.direction = 0;
		this.elevatorId = count++;
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ElevatorThread(ThreadGroup arg0, String arg1) {
		super(arg0, arg1);
		this.location = 0;
		this.destination = -1;
		this.direction = 0;
		this.elevatorId = count++;
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ElevatorThread(Runnable arg0, String arg1) {
		super(arg0, arg1);
		this.location = 0;
		this.destination = -1;
		this.direction = 0;
		this.elevatorId = count++;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public ElevatorThread(ThreadGroup arg0, Runnable arg1, String arg2) {
		super(arg0, arg1, arg2);
		this.location = 0;
		this.destination = -1;
		this.direction = 0;
		this.elevatorId = count++;
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ElevatorThread(ThreadGroup arg0, Runnable arg1, String arg2, long arg3) {
		super(arg0, arg1, arg2, arg3);
		this.location = 0;
		this.destination = -1;
		this.direction = 0;
		this.elevatorId = count++;
	}
	public ElevatorThread(int location,int destination,int direction) {
		super();
		this.location = location;
		this.destination = destination;
		this.direction = direction;
		this.elevatorId = count++;
	}
	public boolean isMoving() {
		if(this.getDirection()==0) {
			return false;
		}else {
			return true;
		}
	}

}
