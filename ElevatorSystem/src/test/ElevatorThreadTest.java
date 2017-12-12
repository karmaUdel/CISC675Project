package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import elevatorunit.ElevatorThread;

public class ElevatorThreadTest {
    static ElevatorThread ele;
    static ArrayList<Integer> destList;
	static Random rand;
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rand = new Random();
    	ele = new ElevatorThread(0, -1, 0, 10);
		destList = new ArrayList<Integer>();
		}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetDestinationList() {
		destList.add(rand.nextInt(10));
		destList.add(rand.nextInt(10));
		ele.setDestinationList(destList);
		ArrayList<Integer> temp;
		temp = ele.getDestinationList(); // this list will be sorted
		Collections.sort(destList);
		for(int i = 0; i<destList.size(); i++) {
			assertEquals(destList.get(i), temp.get(i));
		}
	}


	@Test
	public void testGetElevatorId() {
		assertEquals(0, ele.getElevatorId()); // 0 will be assigned to object
	}

	@Test
	public void testGetLocation() {
		ele.setLocation(0);
		assertEquals(0, ele.getLocation()); // 0 will be assigned to object
	}

	@Test
	public void testSetLocation() {
		int x = rand.nextInt(10);
		ele.setLocation(x);
		assertEquals(x, ele.getLocation());
	}

	@Test
	public void testGetDestination() {
		ele.setDestination(-1);
		assertEquals(-1, ele.getDestination());
	}

	@Test
	public void testSetDestination() {
		int x = rand.nextInt(10);
		ele.setDestination(x);
		assertEquals(x, ele.getDestination());
	}

	@Test
	public void testGetDirection() {
		ele.setDirection(0);
		assertEquals(0, ele.getDirection());
	}

	@Test
	public void testSetDirection() {
		ele.setDirection(-1);
		assertEquals(-1, ele.getDirection());
		ele.setDirection(1);
		assertEquals(1, ele.getDirection());
	}

	@Test
	public void testIsMoving() {
		ele.setDirection(0); //it's not moving
		assertFalse(ele.isMoving());
		ele.setDirection(1); //it's moving Up
		assertTrue(ele.isMoving());
		ele.setDirection(-1); //it's moving Down
		assertTrue(ele.isMoving());
	}

	@Test
	public void testSort() {
		destList.add(rand.nextInt(10));
		destList.add(rand.nextInt(10));
		ele.setDestinationList(destList);
		ele.setDirection(1); // moving up
		ArrayList<Integer> temp;
		temp = ele.getDestinationList(); // this list will be sorted
		destList = ele.sort(destList);
		for(int i = 0; i<destList.size(); i++) {
			assertEquals(destList.get(i), temp.get(i));
		}
		ele.setDirection(-1); // moving down
		temp = ele.getDestinationList(); // this list will be sorted
		destList = ele.sort(destList);
		for(int i = 0; i<destList.size(); i++) {
			assertEquals(destList.get(i), temp.get(i));
		}
		
	}

	@Test
	public void testMove() {
		assertTrue(ele.move("start")); //start Motor
		assertFalse(ele.move("stop")); //Stop Motor
	}

	@Test
	public void testChangeLights() {
		int x = rand.nextInt(10);
		assertTrue(ele.changeLights("on",x)); //turn on the light
		assertFalse(ele.changeLights("off",x)); //turn off the light
		
	}

	@Test
	public void testCheckDoors() {
		assertTrue(!ele.checkDoors()); // door open
	}

	@Test
	public void testOperateDoors() {
		assertFalse(ele.operateDoors()); // if open the closing door
		assertTrue(ele.operateDoors()); //closing door
	}

}
