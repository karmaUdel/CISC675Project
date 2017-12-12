package test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utility.AnimationTask;
import utility.TaskHandler;
import utility.Work;

public class AnimationTaskTest {
	static AnimationTask task;
	static TaskHandler handler;
	static Random rand;
	static int key;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//task = new AnimationTask("Up_3");
		handler = new TaskHandler();
		task = handler.getInstance("Up_3");
		key = task.getUniqueKey();
	}

	@Before
	public void setUp() throws Exception {
		rand = new Random(Calendar.getInstance().getTimeInMillis());
	}

	@Test
	public void testHasFutureWork() {
		assertFalse(task.hasFutureWork());
	}

	@Test
	public void testAnimationtask() {
		AnimationTask  tsk = new AnimationTask();
		assertTrue("Up_0".equalsIgnoreCase(tsk.getName())); // default name is Up_0
	}
	@Test
	public void testAnimationtaskForTypeDoor() {
		AnimationTask  tsk = new AnimationTask("door");
		assertFalse(tsk.isType()); // True : Move False: Door

		tsk = new AnimationTask("Up_1");
		assertTrue(tsk.isType()); // True : Move False: Door

	}
	@Test
	public void testSetHasFutureWork() {
		task.setHasFutureWork(true);
		assertTrue(task.hasFutureWork());
		task.setHasFutureWork(false);
		assertFalse(task.hasFutureWork());
	}

	@Test
	public void testGetName() {
		String random ="Down_"+rand.nextInt(100);
		assertEquals("Up_3",task.getName());
		assertFalse(random.equalsIgnoreCase(handler.getInstance(random).getName()));
	}

	@Test
	public void testIsTaskRunning() {
		task.setRunning(false);
		assertFalse(task.isTaskRunning());
	}

	@Test
	public void testSetRunning() {
		task.setRunning(false);
		assertFalse(task.isTaskRunning());
		task.setRunning(true);
		assertTrue(task.isTaskRunning());
		
	}

	@Test
	public void testSetName() {
		String generatedName = "Down_"+rand.nextInt(100);
		assertFalse(generatedName.equalsIgnoreCase(task.getName())); // name won't be same
		task.setName(generatedName); // setting expected Name
		assertTrue(generatedName.equalsIgnoreCase(task.getName())); // name will be same now
	}

	@Test
	public void testGetUniqueKey() {
		System.out.println(task.getUniqueKey());
		assertTrue(task.getUniqueKey()==key); // it'll be 0 or more
	}

	@Test
	public void testSetUniqueKey() {
		int differentKey = rand.nextInt(100) + 1; // within 1 to 101 
		assertTrue(task.getUniqueKey()!=differentKey); // it'll be 0 but not randomly generated Value
		task.setUniqueKey(differentKey);
		assertTrue(task.getUniqueKey()==differentKey); // it'll match
	}

	@Test
	public void testSetType() {
		//True : move False : Doors are opening or closing
		assertTrue(task.isType()); // Move
		assertFalse(!task.isType()); //not Door
	}

	@Test
	public void testSetValuesForAnimation() {
		Work work = new Work();
		//list.add(work);
		assertEquals(0, task.getWorkList().size());// no work is assigned
		task.setValuesForAnimation(work); // assigning the work
		assertEquals(1, task.getWorkList().size()); // 1 work is assigned
		work = new Work();
		List<Work> list = task.getWorkList();
		list.add(work); // 2nd object is added
		task.setWorkList(list); // set this updated workList
		assertEquals(2, task.getWorkList().size()); // 1 work is assigned
		
	}

	@Test
	public void testConsumeValuesForAnimation() {
		List<Work> list = task.getWorkList();
		Work work = new Work();
		if(list.size()!=0) {
			//setToZero
			list.removeAll(list);
		}

		task.setValuesForAnimation(work); // assigning the work
		assertEquals(1, task.getWorkList().size()); // 1 work is assigned
		task.consumeValuesForAnimation(work);
		assertEquals(0, task.getWorkList().size()); // work got consumed
		
	}

}
