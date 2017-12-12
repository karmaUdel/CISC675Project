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
		assertFalse(OpenTest);
	}

	@Test
	public void testClose() {
		boolean CloseTest =  Door.close();
		assertTrue(CloseTest);
	}

}
