package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.Group;
import javafx.scene.control.Button;
import utility.ElevatorUtility;
import utility.Work;

/**
 * @author Zheyuan 
 *
 */

public class UtilityTest {

	@Before
	public void setUp() throws Exception {
	}
	
	//Test for Class utility.ElevatorUtility
	@Test
	public void testKnowDirection() {
		ElevatorUtility ElevatorUtilityTest = new ElevatorUtility();
		Integer Testdirection1 = ElevatorUtilityTest.knowDirection(5,3);
		assertTrue(Testdirection1 == 1);
		
		Integer Testdirection2 = ElevatorUtilityTest.knowDirection(3,5);
		assertTrue(Testdirection2 == -1);
		
		Integer Testdirection3 = ElevatorUtilityTest.knowDirection(3,3);
		assertTrue(Testdirection3 == 0);
	}
	
	//Test for Class utility.TaskHandler

	//Test for Class utility.Work
	Group elevator = new Group();
	private Button b = null;
	int request = 0;
	double constant = 0;
	//Work WorkwithParameterTest = new Work(elevator, b,request, constant);
	Work WorkwithoutParameterTest = new Work();	
	@Test 
	public void testElevatorunitmethod() {
		WorkwithoutParameterTest.setElevatorunit(elevator);		
		assertEquals(WorkwithoutParameterTest.getElevatorunit(),elevator);		
	}
	@Test
	public void testElevatorunitParametricConstructor() {
		WorkwithoutParameterTest = new Work(elevator, b, request, constant);
		assertTrue(WorkwithoutParameterTest.getElevatorunit()!=null); // some group
		assertFalse(WorkwithoutParameterTest.getElevatorunit()==null);
		assertEquals(0,WorkwithoutParameterTest.getRequest()); // request is 0
		assertTrue(WorkwithoutParameterTest.getConstant() == 0); // constant is 0
		assertFalse(WorkwithoutParameterTest.getConstant() != 0); // constant is 0		
		assertEquals(null,WorkwithoutParameterTest.getB()); // button is null, FX doesn't allow Button without "scene"
	}
	
	@Test 
	public void testRequestmethod() {
		WorkwithoutParameterTest.setRequest(request);
		assertEquals(WorkwithoutParameterTest.getRequest(),request);			
	}
	
	@Test 
	public void testConstantmethod() {
		WorkwithoutParameterTest.setConstant(constant);
		assertTrue(WorkwithoutParameterTest.getConstant() == constant);			
	}
	
	@Test 
	public void testButtonmethod() {
		WorkwithoutParameterTest.setB(b);
		assertTrue(WorkwithoutParameterTest.getB() == null);		
	}
}

