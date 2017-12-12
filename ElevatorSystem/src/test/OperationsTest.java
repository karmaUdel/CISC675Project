package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.evosuite.testcarver.wrapper.java.util.Calendar;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import elevatorunit.ElevatorThread;
import operations.OperationImpl;
import scheduler.IScheduler;
import scheduler.SchedulerImpl;

public class OperationsTest {
	static OperationImpl operation;
	static OperationImpl op;
	static Random rand;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		operation = new OperationImpl();
		rand = new Random(Calendar.getInstance().getTimeInMillis());
		op = new OperationImpl(10,3,rand.nextInt(100));
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testUpdate() {
		int floor = rand.nextInt(10);
		int elevator = rand.nextInt(3);
		ArrayList<Integer> list = op.update(floor, -1, -1, "");
		assertEquals(0, list.get(0).intValue()); // elevator 0 will be called
		assertEquals(floor, list.get(1).intValue());
		assertEquals(true, op.getThreads()[list.get(0).intValue()].changeLights("on", floor)); // lights on button will be turned on
		list = op.update(-1,elevator , floor, "");
		assertEquals(elevator, list.get(0).intValue()); // elevator 0 will be called
		assertEquals(floor, list.get(1).intValue());
		assertEquals(true, op.getThreads()[elevator].changeLights("on", floor)); // lights on button will be turned on
	}

	@Test
	public void testStoreMaps() {
		assertFalse(op.storeMaps());
	}

	@Test
	public void testStoreValues() {
		//store Final Values when elevator Arrives at destination
		ArrayList<Integer> values = new ArrayList<Integer>();
		int elevator = rand.nextInt(3);
		int floor =rand.nextInt(10);
		values.add(elevator);
		values.add(floor);
		assertNull(op.storeValues(values)); // on success storeValue returns null
		assertEquals(floor,op.getThreads()[elevator].getLocation()); // after halt elevator will be at floor : _floor
		assertEquals(0, op.getThreads()[elevator].getDirection()); // elevator stopped so direction should be 0
	}

	@Test
	public void testClearSignals() {
		int elevator = rand.nextInt(3);
		assertFalse(op.clearSignals(elevator));
	}

	@Test
	public void testClearIndicators() {
		int elevator = rand.nextInt(3);
		int location = rand.nextInt(10);
		op.getThreads()[elevator].setLocation(location);
		assertTrue(op.getThreads()[elevator].changeLights("on", location)); // lights will be turned on
		
		assertFalse(op.clearIndicators(elevator)); // lights will be turned off
		
		
	}

	@Test
	public void testInitiate() {
		operation.initiate(10, 5, rand.nextInt(100));
		assertEquals(5, operation.getThreads().length);
	}
	@Test 
	public void testSetters() {
		OperationImpl operate = new OperationImpl();
		operate.initiate(10, 5, rand.nextInt(100));
		assertEquals(5, operate.getThreads().length);
		int random =rand.nextInt(10);
		ElevatorThread threads [] = new ElevatorThread[random];
		for(int i=0;i<random;i++) {
			threads [i] = new ElevatorThread();
		}
		operate.setThreads(threads);
		assertEquals(random, operate.getThreads().length);
		
		IScheduler schdule = operate.getScheduler();
		schdule.setElevators(threads);
		
		IScheduler newScheduler = new SchedulerImpl();
		newScheduler.setSettings(threads, random);
		operate.setScheduler(newScheduler); // set new Scheduler
		assertEquals(random, operate.getThreads().length);
		
	}
}
