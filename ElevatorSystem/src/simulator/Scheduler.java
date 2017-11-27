/**
 * 
 */
package simulator;

import java.util.Scanner;

import gui_learning.Main;

/**
 * @author Aditya Karmarkar
 * Job : Implements multiple schedules
 */

public class Scheduler {
    public int algorithmSetting = 0; // 0 is default setting
    private ElevatorThread elevators [];
    public void setElevators(int numberOfElevators) {
		this.elevators = new ElevatorThread[numberOfElevators];
	}
	public ElevatorThread[] getElevators() {
		return elevators;
	}

	private Simulator simulatorValues;
    private Schedule scheduleSelected;
    private Main gui;
    public Main getGui() {
		return gui;
	}
	public void setGui(Main gui) {
		this.gui = gui;
	}
	public void setElevators(ElevatorThread[] elevators) {
		this.elevators = elevators;
	}
    /**
     * Default Constructor
     */
    public Scheduler() {
    	super();
    	this.simulatorValues = null;//new Simulator(); // this value will be passed from Simulator object
		//this.elevators = new ElevatorThread[simulatorValues.getNumberOfElevators()];
		this.scheduleSelected = new Schedule();
		//this.gui = new Main(simulatorValues.getNumberOfFloors(), simulatorValues.getNumberOfElevators(),this);
    }
	/**
	 * @param values
	 */
	public void schedule(Simulator values) {
		// now we've all the values 
		this.simulatorValues = values;
		// make threads
		this.setElevators(new ElevatorThread[values.getNumberOfElevators()]);
		//this.setElevators(values.getNumberOfElevators());
		this.createThreads();
		// call gui
		// ask for which scheduler algorithm want to use ?
		System.out.println("Which Scheduling you want to use");
		System.out.println("1. Schedule only moving elevators\n2. Schedule only non-moving elevator");
		System.out.println("3. Schedule only closest elevators moving towards requested floor\n4. Default Schedule for an elevator System\n");		
		try {
			Scanner in = new Scanner(System.in);
			this.algorithmSetting = in.nextInt();
		}catch (Exception e) {
			// Default schedule
			System.err.println("Invalid input\n Choosing default scheduler");
		}
		this.setGui(new Main(simulatorValues.getNumberOfFloors(), simulatorValues.getNumberOfElevators(),this));
		this.gui.setScheduler(this); // this will contain elevator threads
		this.scheduleSelected.setElevators(this.getElevators());
		this.gui.initiate(null,this.gui); // now we can launch gui
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
	public int schedulerAlgorithm(int algorithm, int request) {
		return scheduleSelected.scheduler(algorithm,request); // not sure how this is handled but depends on GUI and flow
	}
}
