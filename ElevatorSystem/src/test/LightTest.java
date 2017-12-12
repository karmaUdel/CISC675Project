package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import elevatorunit.ElevatorThread;
import elevatorunit.LightImpl;

public class LightTest {
	static LightImpl light = null;
	static ElevatorThread ele = null;
	static Random rand;
	static int numOfFloors;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		light = new LightImpl();
		ele = new ElevatorThread(0, -1, 0, 10);
	}

	@Before
	public void setUp() throws Exception {
		rand = new Random(Calendar.getInstance().getTimeInMillis());
		numOfFloors = rand.nextInt(10);
	}

	@Test
	public void testTurnOn() {
		int x = rand.nextInt(numOfFloors);
		assertTrue(ele.changeLights("on", x)); // light x will be set 1 {turned on}
		light = new LightImpl(numOfFloors); // every light be off i.e 0
		assertNotEquals(light.getButtonLights()[x],ele.changeLights("on", x)); // 0 != 1
	}

	@Test
	public void testTurnOff() {
		int x = rand.nextInt(numOfFloors);
		assertFalse(ele.changeLights("off", x)); // light x will be set 1 {turned on}
		light = new LightImpl(numOfFloors); // every light be off i.e 0
		//now let's set all lights on
		int buttonLights[] = new int [numOfFloors];
		for(int i=0; i<numOfFloors;i++) {
			buttonLights[i] = 1; // turning lights on
		}
		light.setButtonLights(buttonLights); // setting all lights on
		assertNotEquals(light.getButtonLights()[x],ele.changeLights("off", x)); // 1 != 0
	}

}
