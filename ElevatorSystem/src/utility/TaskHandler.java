package utility;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskHandler  {
	static List<AnimationTask> list ;
	
	public TaskHandler() {
		// TODO Auto-generated constructor stub
		super();
		list = new ArrayList<AnimationTask>();
	}
	public AnimationTask getInstance(String name) {
		AnimationTask task = this.searchTask(name);
		if(task == null) {
			return createNewTask(name);
		}
		return updateExisting(task);
	}
	private AnimationTask createNewTask(String name) {
		AnimationTask newTask = new AnimationTask(name);
		list.add(newTask);
		return newTask;		
	}
	private AnimationTask updateExisting(AnimationTask task) {
		//do something
		list.remove(task); // remove old task
		list.add(task); // add updated task
		//AnimationTask task = list.get(index);
		return task;
	}
	
	private AnimationTask searchTask(String name){
		Iterator<AnimationTask> iterator = list.iterator();
		AnimationTask task = null;
		while(iterator.hasNext()) {
			task = iterator.next();
			if(name.equalsIgnoreCase(task.getName())) {
				break; // match found
			}
		}
		return task;
	}
	public void terminateTask(AnimationTask task) {
		list.remove(task);
	}
}
