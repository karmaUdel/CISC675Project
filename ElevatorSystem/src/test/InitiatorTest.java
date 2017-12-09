package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Platform;
import simulator.Initiator;

public class InitiatorTest {
	static Initiator init ;
	String args[] ;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		init = new Initiator();
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMainValidArguments() {
		args = new String[2];
		args[0] = "10";
		args[0] = "10";
		Initiator.main(args);
		assertEquals(10, init.getGui().getElevatornum());
		assertEquals(10, init.getGui().getFloornum());
		Platform.exit();
		
	}
	@Test
	public void testMainIncompleteArguments1() {
		args = new String[2];
		args[0] = "10";
		args[0] = "";
		Initiator.main(args);
		assertEquals(5, init.getGui().getElevatornum());
		assertEquals(10, init.getGui().getFloornum());
		Platform.exit();
		
	}
	@Test
	public void testMainIncompleteArguments2() {
		args = new String[2];
		args[0] = "ada";
		args[0] = "1";
		Initiator.main(args);
		assertEquals(5, init.getGui().getElevatornum());
		assertEquals(10, init.getGui().getFloornum());
		Platform.exit();
		
	}
	@Test
	public void testMainInvalidArguments() {
		args = new String[2];
		args[0] = "11";
		args[0] = "Abc";
		Initiator.main(args);
		assertEquals(5, init.getGui().getElevatornum());
		assertEquals(10, init.getGui().getFloornum());
		Platform.exit();
		/*try {
			init.getGui().stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	@Test
	public void testMainNoArguments() {
		Initiator.main(null);
		assertEquals(5, init.getGui().getElevatornum());
		assertEquals(10, init.getGui().getFloornum());
		Platform.exit();
		
	}
	
	@Test
	public void testInitiate() {
		fail("Not yet implemented");
	}

}
