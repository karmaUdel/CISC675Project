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
    public ElevatorThread[] getElevators() {
		return elevators;
	}

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
		this.gui = new Main(simulatorValues.getNumberOfFloors(), simulatorValues.getNumberOfElevators(),this);
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
		// ask for which scheduler algorithm want to use ?
		System.out.println("Which Scheduling you want to use");
		System.out.println("1. Schedule only moving elevators\n 2. Schedule only non-moving elevator");
		System.out.println("3. Schedule only closest elevators moving towards requested floor\n 4. Default Schedule for an elevator System");		
		try {
			
		}catch (Exception e) {
			// Default schedule
			System.err.println("Invalid input\n Choosing default scheduler");
		}
		this.gui.initiate(null);
		// connect thread to gui
		// schedule stuff this is done on gui side
		
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
	public void schedulerAlgorithm(int algorithm) {
		scheduleSelected.scheduler(algorithm,1); // not sure how this is handled but depends on GUI and flow
	}
}
