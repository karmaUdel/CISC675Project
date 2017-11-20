/**
 * 
 */
package simulator;

/**
 * @author Aditya Karmarkar
 * Job : Implements multiple schedules
 */
public class Scheduler {
    public int algorithmSetting = 0; // 0 is default setting
    private ElevatorThread elevators [];
    private Simulator simulatorValues;
    private Schedule scheduleSelected;
    public Scheduler() {
    	simulatorValues = new Simulator();
		this.elevators = new ElevatorThread[simulatorValues.getNumberOfElevators()];
		scheduleSelected = new Schedule();
    }
	public void schedule(Simulator values) {
		// now we've all the values 
		this.simulatorValues = values;
		// make threads
		createThreads();
		// call gui
		// connect thread to gui
		// schedule stuff
		
	}
	public void createThreads() {
		this.elevators = new ElevatorThread[this.simulatorValues.getNumberOfElevators()]; 
		int numberOfElevators = this.simulatorValues.getNumberOfElevators();
		for(int i = 0;i<numberOfElevators;i++) {
			this.elevators[i]= new ElevatorThread(0,-1,0);//location,destination,direction
		}
	}
	
	public void schedulerAlgorithm(int algorithm) {
		scheduleSelected.scheduler(algorithm);
	}
}
