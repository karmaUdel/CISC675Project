/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import elevatorunit.ElevatorThread;

import scheduler.SchedulerImpl;

/**
 * @author Aditya
 *
 */
public class SchedulerTest {
	static SchedulerImpl scheduler;
	static Random rand ; 
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		scheduler = new SchedulerImpl();
		ElevatorThread elevators[] = new ElevatorThread[5];
		for(int i =0;i<5;i++) {
			elevators[i]= new ElevatorThread(0, -1, 0, 10);
		}
		scheduler.setSettings(elevators, 999);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		rand = new Random(Calendar.getInstance().getTimeInMillis()); 
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link scheduler.SchedulerImpl#setElevators(elevatorunit.ElevatorThread[])}.
	 */
	@Test
	public void testSetElevators() {
		int x = rand.nextInt(100)+1;
		ElevatorThread elevators[] = new ElevatorThread[x];
		for(int i =0;i<x;i++) {
			elevators[i]= new ElevatorThread(0, -1, 0, 10);
		}
		scheduler.setElevators(elevators);
		assertEquals(x, SchedulerTest.scheduler.getElevators().length); // Length is changed
	}
	@Test
	public void testSetElevatorsNull() {
		scheduler.setElevators(null);
		assertTrue(null == SchedulerTest.scheduler.getElevators()); // Length is null check
	}
	/**
	 * Test method for {@link scheduler.SchedulerImpl#setSettings(elevatorunit.ElevatorThread[], int)}.
	 */
	@Test
	public void testSetSettings0() {
		scheduler.setSetting(0);
		assertEquals(0, SchedulerTest.scheduler.getSetting()); // Length is changed
	}
	@Test
	public void testSetSettingsPositive() {
		scheduler.setSetting(rand.nextInt(100));
		assertTrue(0 <SchedulerTest.scheduler.getSetting()); // Length is changed
	}
	@Test
	public void testSetSettingsNegative() {
		scheduler.setSetting(-1 * rand.nextInt(100));
		assertTrue(0 > SchedulerTest.scheduler.getSetting()); // Length is changed
	}
	/**
	 * We are very sure for the fact that all elevators are location floor = 0 
	 * So no matter what function is called elevator returned will be elevatorId = 0 --> Known fact
	 * We've 4 cases and 1,2,3 and any other number which is not 1,2 and 3
	 * We can;t test getClosest for all possibilities as it doesn't do any null checks
	 * So aim is to get maximum coverage here
	 * 
	 * Test method for {@link scheduler.SchedulerImpl#scheduler(int)}.
	 */
	@Test
	public void testSchedulerWithSetting1Up() {
		scheduler.setSetting(1);
		setup();
		// This setup requires moving elevators
		// so we'll set random elevator as moving
		// criteria to setup elevator moving to make direction of an elevator to 1 or -1
		int movingElevator = rand.nextInt(5);
		scheduler.getElevators()[movingElevator].setDirection(1); // moving up
		int response = scheduler.scheduler(rand.nextInt(10));
		assertEquals(movingElevator, response);
	}
	@Test
	public void testSchedulerWithSetting1Down() {
		scheduler.setSetting(1);
		setup();
		// This setup requires moving elevators
		// so we'll set random elevator as moving
		// criteria to setup elevator moving to make direction of an elevator to 1 or -1
		int movingElevator = rand.nextInt(5);
		scheduler.getElevators()[movingElevator].setDirection(-1); // moving down
		int response = scheduler.scheduler(rand.nextInt(10));
		assertEquals(0, response); // location is floor 0 and direction is going down, so default condition so call 0th elevator
	}
	@Test
	public void testSchedulerWithSetting2() {
		scheduler.setSetting(2);
		setup();
		int response = scheduler.scheduler(rand.nextInt(10));
		assertEquals(0, response);
	}
	@Test
	public void testSchedulerWithSetting3() {
		scheduler.setSetting(3);
		setup();
		int response = scheduler.scheduler(rand.nextInt(10));
		assertEquals(0, response);
	}
	@Test
	public void testSchedulerWithSettingDefault() {
		int setting = 1;
		while (setting == 1 || setting == 2 || setting==3 ) {
			setting = rand.nextInt(100);
		}
		scheduler.setSetting(setting); // any positive value
		setup();
		int response = scheduler.scheduler(rand.nextInt(10));
		assertEquals(0, response);
	}
	@Test
	public void testSchedulerWithSettingDefaultNegative() {
		int setting = -1 * rand.nextInt(100);
		scheduler.setSetting(setting); // any positive value
		setup();
		int response = scheduler.scheduler(rand.nextInt(10));
		assertEquals(0, response);
	}
	@Test
	public void testSchedulerWithSettingDefaulZero() {
		scheduler.setSetting(0); // any positive value
		setup();
		int response = scheduler.scheduler(rand.nextInt(10));
		assertEquals(0, response);
	}
	@Test
	public void testSchedulerWithSettingDefaulZeroExceptional() {
		scheduler.setSetting(0); // any positive value
		setup();
		int movingElevator = rand.nextInt(5);
		int expected = 0;
		System.out.println(movingElevator);
		if(movingElevator == 0) { // if 0th elevator is in exceptional condition then 1st elevator will be called by default
			expected = 1;
		}
		scheduler.getElevators()[movingElevator].setDirection(-1); // moving down
		int response = scheduler.scheduler(rand.nextInt(10));
		assertEquals(expected, response);
	}
	public void setup() {
		ElevatorThread elevators[] = new ElevatorThread[10];
		for(int i =0;i<10;i++) {
			elevators[i]= new ElevatorThread(0, -1, 0, 10);
		}

		scheduler.setElevators(elevators);
	}
}
