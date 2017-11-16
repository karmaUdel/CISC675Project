package elevator;

/**
 * @author Zheyuan Liu
 */

// TODO: Consider Exception for all methods. 
// TODO: Decide what is necessary for other classes (delay? request signal from both internal and external?(Where to put the priority))

public class Elevator 
{	
	//The door parameter
	public boolean isdooropen; //True means the door is open, false means the door is closed
	
	//The motor parameters
	public boolean ismoving; //True == moving; False == not moving
	public boolean ismovingup; //True == movingup; False == not movingup
	public boolean ismovingdown; //True == movingdown; False == not movingdown
	
	//The floor indicator parameter
	public int current_floor;
	public int destination;
	public int maxfloorindex;
	public int request_floor;
	
	//The Inbutton parameter
	public int inbutton_num;
	public boolean[] isinbuttonlit;
	public int inbuttonrequest;
	
//*************************************The door method******************************//
	public void opendoor()
	{
		this.isdooropen = true;
	}
	
	public void closedoor()
	{
		this.isdooropen = false;
	}
	//This method is used to check the status of the door.
	//Return value: True means the door is open, false means the door is closed
	public boolean checkdoorstats()
	{
		return this.isdooropen;
	}
	
//*************************************The motor method******************************//
	//This method deals with the movement of the elevator: move; moveup; movedown
	public void beginmotor()
	{
		this.ismoving = true;
	}
	
	public void shutmotor()
	{
		this.ismoving = false;
		this.ismovingup = false;
		this.ismovingdown = false;	
	}
	
	public boolean checkmotorstats()
	{
		return this.ismoving;
	}
	//IMPORTANT NOTE!: Make sure req_floor is in the range of (0,maxfloor) in req signals method!!
	public int move()
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
	
	public void moveup()
	{
		this.beginmotor();
		this.ismovingup = true;
	}
	
	public void movedown()
	{
		this.beginmotor();
		this.ismovingdown= true;
	}
	
//*************************************The Inbutton method******************************//
	public int get_inbutton_num()
	{		
		return this.inbutton_num;
	}
	
	public void pressinbutton(int pressfloornum)
	{
		this.isinbuttonlit[pressfloornum] = true;
		this.inbuttonrequest = pressfloornum;
	}
	
	public void inbuttonoff(int pressfloornum)
	{
		this.isinbuttonlit[pressfloornum] = false;
	}
	
//*************************************The floor indicator method***********************//	
	public int getmaxfloor_num()
	{
		return this.maxfloorindex+1;
	}
	
	public int getmaxfloor_index()
	{
		return this.maxfloorindex;
	}
	
	public int getreqfloor()
	{
		return this.request_floor;
	}
	
	public int getdestination()
	{
		return this.destination;
	}
	
	public void reachdest()
	{
		this.current_floor = this.destination;
	}
	
//********************************* The request receiver *******************************//
	public void receive_req(int request)
	{
		this.request_floor = request;
	}
	
//**********************************Initialize the elevator*****************************//
	public void initialize_elevator(int maxfloornum)
	{
		this.isdooropen = false;
		this.ismoving = false; 
		this.ismovingup = false; 
		this.ismovingdown = false; 

		this.current_floor = 0;
		this.destination = 0;
		this.maxfloorindex = maxfloornum -1;   //exception: if maxfloornum < 1
		this.request_floor = 0;

		this.inbutton_num = maxfloornum;
		this.isinbuttonlit = new boolean[maxfloornum];
	}
	

}
