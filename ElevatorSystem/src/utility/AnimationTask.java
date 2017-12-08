package utility;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimationTask extends Task<Integer> {
	private String name;
	private boolean type; // true : move false: doors
	private int uniqueKey ;
	private static int id = 0;
	//Know all the Values required for animation
	private Group elevatorunit; 
	private Button b; // left for future use
	private int request;
	private double constant;
	private boolean running = false;
	private List<Work> workList ;
	private boolean hasFutureWork = false;
	
	public boolean hasFutureWork() {
		return hasFutureWork;
	}
	public void setHasFutureWork(boolean hasFutureWork) {
		this.hasFutureWork = hasFutureWork;
	}
	public AnimationTask() {
		super();
		this.uniqueKey = id++;
		this.name = "Up_0"; // default name
		this.setType(true);
		this.workList = new ArrayList<Work>();
	}
	public AnimationTask(String name) {
		super();
		this.uniqueKey = id++;
		this.name = name; // default name
		this.setType(!name.contains("door")); // if name contains door then set False else True
		this.workList = new ArrayList<Work>();
	}

	public String getName() {
		return name;
	}

	public boolean isTaskRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(int uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	@Override
	protected Integer call() throws Exception {
		// TODO Auto-generated method stub
		while(!workList.isEmpty()) {
			setRunning(true);
			consumeValuesForAnimation(workList.get(0));
			travel();
			setRunning( false);
			workList.remove(0);
			System.out.println(workList.size());
			//this.notifyAll();
		}
		//this.runAndReset();
		hasFutureWork = false;
		return 1;
	}

	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}

	/**
	 * set values required for Transitions
	 * @param elevatorunit
	 * @param b
	 * @param request
	 * @param constant
	 */
	public void setValuesForAnimation(Work work){
		workList.add(work);
	}
	
	public void consumeValuesForAnimation(Work work){
		this.elevatorunit= work.getElevatorunit();
		this.b = work.getB();
		this.request = work.getRequest();
		this.constant = work.getConstant();
	}
	/**
	 * Play the Animation
	 */
	public void travel()
	{
		//System.out.println("Inside travel");
		Rectangle elevrec = (Rectangle)(elevatorunit.getChildren().get(0));
		Rectangle elevrec1 = (Rectangle)(elevatorunit.getChildren().get(1));
		Rectangle elevrec2= (Rectangle)(elevatorunit.getChildren().get(2));

		double cur_elelocation_y = ((Rectangle)elevrec.yProperty().getBean()).getY();
		double durationtime1 = Math.abs(cur_elelocation_y-Math.ceil((double)request)*constant)*10;
		Timeline moveing = new Timeline();
		//System.out.println(elevrec.yProperty()+ "---- , ----"+ request * constant);

		final KeyValue kv=new KeyValue(elevrec.yProperty(), - request * constant);  
		final KeyValue kv1=new KeyValue(elevrec1.yProperty(),-  request * constant);
		final KeyValue kv2=new KeyValue(elevrec2.yProperty(), - request * constant);
		final KeyFrame kf=new KeyFrame(Duration.millis(durationtime1), kv);
		final KeyFrame kf1=new KeyFrame(Duration.millis(durationtime1), kv1);
		final KeyFrame kf2=new KeyFrame(Duration.millis(durationtime1), kv2);
		moveing.getKeyFrames().addAll(kf,kf1,kf2);

		Timeline opendoors = new Timeline();
		final KeyValue kv1open = new KeyValue(elevrec1.xProperty(),((Rectangle)elevrec.xProperty().getBean()).getX() - elevrec1.getWidth());
		final KeyValue kv2open = new KeyValue(elevrec2.xProperty(),((Rectangle)elevrec.xProperty().getBean()).getX() + elevrec2.getWidth());
		final KeyFrame kf1open = new KeyFrame(Duration.millis(2000), kv1open);
		final KeyFrame kf2open = new KeyFrame(Duration.millis(2000), kv2open);
		opendoors.getKeyFrames().addAll(kf1open,kf2open);

		Timeline closedoors = new Timeline();
		final KeyValue kv1close = new KeyValue(elevrec1.xProperty(),((Rectangle)elevrec.xProperty().getBean()).getX());
		final KeyValue kv2close = new KeyValue(elevrec2.xProperty(),((Rectangle)elevrec.xProperty().getBean()).getX());
		final KeyFrame kf1close = new KeyFrame(Duration.millis(2000), kv1close);
		final KeyFrame kf2close = new KeyFrame(Duration.millis(2000), kv2close);
		closedoors.getKeyFrames().addAll(kf1close,kf2close);

		SequentialTransition sequence = new SequentialTransition(moveing, opendoors, closedoors);

		ParallelTransition parallel = new ParallelTransition(sequence);
		parallel.play();				
	}
}
