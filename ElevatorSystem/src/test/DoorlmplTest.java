package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import elevatorunit.DoorImpl;

public class DoorlmplTest {
	
	static DoorImpl Door = null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Door = new DoorImpl();
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOpen() {
		boolean OpenTest =  Door.open();
		assertTrue(OpenTest);
		assertTrue(!OpenTest == false);
	}

	@Test
	public void testClose() {
		boolean CloseTest =  Door.close();
		assertTrue(CloseTest == false);
		assertTrue(!CloseTest);
	}

}
