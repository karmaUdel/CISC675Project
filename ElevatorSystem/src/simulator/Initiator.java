/**
 * 
 */
package simulator;

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
		gui = new Gui(); // running in default mode	
	}
	public Initiator(int numberOfFloors,int numberOfElevators) {
		super();
		gui = new Gui(numberOfFloors, numberOfElevators);		
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

}
