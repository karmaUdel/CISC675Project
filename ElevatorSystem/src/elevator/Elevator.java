package elevator;

/**
 * @author Zheyuan Liu
 
// TODO: Consider Exception for all methods. 
// TODO: Decide what is necessary for other classes (delay? request signal from both internal and external?(Where to put the priority))
**/

public class Elevator 
{	
	// The door parameter
	public boolean isdooropen; //True means the door is open, false means the door is closed
	
	// The motor parameters
	public boolean ismoving; //True == moving; False == not moving
	public boolean ismovingup; //True == movingup; False == not movingup
	public boolean ismovingdown; //True == movingdown; False == not movingdown
	
	// The floor indicator parameter
	public int current_floor; //This is the current floor of the elevator
	public int destination; //This is the destination of the elevator
	public int maxfloorindex; // This is the top floor Index! E.g. if we have 5 floors and the first floor is floor0, then maxfloorindex = 4.
	public final int no_destination = -1; //This is used to represent there is no destination
	
	// The Inbutton parameter
	public int inbutton_num; //The number of indoor buttons of an elevator
	public boolean[] isinbuttonlit; //The status of all indoor button lights
	
	// The request receiver
	public int request_floor; //This is the final request signal that the elevator is going to receive!
	public int int_request; //This is the internal request
	public int ext_request; //This is the external request
	public final int no_request = -1;//This static int is used to represent there is no signal
	
//*************************************The door method******************************//
	public void opendoor() //Open the door
	{
		this.isdooropen = true;
	}
	
	public void closedoor() //Close the door
	{
		this.isdooropen = false;
	}
	
	public boolean checkdoorstats() //Check the status of the door.
	{
		return this.isdooropen;
	}
	
//*************************************The motor method******************************//
	//This method deals with the movement of the elevator: move; moveup; movedown
	public void beginmotor() //Begin the motor, the elevator begins to move.
	{
		this.ismoving = true;
	}
	
	public void shutmotor() //shut down the motor, the elevator stops moving
	{
		this.ismoving = false;
		this.ismovingup = false;
		this.ismovingdown = false;	
	}
	
	public boolean checkmotorstats() //Check the moving status of the elevator
	{
		return this.ismoving;
	}
	//IMPORTANT NOTE!: Make sure req_floor is in the range of (0,maxfloor) in req signals method!!
	public int move() //Move the elevator, if req_floor>cur_floor, moves up; if req_floor<cur_floor, moves down. Set the destination to be the req_floor
	{	
		if(this.request_floor> current_floor)
		{
			moveup();
			this.destination = this.request_floor;		
		}
		else if (this.request_floor < current_floor)
		{
			movedown();
			this.destination = this.request_floor;
		}
		else 
		{
			this.destination = this.request_floor;
		}
		return this.destination;
	}
	
	public void moveup() //move up
	{
		this.beginmotor();
		this.ismovingup = true;
	}
	
	public void movedown() //move down
	{
		this.beginmotor();
		this.ismovingdown= true;
	}
	
//*************************************The Inbutton method******************************//
	public int get_inbutton_num() //Get the number of indoor buttons
	{		
		return this.inbutton_num;
	}
	
	public void pressinbutton(int pressfloornum) //Press the button: 1.this button is lit; 2.an internal request is generated
	{
		this.isinbuttonlit[pressfloornum] = true;
		this.int_request = pressfloornum;
	}
	
	public void inbuttonoff(int pressfloornum) //This will turn off ONE button light. 
	{
		this.isinbuttonlit[pressfloornum] = false;
	}
	
//*************************************The floor indicator method***********************//	
	public int getmaxfloor_num() //Get the number of total floor numbers
	{
		return this.maxfloorindex+1;
	}
	
	public int getmaxfloor_index() //Get the top floor index
	{
		return this.maxfloorindex;
	}
	
	public int getreqfloor() //Get the final request floor
	{
		return this.request_floor;
	}
	
	public int getdestination() //Get the destination
	{
		return this.destination;
	}
	
	public void reachdest() //Arrive at the destination: assign the destination to the current floor
	{
		this.current_floor = this.destination;
	}
	
//********************************* The request receiver *******************************//
	public void receive_req(int request) //Receive the final request!
	{	
		this.request_floor = request;
	}
	
//**********************************Initialize the elevator*****************************//
	public void initialize_elevator(int maxfloornum) //Initialize the elevator status 
	{
		this.isdooropen = false;
		this.ismoving = false; 
		this.ismovingup = false; 
		this.ismovingdown = false; 

		this.current_floor = 0;
		this.destination = this.no_destination;
		this.maxfloorindex = maxfloornum -1;   //exception: if maxfloornum < 1
		this.request_floor = this.no_request;
		this.int_request = this.no_request;
		this.ext_request = this.no_request;

		this.inbutton_num = maxfloornum;
		this.isinbuttonlit = new boolean[maxfloornum];
	}
	

}
