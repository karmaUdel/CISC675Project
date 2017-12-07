package utility;

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
	private Button b; 
	private int request;
	private double constant;
	public AnimationTask() {
		super();
		this.uniqueKey = id++;
		this.name = "Up_0"; // default name
		this.setType(true);
	}
	public AnimationTask(String name) {
		super();
		this.uniqueKey = id++;
		this.name = name; // default name
		this.setType(!name.contains("door")); // if name contains door then set False else True
	}

	public String getName() {
		return name;
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
		travel();
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
	public void setValuesForAnimation(Group elevatorunit, Button b, int request,double constant){
		this.elevatorunit= elevatorunit;
		this.b = b;
		this.request = request;
		this.constant = constant;
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
		if (cur_elelocation_y != (Math.ceil((double)request)))
		{
			double durationtime1 = Math.abs(cur_elelocation_y-Math.ceil((double)request)*constant)*10;
			Timeline moveing = new Timeline();
			//System.out.println(elevrec.yProperty()+ "---- , ----"+ request * constant);

			if(cur_elelocation_y < (Math.ceil((double)request)))
			{
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

				/*    	    Timeline litbutton = new Timeline();
 	    	    KeyValue kv1lit = new KeyValue(brightness, 0d);
 	    	    KeyValue kv2lit = new KeyValue(brightness, 1d);
 	    	    final KeyFrame kf1lit = new KeyFrame(Duration.ZERO, kv1lit);
 	    	    final KeyFrame kf2lit = new KeyFrame(Duration.millis(4000+durationtime1), kv2lit);
 	    	    litbutton.getKeyFrames().addAll(kf1lit,kf2lit); 
				 */  	    //sequence.play();
				//calculating direction
				/* 	    	    if( 0 < request - Math.abs( cur_elelocation_y/constant)) {
	    	    		Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(1);
 	    	    }else {
	    	    		Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(-1);		 	    	    	    	
 	    	    }
				 */
				// 	    		Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(request);
				//System.out.println("durationtime1 "+durationtime1 + "   cur_elelocation_y "+ cur_elelocation_y);
				ParallelTransition parallel = new ParallelTransition(sequence);
				parallel.play();

				//System.out.println("durationtime1 "+durationtime1 + "   cur_elelocation_y "+ cur_elelocation_y);
			}

			//	    	Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(0);
			//	    	Main.list.get(0).getScheduler().getElevators()[elevator].setLocation(request);
			//System.out.println("Location of elevator "+elevator +" is "+Main.list.get(0).getScheduler().getElevators()[elevator].getLocation());
			//System.out.println("Direction of elevator "+elevator +" is "+Main.list.get(0).getScheduler().getElevators()[elevator].getDirection());
		}
	}
}
