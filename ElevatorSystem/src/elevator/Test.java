package elevator;

/**
 * @author Zheyuan Liu
 */

// TODO: Consider what else is needed  

public class Test 
{	
	// Reset the status of the elevator to be at the initial state
	public void reset_elevator(Elevator testele)
	{
		testele.isdooropen = false;
		testele.ismoving = false; 
		testele.ismovingup = false; 
		testele.ismovingdown = false; 

		testele.current_floor = 0;
		testele.destination = testele.no_destination;
		testele.request_floor = testele.no_request;
		testele.ext_request = testele.no_request;
		testele.int_request = testele.no_request;
		
		for(int i = 0; i<testele.inbutton_num; i++){
		testele.isinbuttonlit[i] = false;}
	}
	
	// Show the status of the elevator unit
	public void showstats(Elevator testele)
	{
		if(testele.isdooropen == false)
		{
			System.out.println("door: closed");
		}
		else
		{
			System.out.println("door: open");
		}
		
		if(testele.ismoving == false)
		{
			System.out.println("moving stats: not moving");
		}
		else
		{
			System.out.println("moving stats: moving");
		}
		
		if(testele.ismovingup == false)
		{
			System.out.println("movingup?: no");
		}
		else
		{
			System.out.println("movingup?: yes");
		}
		
		if(testele.ismovingdown == false)
		{
			System.out.println("movingdown?: no");
		}
		else
		{
			System.out.println("movingdown?: yes");
		}
		
		System.out.println("current_floor:" + testele.current_floor);
		System.out.println("destination:" + testele.destination);
		System.out.println("maxfloorindex:" + testele.maxfloorindex);
		System.out.println("request_floor:" + testele.request_floor);
		System.out.println("internal_request:" + testele.int_request);
		
		System.out.println("inbutton_num:" + testele.inbutton_num);
		for (int i = 0; i<testele.inbutton_num;i++)
		{
			if (testele.isinbuttonlit[i] == false)
			{
				System.out.println("the buttonlight at floor" + i + ": off. ");
			}
			else
			{
				System.out.println("the buttonlight at floor" + i + ": on. ");
			}
		}
		System.out.println();
	}
	
	public static void main(String[] args) 
	{  
		Test test1 = new Test();
		int maxfloornumber = 5;
		Elevator testele = new Elevator();
		
		// Initial state
		System.out.println("Initial state:");
		testele.initialize_elevator(maxfloornumber);	
		test1.showstats(testele);
		
		// Randomly change some status of the elevator
		System.out.println("Randomtest 1:");
		testele.opendoor();
		testele.moveup();
		testele.pressinbutton(1);
		testele.pressinbutton(4);
		test1.showstats(testele);
		
		// Reset the elevator status. Notice that reset will not change the maximum floor of this elevator!
		System.out.println("Reset:");
		test1.reset_elevator(testele);
		test1.showstats(testele);
		
		// A toy example of how can an elevator move from floor0 to floor3 
		System.out.println("Example: At initial state, receive a request(in or out) for floor3:");
		System.out.println("phase1: receive the request signal, close the door:");
		testele.receive_req(3);
		testele.closedoor();
		test1.showstats(testele);
		System.out.println("phase2: start the engine, the elevator begins moving:");
		testele.move();
		test1.showstats(testele);
		System.out.println("phase3: the elevator reaches the destination, and the door opens:");
		testele.reachdest();
		testele.shutmotor();
		testele.opendoor();
		test1.showstats(testele);
		
		// A toy example of how to use internal request(by pushing inbuttons) to move from floor3 to floor1
		test1.reset_elevator(testele);
		System.out.println("Example: At floor 3, receive a internal request to get to floor 1");
		testele.current_floor = 3;
		testele.closedoor();
		testele.pressinbutton(1);
		testele.receive_req(testele.int_request);	
	}
	
}
