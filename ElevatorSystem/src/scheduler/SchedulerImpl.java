/**
 * 
 */
package scheduler;

import elevatorunit.ElevatorThread;

/**
 * @author Aditya
 *
 */
public class SchedulerImpl implements IScheduler{
	ElevatorThread[] elevators;
	public int setting = 0;
	public static final int FALSE = 99999;
	/**
	 * Generic Constructor
	 * Idea is , never use this constructor
	 */
	public SchedulerImpl() {
		super();
		elevators = new ElevatorThread[5];
	}
	/**
	 * Default setting on scheduling side
	 * @param elevators
	 */
	public void setElevators(ElevatorThread[] elevators) {
		this.elevators = elevators;
	}
	@Override
	public void setSettings(ElevatorThread[] elevators, int setting) {
		this.elevators = elevators;
		this.setting = setting;
	}
	/**
	 * Specialized Constructor
	 * @param schedulerObject
	 */
	/*public Schedule(Scheduler schedulerObject) {
		super();
		this.elevators = schedulerObject.getElevators(); 
	}*/
	
	/**
	 * @param setting
	 * @param request
	 */
	@Override
	public int scheduler(int request) {
		int elevatorId =0;
		switch(setting) {
		case 1:
			elevatorId = schedule1(request);
			break;
		case 2:
			elevatorId = schedule2(request);
			break;
		case 3:
			elevatorId = schedule3(request);
			break;
		default:
			elevatorId = scheduleDefault(request);
			
		}
		callAnElevator(elevatorId);
		routine();
		return elevatorId;
	}
	/**
	 * call only moving Elevators
	 * @param request
	 */
	private int schedule1(int request) {
		int i = 0;
		int closest = FALSE;
		int val = 0;
		int closestElevator = 0;
		while(i<this.elevators.length) {
			if(this.elevators[i].isMoving()) { // if moving
				val = Math.abs(getClosest(true, this.elevators[i].getDirection(),request, i));
				if(val!=FALSE) {
					if(val<closest) {
						closest = val;
						closestElevator = i;
					}
				}
			}
			i++;
		}
		return closestElevator;
	}
	/**
	 * Call Only non-moving Elevators
	 * @param request
	 */
	private int schedule2(int request) {
		int i = 0;
		int closest = FALSE;
		int val = 0;
		int closestElevator = 0;
		while(i<this.elevators.length) {
			if(!this.elevators[i].isMoving()) { // if not moving
				val = Math.abs(getClosest(false, this.elevators[i].getDirection(),request, i));
				//System.out.println("Inside schedule2 :: val :"+val +" closest: "+closest+"\n");
				if(val!=FALSE) {
					if(val<closest) {
						closest = val;
						closestElevator = i;
					}
				}

			}
			
			i++;
		}
		return closestElevator;
	}
	/**
	 * Call any type of an elevator 
	 * if moving then requested floor should be in path of the movement of elevator
	 * 		for example, elevator i is at floor 7 and moving down
	 * 					if Request came from floor 3 then Elevator i can serve the request
	 * 					if Request came from floor 9 then Elevator i can't serve the request
	 * @param request
	 */
	private int schedule3(int request) {
		int i = 0;
		int closest = FALSE;
		int val = 0;
		int closestElevator = 0;
		while(i<this.elevators.length) {
			val = Math.abs(getClosest(this.elevators[i].isMoving(), this.elevators[i].getDirection(),request, i));
			if(val!=FALSE) {
				if(val<closest) {
					closest = val;
					closestElevator = i;
				}
			}

			i++;
		}
		return closestElevator;
	}
	/**
	 * This setting considers only closest it doesn't matter elevator is moving or not
	 * direction = 1, moving based on thread's current state. Always
	 * @param request
	 */
	private int scheduleDefault(int request) {
		int i = 0;
		int closest = FALSE;
		int val = 0;
		int closestElevator = 0;
		while(i<this.elevators.length) {
			val = Math.abs(getClosest(this.elevators[i].isMoving(), this.elevators[i].getDirection(),request, i));
			if(val!=FALSE) {
				if(val<closest) {
					closest = val;
					closestElevator = i;
				}
			}
			i++;
		}
		return closestElevator;
	}
	/**
	 * Get Closest return is elevator near the requested ?
	 * True means we can call the requested lift, False means we can't
	 */
	private int getClosest(boolean moving, int direction,int destination, int elevatorId) {
		int val = Math.abs(destination - elevators[elevatorId].getLocation());
		if(moving) {
			if(direction==1) {
				return val; //going up or down
			}else { 
				return FALSE; //going away from the requested floor
			}
		}else {
			if(direction==0){ //if lift is not moving
				return val;
			}else { // this condition is never reached
				return FALSE;
			}
		}
	}
	/**
	 * This method marks an elevator for movement
	 * this method is usually followed by the routine() method call
	 * @param elevatorId
	 * ------------------Not needed anymore---------------
	 */
	private void callAnElevator(int elevatorId) {
		
	}
	/**
	 *
	 * This is routine that every elevator follows once called
	 * ------------------Not needed anymore---------------
	 */
	private void routine() {
		
	}
}
