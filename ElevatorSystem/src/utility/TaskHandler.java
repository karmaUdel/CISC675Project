package utility;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;

public class TaskHandler  implements Executor{
	//List<AnimationTask> list ;
	private final Executor exec ;
	ObservableList<AnimationTask> list;
	public TaskHandler() {
		// TODO Auto-generated constructor stub
		super();
		exec = null;
		list = FXCollections.observableArrayList();//new ArrayList<AnimationTask>();
	}
	public TaskHandler(Executor exec) {
		// TODO Auto-generated constructor stub
		super();
		this.exec = exec;
		list = FXCollections.observableArrayList();//new ArrayList<AnimationTask>();
	}
	public AnimationTask getInstance(String name) {
		//System.out.println("name : "+name);
		//System.out.println(list);
		AnimationTask task = this.searchTask(name);
		if(task == null) {
			return createNewTask(name);
		}
		return updateExisting(task);
	}
	private AnimationTask createNewTask(String name) {
		AnimationTask newTask = new AnimationTask(name);
		list.add(newTask);
		//System.out.println("New task added : "+list.size());
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
			System.out.println("Task Name :"+task.getName());
			if(name.equalsIgnoreCase(task.getName())) {
				break; // match found
			}
		}
		return task;
	}
	public void terminateTask(AnimationTask task) {
		list.remove(task);
	}
	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		if (command instanceof Worker) {
            final Worker<?> task = (Worker<?>) command ;
            task.stateProperty().addListener(new ChangeListener<State>() {
                @Override
                public void changed(ObservableValue<? extends State> obs,
                        State oldState, State newState) {
                    if (oldState == State.RUNNING) {
                        list.remove(task);
                    }
                }
            });
            
            list.add((AnimationTask)task);
        }
        exec.execute(command);
	}
}
