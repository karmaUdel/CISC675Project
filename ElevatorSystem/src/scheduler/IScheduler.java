/**
 * 
 */
package scheduler;

import elevatorunit.ElevatorThread;

/**
 * @author Aditya
 *
 */
public interface IScheduler {
	int scheduler(int request);
	public void setElevators(ElevatorThread[] elevators);
	public void setSettings(ElevatorThread[] elevators, int setting);
}
