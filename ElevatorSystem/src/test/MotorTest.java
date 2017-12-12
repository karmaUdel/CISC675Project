package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import elevatorunit.MotorImpl;

public class MotorTest {
	static MotorImpl motor ;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		motor = new MotorImpl();
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testStartMotor() {
		assertTrue(motor.startMotor());		
	}

	@Test
	public void testStopMotor() {
		assertFalse(motor.stopMotor());
	}

}
