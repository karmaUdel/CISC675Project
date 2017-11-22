/**
 * 
 */
package simulator;

import gui_learning.Main;

/**
 * @author Aditya Karmarkar
 * Job : Implements multiple schedules
 */

public class Scheduler {
    public int algorithmSetting = 0; // 0 is default setting
    private ElevatorThread elevators [];
    private Simulator simulatorValues;
    private Schedule scheduleSelected;
    private Main gui;
    /**
     * Default Constructor
     */
    public Scheduler() {
    	super();
    	this.simulatorValues = new Simulator();
		this.elevators = new ElevatorThread[simulatorValues.getNumberOfElevators()];
		this.scheduleSelected = new Schedule();
		this.gui = new Main(simulatorValues.getNumberOfFloors(), simulatorValues.getNumberOfElevators());
    }
	/**
	 * @param values
	 */
	public void schedule(Simulator values) {
		// now we've all the values 
		this.simulatorValues = values;
		// make threads
		this.createThreads();
		// call gui
		this.gui.initiate(null);
		// connect thread to gui
		// schedule stuff
		
	}
	/**
	 * 
	 */
	private void createThreads() {
		this.elevators = new ElevatorThread[this.simulatorValues.getNumberOfElevators()]; 
		int numberOfElevators = this.simulatorValues.getNumberOfElevators();
		for(int i = 0;i<numberOfElevators;i++) {
			this.elevators[i]= new ElevatorThread(0,-1,0);//location,destination,direction
		}
	}
	
	/**
	 * @param algorithm
	 */
	private void schedulerAlgorithm(int algorithm) {
		scheduleSelected.scheduler(algorithm,1); // not sure how this is handled but depends on GUI and flow
	}
}
