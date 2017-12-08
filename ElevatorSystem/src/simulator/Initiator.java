/**
 * 
 */
package simulator;

import java.util.Scanner;

import gui.Gui;

/**
 * @author Aditya
 *
 */
public class Initiator {
	private Gui gui ;
	private static String errorMessage = "Please Enter Valid value for Floors and elevators\n";
	/**
	 * Constructor
	 */
	public Initiator() {
		super();
		// ask for algorithm setting 
		gui = new Gui(10,5,getAlgorithmSetting()); // running in default mode	
	}
	public Initiator(int numberOfFloors,int numberOfElevators) {
		super();
		// ask for algorithm setting 
		gui = new Gui(numberOfFloors, numberOfElevators,getAlgorithmSetting());		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       Initiator init = null;
       if(args!=null) {
    	   try {
    		   int numberOfFloors = Integer.parseInt(args[0]);
    		   int numberOfElevators = Integer.parseInt(args[1]);
    		   init = new Initiator(numberOfFloors, numberOfElevators);
    		   
    	   }catch(Exception e) {
    		   System.err.println(errorMessage);
    		   // Running in default mode
    		   System.err.println("Runnning default mode Number of Floors : 10, number of Elevators: 5");
    		   init =  new Initiator();
    	   }
       }else {
    	   System.err.println(errorMessage);
    	   //System.exit(0);
    	   // Running in default mode
		   System.err.println("Runnning default mode Number of Floors : 10, number of Elevators: 5");
		   init =  new Initiator(); // initiate for default mode
       }
       
       init.initiate(); // run the simulator
	}
	public void initiate() {
		String args[] = null;
		this.gui.initiate(args, this.gui);
	}
	public int getAlgorithmSetting() {
		System.out.println("Enter Scheduling Algorithm \n");
		System.out.println("Which Scheduling you want to use");
		System.out.println("1. Schedule only moving elevators\n2. Schedule only non-moving elevator");
		System.out.println("3. Schedule only closest elevators moving towards requested floor\n4. Default Schedule for an elevator System\n");		
		int x =999;
		Scanner in =null;
		try {
			in = new Scanner(System.in);
			x = in.nextInt();
		}catch (Exception e) {
			// Default schedule
			System.err.println("Invalid input\n Choosing default scheduler");
		}
		finally {
			in.close();
		}
		return x;
	}
}
