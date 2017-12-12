package test;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utility.AnimationTask;
import utility.TaskHandler;

public class TaskHandlerTest {
	static AnimationTask task ;
	static TaskHandler handler;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//task = new AnimationTask("Up_3");
		handler = new TaskHandler();
		task = handler.getInstance("Up_3");
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetInstance() {
		assertEquals(task.getName() , handler.getInstance("Up_3").getName());
	}

	@Test
	public void testTerminateTask() throws InterruptedException, ExecutionException {
		handler.terminateTask(task); // remove Task from the list
		assertTrue(task.getUniqueKey() != handler.getInstance("Up_3").getUniqueKey()) ; // this won't be same as hashcode will be different
	}

}
