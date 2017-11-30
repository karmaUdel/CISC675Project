package gui_learning;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import simulator.ElevatorThread;
import simulator.Scheduler;


public class Main extends Application {
	 /**
	  * Default Constructor
	  */
	static ArrayList<Main> list = new ArrayList<Main>();
	    public Main() {
			super();
			this.scheduler = null;// this value will be passed from scheduler object//new Scheduler();
			this.elevatornum = 5;
			this.floornum = 10;
			this.elevatorpaneheight = 200;
			this.elevatorpanewidth = 1600;
			this.floorpaneheight = 700;
			this.floorpanewidth = 200;
			this.Stagewidth = 1800;
			this.Stageheight = 900;
			list.add(this);
		}
	    /**
	     * @param numberOfFloors
	     * @param numberOfElevators
	     * Specialized constructor
	     */
	    public Main(int numberOfFloors, int numberOfElevators, Scheduler scheduler) {
			super();
			this.scheduler = scheduler;
			this.elevatornum = numberOfElevators;
			this.floornum = numberOfFloors;
			this.elevatorpaneheight = 200;
			this.elevatorpanewidth = 1600;
			this.floorpaneheight = 700;
			this.floorpanewidth = 200;
			this.Stagewidth = 1800;
			this.Stageheight = 900;
			//System.out.println(this);
			list.add(this);
			//System.out.println(this.scheduler);
	    }
	    public Main getInstance() {
	        return this;
	    }
		/**
	     * @param args the command line arguments
	     */
		private  int elevatornum ;
		private  int floornum;
		private  double elevatorpaneheight ;
		private  double elevatorpanewidth ;
		private  double floorpaneheight ;
		private  double floorpanewidth ;
		private  double Stagewidth ;
		private  double Stageheight ;
		private Scheduler scheduler;
		private Stage primaryStage;
	
		public Scheduler getScheduler() {
			return scheduler;
		}
		public void setScheduler(Scheduler scheduler) {
			this.scheduler = scheduler;
		}

	EventHandler floorbuttonhandler = new EventHandler() 
	{
		@Override
		public void handle(Event event) 
		{
			Button b =(Button)event.getSource();
          	String s = b.getText();
          	String id = b.getId();
          	int idint = Integer.parseInt(id);
          	if(idint%2==1){
          		idint++; //if value is odd add 1 and didvide by 2 which will give floor number
          	}
          	idint=idint/2;
          	
          	if("Up".equalsIgnoreCase(s))
          	{
          		//up clicked
          	    ////System.out.println(idint);
          	    //b.getStyleClass().removeAll("floorbutton, focus");
          	    //b.getStyleClass().add("buttonlit");          	    
          	}
          	else if ("Down".equalsIgnoreCase(s))
          	{
          		//down clicked
          		////System.out.println(idint);
          		//b.getStyleClass().removeAll("floorbutton, focus");
          	    //b.getStyleClass().add("buttonlit");          	
          	}
        	Main.list.get(0).update(idint,s);
			event.consume();
              // this will call schedule
              // use scheduler to call scheduler

		}
	};
           
	EventHandler elevatorbuttonhandler = new EventHandler() 
	{
		@Override
		public void handle(Event event) 
		{
			Button b =(Button)event.getSource();
          	String s = b.getText();
          	String id = b.getId();
        	String[] splittedId= id.split("_"); // id is of form #elevatorId_#buttonId
        	int elevatorId = Integer.parseInt(splittedId[0]);
        	int buttonId = Integer.parseInt(splittedId[1]);
        	////System.out.println(event);
        	if("Up".equalsIgnoreCase(s)){
        		//up clicked
        	    ////System.out.println(elevatorId);
        	    ////System.out.println(buttonId);
        	    //b.getStyleClass().removeAll("elevatorbutton, focus");
          	    //b.getStyleClass().add("buttonlit");
        	}else{
        		//down clicked
        		////System.out.println(elevatorId);
        		////System.out.println(buttonId);
        		 //b.getStyleClass().removeAll("elevatorbutton, focus");
           	    //b.getStyleClass().add("buttonlit");
        	}
        	Main.list.get(0).update(elevatorId,buttonId);
		    event.consume();
            // this will call schedule
		}
	};
	
    public static void main(String[] args) {
    	Main app = new Main();
    	app.initiate(args,app);
    }
    public void initiate(String[] args, Main main) {
    	Application.launch(args);
    	
    }
    /**
     * update for requests made inside an elevator
     * @param elevator
     * @param request
     */
    public void update(Integer elevator, Integer request) {
    	if(this.scheduler!=null) {
    		ElevatorThread [] elevators = this.scheduler.getElevators();
	    	//System.out.println("update is called "+ elevator);
	    	//System.out.println("update is called "+ request);
	    	ArrayList<Integer> requestList = elevators[elevator].getDestinationList();
	    	requestList.add(request);
	    	elevators[elevator].setDestinationList(requestList);
	    	
	    	Node eleanchorpane = ((BorderPane) this.primaryStage.getScene().getRoot()).getCenter();
	    	Group ele = (Group) eleanchorpane.lookup("#e"+elevator);
	    	Node elecontrolpane = ((BorderPane) this.primaryStage.getScene().getRoot()).getBottom();
	    	TilePane t = (TilePane) elecontrolpane.lookup("#ec"+elevator);
		    Button b = (Button) t.lookup("#"+elevator+"_"+request);
		    Blend blendEffect = new Blend(BlendMode.DIFFERENCE);
			ColorInput input = new ColorInput();
		    blendEffect.setTopInput(input);
			input.widthProperty().bind(b.widthProperty());
			input.heightProperty().bind(b.heightProperty());
			b.setEffect(blendEffect);
			b.setStyle("-fx-body-color: orange;");
			DoubleProperty brightness = new SimpleDoubleProperty(0);
			input.paintProperty().bind(Bindings.createObjectBinding(() -> Color.BLACK.interpolate(Color.WHITE, brightness.get()), brightness));
	    	 
	    	//Set<Node> set = eleanchorpane.lookupAll(selector)
			//System.out.println("Inside update" +ele);
			//System.out.println("Inside update" +t);
			//System.out.println("Inside update" +b);
			
	    	Task elevatormove = new Task()
	    	    {
	    		@Override
	    		protected Integer call() throws Exception{
	    			//System.out.println("Inside call" +ele);
	    			//System.out.println("Inside call" +b);
	    			//System.out.println("Inside call" +t);
	    			travel(ele,b);
	    			return 1;
   
	    		}
	    	    	/* @Override public Void call() 
	    	    	 {
	    	    		 travel(ele);
	    	    		 return null;
	    	    	 }*/
	    	    	 
	    	    	 public void travel(Group elevatorunit, Button b)
	 	    	    {
	    	    		 //System.out.println("Inside travel");
	    	    		Rectangle elevrec = (Rectangle)(elevatorunit.getChildren().get(0));
	    	    		Rectangle elevrec1 = (Rectangle)(elevatorunit.getChildren().get(1));
	    	    		Rectangle elevrec2= (Rectangle)(elevatorunit.getChildren().get(2));
	    	    		
	    	    		double constant = (double)(floorpaneheight/floornum);
	    	    		double cur_elelocation_y = ((Rectangle)elevrec.yProperty().getBean()).getY();
	 	    	    	if (cur_elelocation_y != (Math.ceil((double)request)))
	 	    	    	{
	 	    	    		double durationtime1 = Math.abs(cur_elelocation_y-Math.ceil((double)request)*constant)*10;
		 	    	    	double durationtime2 = Math.abs(Math.ceil((double)request)*constant-cur_elelocation_y)*10;
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
		 	    	    	    
		 	    	    	    Timeline litbutton = new Timeline();
		 	    	    	    KeyValue kv1lit = new KeyValue(brightness, 0d);
		 	    	    	    KeyValue kv2lit = new KeyValue(brightness, 1d);
		 	    	    	    final KeyFrame kf1lit = new KeyFrame(Duration.ZERO, kv1lit);
		 	    	    	    final KeyFrame kf2lit = new KeyFrame(Duration.millis(4000+durationtime1), kv2lit);
		 	    	    	    litbutton.getKeyFrames().addAll(kf1lit,kf2lit); 
		 	    	    	    //sequence.play();
		 	    	    	    //calculating direction
		 	    	    	    if( 0 < request - Math.abs( cur_elelocation_y/constant)) {
			 	    	    		Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(1);
		 	    	    	    }else {
			 	    	    		Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(-1);		 	    	    	    	
		 	    	    	    }
//		 	    	    		Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(request);
		 	    	    	    //System.out.println("durationtime1 "+durationtime1 + "   cur_elelocation_y "+ cur_elelocation_y);
	 	    	    			ParallelTransition parallel = new ParallelTransition(sequence,litbutton);
	 	    	    			parallel.play();
		 	    	     
	 	    	    			//System.out.println("durationtime1 "+durationtime1 + "   cur_elelocation_y "+ cur_elelocation_y);
		 	    	        }
	 	    	    		
		 	    	    	else
		 	    	    	{
		 	    	    		final KeyValue kv=new KeyValue(elevrec.yProperty(), - request * constant );  
	 	    	    			final KeyValue kv1=new KeyValue(elevrec1.yProperty(),-  request * constant);
	 	    	    			final KeyValue kv2=new KeyValue(elevrec2.yProperty(), - request * (double)(floorpaneheight/floornum));
		 	    	    	    		 	    	    
	 	    	    			final KeyFrame kf=new KeyFrame(Duration.millis(durationtime2), kv);
	 	    	    			final KeyFrame kf1=new KeyFrame(Duration.millis(durationtime2), kv1);
	 	    	    			final KeyFrame kf2=new KeyFrame(Duration.millis(durationtime2), kv2);
	 	    	    			moveing.getKeyFrames().addAll(kf,kf1,kf2);  
	 	    	    			
	 	    	    			Timeline opendoors = new Timeline();
	 	    	    			final KeyValue kv1open = new KeyValue(elevrec1.xProperty(),((Rectangle)elevrec1.xProperty().getBean()).getX() -elevrec1.getWidth());
		 	    	    	    final KeyValue kv2open = new KeyValue(elevrec2.xProperty(),((Rectangle)elevrec2.xProperty().getBean()).getX() +elevrec2.getWidth());
		 	    	    	    final KeyFrame kf1open = new KeyFrame(Duration.millis(2000), kv1open);
		 	    	    	    final KeyFrame kf2open = new KeyFrame(Duration.millis(2000), kv2open);
		 	    	    	    opendoors.getKeyFrames().addAll(kf1open,kf2open);
		 	    	    	    
		 	    	    	    Timeline closedoors = new Timeline();
	 	    	    			final KeyValue kv1close = new KeyValue(elevrec1.xProperty(),((Rectangle)elevrec1.xProperty().getBean()).getX() +elevrec1.getWidth());
		 	    	    	    final KeyValue kv2close = new KeyValue(elevrec2.xProperty(),((Rectangle)elevrec2.xProperty().getBean()).getX() -elevrec2.getWidth());
		 	    	    	    final KeyFrame kf1close = new KeyFrame(Duration.millis(2000), kv1close);
		 	    	    	    final KeyFrame kf2close = new KeyFrame(Duration.millis(2000), kv2close);
		 	    	    	    closedoors.getKeyFrames().addAll(kf1close,kf2close);
		 	    	    	    SequentialTransition sequence = new SequentialTransition(moveing, opendoors, closedoors);
		 	    	    	   
		 	    	    	    Timeline litbutton = new Timeline();
		 	    	    	    KeyValue kv1lit = new KeyValue(brightness, 0d);
		 	    	    	    KeyValue kv2lit = new KeyValue(brightness, 1d);
		 	    	    	    final KeyFrame kf1lit = new KeyFrame(Duration.ZERO, kv1lit);
		 	    	    	    final KeyFrame kf2lit = new KeyFrame(Duration.millis(4000+durationtime2), kv2lit);
		 	    	    	    litbutton.getKeyFrames().addAll(kf1lit,kf2lit);
		 	    	    	
		 	    	    	    
		 	    	    	    //sequence.play();
		 	    	    	    if( 0 < request - Math.abs( cur_elelocation_y/constant)) {
			 	    	    		Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(1);
		 	    	    	    }else {
			 	    	    		Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(-1);		 	    	    	    	
		 	    	    	    }
		 	    	    	       	    	
		 	    	    	    
	 	    	    			//System.out.println("durationtime1 "+durationtime1 + "   cur_elelocation_y "+ cur_elelocation_y);
	 	    	    			ParallelTransition parallel = new ParallelTransition(sequence,litbutton);
	 	    	    			parallel.play();
		 	    	    	    //b.getStyleClass().removeAll("elevatorbutton, focus");
		 	            	    //b.getStyleClass().add("buttonoff");
		 	            	    
	 	    	    			//System.out.println("durationtime2 "+durationtime2 + "   cur_elelocation_y "+ cur_elelocation_y);
		 	    	    	}
	 	    	    	
	 	    	    		//b.getStyleClass().removeAll("elevatorbutton, focus");
	 	            	    //b.getStyleClass().add("buttonlit");		 	    	        
	 	    	    	}
	 	    	    	Main.list.get(0).getScheduler().getElevators()[elevator].setDirection(0);
	 	    	    	Main.list.get(0).getScheduler().getElevators()[elevator].setLocation(request);
	 	    	    	//System.out.println("Location of elevator "+elevator +" is "+Main.list.get(0).getScheduler().getElevators()[elevator].getLocation());
	 	    	    	//System.out.println("Direction of elevator "+elevator +" is "+Main.list.get(0).getScheduler().getElevators()[elevator].getDirection());

	 	    	    }
	    	    	
	    	    	
	    	    };
	    	    Thread x = new Thread(elevatormove);
	    	    x.run();
	    	   
	    	//destination list got updated 
	    	//create task
	    	// rect[elevatorId].bind(newTask);
	    	// new Thread(newTask).start();
	    	//task.travel
	    	//call task
	    }
//    	//System.out.println(this);

//    	//System.out.println(this.scheduler);
    }
    
   
    /* Task newTask = new Task(){
	     * @Override
	     * public void run(){
	     * 		travel();
	     * }
	     * PUBLIC VOID travel(){
		     * while{
		     * 	if floor == in destionation list
		     * 			stop
		     * getCordinates()
		     * fix them 
		     * play()	
		     * turn buttonlight off.
		     * open door
		     * close door
		     * }
		     * 
	     * }
	     * 
	     * public void close(){}
	     * public void open(){}
     test* };
    */
    /**
     * update for requests made from floor buttons
     * @param request
     */
    public void update(Integer request, String direction) {
    	if(this.scheduler!=null) {
    		int elevatorTobeCalled = this.scheduler.schedulerAlgorithm(this.scheduler.algorithmSetting, request);
    		//System.out.println("elevatorId : "+elevatorTobeCalled);
    		Node eleanchorpane = ((BorderPane) this.primaryStage.getScene().getRoot()).getCenter();
	    	Group ele = (Group) eleanchorpane.lookup("#e"+elevatorTobeCalled);
	    	Node floorcontrolpane = ((BorderPane) this.primaryStage.getScene().getRoot()).getLeft();
	    	VBox v = (VBox) floorcontrolpane.lookup("#fc"+request);
	    	Button b ;
	    	
	    	if("UP".equalsIgnoreCase(direction)) {
	    		b = (Button) v.lookup("#"+2*request);
	    	}else {
	    		b = (Button) v.lookup("#"+(2*request-1));
	    	}
	    	//Set<Node> set = eleanchorpane.lookupAll(selector)
			//System.out.println("Inside update" +ele);
			//System.out.println("Inside update" +v);
			//System.out.println("Inside update" +b);
			
			 Blend blendEffect = new Blend(BlendMode.DIFFERENCE);
			 ColorInput input = new ColorInput();
			 blendEffect.setTopInput(input);
			 input.widthProperty().bind(b.widthProperty());
			 input.heightProperty().bind(b.heightProperty());
			 b.setEffect(blendEffect);
			 b.setStyle("-fx-body-color: orange;");
			 DoubleProperty brightness = new SimpleDoubleProperty(0);
			 input.paintProperty().bind(Bindings.createObjectBinding(() -> Color.BLACK.interpolate(Color.WHITE, brightness.get()), brightness));
	    	 
			 Task elevatormove = new Task()
	    	    {
	    		@Override
	    		protected Integer call() throws Exception{
	    			//System.out.println("Inside call" +ele);
	    			//System.out.println("Inside call" +b);
	    			//System.out.println("Inside call" +v);
	    			travel(ele,b);
	    			return 1;
   
	    		}
	    	    	/* @Override public Void call() 
	    	    	 {
	    	    		 travel(ele);
	    	    		 return null;
	    	    	 }*/
	    	    	 
	    	    	 public void travel(Group elevatorunit, Button b)
	 	    	    {
	    	    		 //System.out.println("Inside travel");
	    	    		Rectangle elevrec = (Rectangle)(elevatorunit.getChildren().get(0));
	    	    		Rectangle elevrec1 = (Rectangle)(elevatorunit.getChildren().get(1));
	    	    		Rectangle elevrec2= (Rectangle)(elevatorunit.getChildren().get(2));
	    	    		double constant = (double)(floorpaneheight/floornum);
	    	    		double cur_elelocation_y = ((Rectangle)elevrec.yProperty().getBean()).getY();
	 	    	    	if (cur_elelocation_y != (Math.ceil((double)request)))
	 	    	    	{
	 	    	    		double durationtime1 = Math.abs(cur_elelocation_y-Math.ceil((double)request)*constant)*10;
		 	    	    	double durationtime2 = Math.abs(Math.ceil((double)request)*constant-cur_elelocation_y)*10;
	 	    	    		//Timeline moveing = new Timeline();
	 	    	    		//System.out.println(elevrec.yProperty()+ "---- , ----"+ request * constant);
	 	    	    		
	 	    	    		if(cur_elelocation_y < (Math.ceil((double)request)))
		 	    	    	{	
	 	    	    			Timeline moveing = new Timeline();
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
		 	    	    	    
		 	    	    	    Timeline litbutton = new Timeline();
		 	    	    	    KeyValue kv1lit = new KeyValue(brightness, 0d);
		 	    	    	    KeyValue kv2lit = new KeyValue(brightness, 1d);
		 	    	    	    final KeyFrame kf1lit = new KeyFrame(Duration.ZERO, kv1lit);
		 	    	    	    final KeyFrame kf2lit = new KeyFrame(Duration.millis(4000+durationtime1), kv2lit);
		 	    	    	    litbutton.getKeyFrames().addAll(kf1lit,kf2lit);
		 	    	    	
		 	    	    	    
		 	    	    	    //sequence.play();
		 	    	    	    if( 0 < request - Math.abs( cur_elelocation_y/constant)) {
			 	    	    		Main.list.get(0).getScheduler().getElevators()[elevatorTobeCalled].setDirection(1);
		 	    	    	    }else {
			 	    	    		Main.list.get(0).getScheduler().getElevators()[elevatorTobeCalled].setDirection(-1);		 	    	    	    	
		 	    	    	    }
		 	    	    	       	    	
		 	    	    	    
	 	    	    			//System.out.println("durationtime1 "+durationtime1 + "   cur_elelocation_y "+ cur_elelocation_y);
	 	    	    			ParallelTransition parallel = new ParallelTransition(sequence,litbutton);
	 	    	    			parallel.play();
	 	    	    			
	 	    	    			//System.out.println((int)durationtime1+ "," +(int)durationtime2);
		 	    	        }
		 	    	    	else
		 	    	    	{	
		 	    	    		Timeline moveing = new Timeline();
		 	    	    		final KeyValue kv=new KeyValue(elevrec.yProperty(), - request * constant);  
	 	    	    			final KeyValue kv1=new KeyValue(elevrec1.yProperty(),-  request * constant);
	 	    	    			final KeyValue kv2=new KeyValue(elevrec2.yProperty(), - request * constant);
		 	    	    	    		 	    	    
	 	    	    			final KeyFrame kf=new KeyFrame(Duration.millis(durationtime2), kv);
	 	    	    			final KeyFrame kf1=new KeyFrame(Duration.millis(durationtime2), kv1);
	 	    	    			final KeyFrame kf2=new KeyFrame(Duration.millis(durationtime2), kv2);
	 	    	    			moveing.getKeyFrames().addAll(kf,kf1,kf2);  
	 	    	    			
	 	    	    			Timeline opendoors = new Timeline();
	 	    	    			final KeyValue kv1open = new KeyValue(elevrec1.xProperty(),((Rectangle)elevrec1.xProperty().getBean()).getX() -elevrec1.getWidth());
		 	    	    	    final KeyValue kv2open = new KeyValue(elevrec2.xProperty(),((Rectangle)elevrec2.xProperty().getBean()).getX() +elevrec2.getWidth());
		 	    	    	    final KeyFrame kf1open = new KeyFrame(Duration.millis(2000), kv1open);
		 	    	    	    final KeyFrame kf2open = new KeyFrame(Duration.millis(2000), kv2open);
		 	    	    	    opendoors.getKeyFrames().addAll(kf1open,kf2open);
		 	    	    	    
		 	    	    	    Timeline closedoors = new Timeline();
	 	    	    			final KeyValue kv1close = new KeyValue(elevrec1.xProperty(),((Rectangle)elevrec1.xProperty().getBean()).getX() +elevrec1.getWidth());
		 	    	    	    final KeyValue kv2close = new KeyValue(elevrec2.xProperty(),((Rectangle)elevrec2.xProperty().getBean()).getX() -elevrec2.getWidth());
		 	    	    	    final KeyFrame kf1close = new KeyFrame(Duration.millis(2000), kv1close);
		 	    	    	    final KeyFrame kf2close = new KeyFrame(Duration.millis(2000), kv2close);
		 	    	    	    closedoors.getKeyFrames().addAll(kf1close,kf2close);
		 	    	    	    
		 	    	    	    Timeline litbutton = new Timeline();
		 	    	    	    KeyValue kv1lit = new KeyValue(brightness, 0d);
		 	    	    	    KeyValue kv2lit = new KeyValue(brightness, 1d);
		 	    	    	    final KeyFrame kf1lit = new KeyFrame(Duration.ZERO, kv1lit);
		 	    	    	    final KeyFrame kf2lit = new KeyFrame(Duration.millis(4000+durationtime2), kv2lit);
		 	    	    	    litbutton.getKeyFrames().addAll(kf1lit,kf2lit);
		 	    	    	    
		 	    	    	    if( 0 < request - Math.abs( cur_elelocation_y/constant)) {
			 	    	    		Main.list.get(0).getScheduler().getElevators()[elevatorTobeCalled].setDirection(1);
		 	    	    	    }else {
			 	    	    		Main.list.get(0).getScheduler().getElevators()[elevatorTobeCalled].setDirection(-1);		 	    	    	    	
		 	    	    	    }
		 	    	    	    SequentialTransition sequence = new SequentialTransition(moveing, opendoors, closedoors);
		 	    	    	    //sequence.play();
		 	    	    	    ParallelTransition parallel = new ParallelTransition(sequence,litbutton);
	 	    	    			parallel.play();
		 	    	    	  
		 	    	    	    //b.getStyleClass().removeAll("elevatorbutton, focus");
		 	            	    //b.getStyleClass().add("buttonoff");
		 	            	    
	 	    	    			//System.out.println("durationtime2 "+durationtime2 + "   cur_elelocation_y "+ cur_elelocation_y);
	 	    	    			//System.out.println((int)durationtime1+ "," +(int)durationtime2);
		 	    	    	}
	 	    	    	
	 	    	    		//b.getStyleClass().removeAll("elevatorbutton, focus");
	 	            	    //b.getStyleClass().add("buttonlit");		 	    	        
	 	    	    	}
	 	    	    	Main.list.get(0).getScheduler().getElevators()[elevatorTobeCalled].setDirection(0);
	 	    	    	Main.list.get(0).getScheduler().getElevators()[elevatorTobeCalled].setLocation(request);
	 	    	    	//System.out.println("Location of elevator "+elevatorTobeCalled +" is "+Main.list.get(0).getScheduler().getElevators()[elevatorTobeCalled].getLocation());
	 	    	    	//System.out.println("Direction of elevator "+elevatorTobeCalled +" is "+Main.list.get(0).getScheduler().getElevators()[elevatorTobeCalled].getDirection());
 	    	    	
	 	    	    	
	 	    	    }
	    	    	
	    	    	
	    	    };
	    	    Thread x = new Thread(elevatormove);
	    	    x.run();
    	// call the elevator and do something
    		// set to destinationList
    		// move the elevator
    		// 
    		//System.out.println("update is called "+ request);
    	}
    	//System.out.println(this);

    	//System.out.println(this.scheduler);

    }
    @Override
    public void start(Stage primaryStage)throws Exception {
    	//Main.this = Main.list.get(0);
    	this.elevatornum = Main.list.get(0).elevatornum;
    	this.floornum = Main.list.get(0).floornum;
    	this.primaryStage = primaryStage;
    	Main.list.get(0).primaryStage = this.primaryStage;
        primaryStage.setTitle("Elevator Control System");
        
        // Create the bottompane for all node in this GUI graph
        BorderPane BottomPane = new BorderPane();
        
        // Create a Panel for all floors
        VBox FloorPane = BuildFloorButtonPane();
        
        // Create a Panel for all elevator controls
        HBox ElevatorPane = BuildElevatorButtonPane();
        
        // Create a Panel for all elevator units
        AnchorPane ElevatorUnitPane = BuildElevatorUnitPane();
        
        //Set location for these two large panels
        BottomPane.setLeft(FloorPane);
        BottomPane.setBottom(ElevatorPane);
        BottomPane.setCenter(ElevatorUnitPane);
        
        /********************************** Initializing GUI control parts  **********************************/
        // These are the buttons for floors
        Button[] floorbuttons = new Button[floornum*2-2];
        
        floorbuttons[floornum*2-3] = new Button();
        floorbuttons[floornum*2-3].setMaxHeight((floorpaneheight/floornum)/5);
    	floorbuttons[floornum*2-3].setPrefHeight((floorpaneheight/floornum)/5);
    	floorbuttons[floornum*2-3].setMinHeight((floorpaneheight/floornum)/5);
    	floorbuttons[floornum*2-3].setMaxWidth(Double.MAX_VALUE);
    	floorbuttons[floornum*2-3].setText("Down");
    	floorbuttons[floornum*2-3].setId((floornum*2-3)+""); 
    	floorbuttons[floornum*2-3].setOnAction(floorbuttonhandler);
    	floorbuttons[floornum*2-3].getStyleClass().add("floorbutton"); 
    	      
        for(int i=(floornum*2)-4; i>=1;i=i-2)
        {
        	floorbuttons[i] = new Button();
        	floorbuttons[i].setMaxHeight((floorpaneheight/floornum)/5);
        	floorbuttons[i].setPrefHeight((floorpaneheight/floornum)/5);
        	floorbuttons[i].setMinHeight((floorpaneheight/floornum)/5);
        	floorbuttons[i].setMaxWidth(Double.MAX_VALUE);
        	
        	floorbuttons[i-1] = new Button();
        	floorbuttons[i-1].setMaxHeight((floorpaneheight/floornum)/5);
        	floorbuttons[i-1].setPrefHeight((floorpaneheight/floornum)/5);
        	floorbuttons[i-1].setMinHeight((floorpaneheight/floornum)/5);
        	floorbuttons[i-1].setMaxWidth(Double.MAX_VALUE);
        	
        	floorbuttons[i].setText("Up");
        	floorbuttons[i].setId(i+"");
        	floorbuttons[i].setOnAction(floorbuttonhandler);
        	floorbuttons[i].getStyleClass().add("floorbutton");
 	
        	floorbuttons[i-1].setText("Down");
        	floorbuttons[i-1].setId((i-1)+""); 
        	floorbuttons[i-1].setOnAction(floorbuttonhandler);
        	floorbuttons[i-1].getStyleClass().add("floorbutton");        	
        }
        
        floorbuttons[0] = new Button();
        floorbuttons[0].setMaxHeight((floorpaneheight/floornum)/5);
    	floorbuttons[0].setPrefHeight((floorpaneheight/floornum)/5);
    	floorbuttons[0].setMinHeight((floorpaneheight/floornum)/5);
    	floorbuttons[0].setMaxWidth(Double.MAX_VALUE);
    	floorbuttons[0].setText("Up");
    	floorbuttons[0].setId(0+""); 
    	floorbuttons[0].setOnAction(floorbuttonhandler);
    	floorbuttons[0].getStyleClass().add("floorbutton"); 
    	
        //These are the panels for each floor
        VBox[] floorspane = new VBox[floornum];
        for (int i=floornum-1; i>=0;i--)
        {
        	floorspane[i] = BuildOneFloor(floorpanewidth, floorpaneheight/floornum);
        	floorspane[i].setLayoutX(0);
        	floorspane[i].setLayoutY((floorpaneheight/floornum)*i);
        	
        	floorspane[i].getStyleClass().add("floorspane"); 
        	floorspane[i].setPadding(new Insets(10, 70, 10, 70)); 
        	floorspane[i].setId("fc"+i);
        	FloorPane.getChildren().add(floorspane[i]);
        }
        
        floorspane[floornum-1].getChildren().add(floorbuttons[floornum*2-3]);
        
        for (int i=floornum-2; i>=1;i--)
        {
        	floorspane[i].getChildren().add(floorbuttons[i*2]);
        	floorspane[i].getChildren().add(floorbuttons[i*2-1]);
        }
        
        floorspane[0].getChildren().add(floorbuttons[0]);
        
        // These are elevator buttons
        Button[][] elevatorbutton = new Button[elevatornum][floornum];
        for (int i=0; i<elevatornum; i++)
        {
        	for(int j=0; j<floornum; j++)
        	{
        		elevatorbutton[i][j] = new Button();
        		elevatorbutton[i][j].setShape(new Circle((elevatorpanewidth/elevatornum)/20));
        		elevatorbutton[i][j].getStyleClass().add("elevatorbutton");
        		elevatorbutton[i][j].setMinSize(2*(elevatorpanewidth/elevatornum)/20, 2*(elevatorpanewidth/elevatornum)/20);
        		elevatorbutton[i][j].setMaxSize(2*(elevatorpanewidth/elevatornum)/20, 2*(elevatorpanewidth/elevatornum)/20);
        		elevatorbutton[i][j].setText(j+"");
        		elevatorbutton[i][j].setId(i+"_"+j);
        		elevatorbutton[i][j].setOnAction(elevatorbuttonhandler);
        	}
        }
        
        //These are panels for elevators
        TilePane[] elevatorspane = new TilePane[elevatornum];
        for (int i=0; i<elevatornum;i++)
        {
        	elevatorspane[i] = BuildOneElevatorButtons(elevatorpanewidth/elevatornum, elevatorpaneheight);
        	elevatorspane[i].getStyleClass().add("elevatorspane");
        	elevatorspane[i].setPrefColumns(5);
        	elevatorspane[i].setPadding(new Insets(10, 10, 10, 10));
        	elevatorspane[i].setVgap(10);
        	elevatorspane[i].setHgap(10);
        	elevatorspane[i].setId("ec"+i);
        	ElevatorPane.getChildren().add(elevatorspane[i]); 
        	
        	
        	for(int j=0; j<floornum; j++)
        	{
        		elevatorspane[i].getChildren().add(elevatorbutton[i][j]);
        	}
        }
        
        // These are elevator unit bases
        Rectangle[] elevatorunitbases = new Rectangle[elevatornum];
        for (int i=0; i<elevatornum;i++) 
        {
        	elevatorunitbases[i] = BuildOneElevatorUnitBase(elevatorpanewidth/elevatornum-20, floorpaneheight/floornum);
        	elevatorunitbases[i].setLayoutX(0);
        	elevatorunitbases[i].setLayoutY(0);
        	elevatorunitbases[i].getStyleClass().add("ElevatorUnit");     	
        	//ElevatorUnitPane.getChildren().add(elevatorunitbases[i]);
        	////System.out.println(""+ elevatorunitbases[i].getWidth());     	
        }
        
        // These are the elevator left doors
        Rectangle[] elevatorleftdoors = new Rectangle[elevatornum];
        for (int i=0; i<elevatornum;i++) 
        {
        	elevatorleftdoors[i] = BuildOneLeftDoor((elevatorpanewidth/elevatornum-20)/8, (floorpaneheight/floornum)* 0.8);
        	elevatorleftdoors[i].setLayoutX((elevatorpanewidth/elevatornum-20)*3/8);
        	elevatorleftdoors[i].setLayoutY((floorpaneheight/floornum)* 0.2);
        	elevatorleftdoors[i].getStyleClass().add("ElevatorDoor"); 
        }
        
     // These are the elevator right doors
        Rectangle[] elevatorrightdoors = new Rectangle[elevatornum];
        for (int i=0; i<elevatornum;i++) 
        {
        	elevatorrightdoors[i] = BuildOneRightDoor((elevatorpanewidth/elevatornum-20)/8, (floorpaneheight/floornum)* 0.8);
        	elevatorrightdoors[i].setLayoutX((elevatorpanewidth/elevatornum-20)*4/8);
        	elevatorrightdoors[i].setLayoutY((floorpaneheight/floornum)* 0.2);
        	elevatorrightdoors[i].getStyleClass().add("ElevatorDoor"); 
        }
        
  
     // These are elevator units
        Group[] elevatorunits = new Group[elevatornum];
        for (int i=0; i<elevatornum;i++)
        {
        	elevatorunits[i] = new Group();
        	elevatorunits[i].setLayoutX((elevatorpanewidth/elevatornum)*i + 10);
        	elevatorunits[i].setLayoutY((floorpaneheight/floornum)*(floornum-1));
        	
        	elevatorunits[i].getChildren().addAll(elevatorunitbases[i], elevatorleftdoors[i],elevatorrightdoors[i]);
        	ElevatorUnitPane.getChildren().add(elevatorunits[i]);
        	elevatorunits[i].setId("e"+i);
        }
    
        // Create scene and load css file
        Scene scene = new Scene(BottomPane, Stagewidth, Stageheight);
        scene.getStylesheets().add("gui_learning/application.css");
        primaryStage.setScene(scene);
    	Main.list.get(0).primaryStage = primaryStage;

    	Main.list.get(0).primaryStage.show();
    }
    
    /************* Below are methods to build large panels for floor buttons, elevator buttons, and elevator units *************/
    //The method for building the panel for all floors
    public VBox BuildFloorButtonPane()
    {
    	VBox floorpane = new VBox();
    	floorpane.setLayoutX(0);
    	floorpane.setLayoutY(0);
    	
    	floorpane.setPrefWidth(floorpanewidth);
    	floorpane.setMaxWidth(floorpanewidth);
    	floorpane.setMinWidth(floorpanewidth);
    	
    	floorpane.setPrefHeight(floorpaneheight);
    	floorpane.setMaxHeight(floorpaneheight);
    	floorpane.setMinHeight(floorpaneheight);
    	
    	//floorpane.setPadding(new Insets(15, 10, 15, 20));
    	//floorpane.setSpacing(10);
    	floorpane.getStyleClass().add("floorpane"); 
    	return floorpane;
    }
    
    //The method for building the panel for all elevators
    public HBox BuildElevatorButtonPane()
    {
    	HBox elevatorpane = new HBox();
    	//elevatorpane.setLayoutX(floorpanewidth);
    	//elevatorpane.setLayoutY(floorpaneheight);
    	
    	elevatorpane.setPrefWidth(Stagewidth);
    	elevatorpane.setMaxWidth(Stagewidth);
    	elevatorpane.setMinWidth(Stagewidth);
    
    	elevatorpane.setPrefHeight(elevatorpaneheight);
    	elevatorpane.setMaxHeight(elevatorpaneheight);
    	elevatorpane.setMinHeight(elevatorpaneheight);
    	
    	elevatorpane.setPadding(new Insets(0, 0, 0, floorpanewidth));
    	//elevatorpane.setSpacing(10);
    	elevatorpane.getStyleClass().add("elevatorpane"); 
    	return elevatorpane;
    }
    
    //The method for building the panel for all elevator units
    public AnchorPane BuildElevatorUnitPane()
    {
    	AnchorPane ElevatorUnitPane = new AnchorPane();	
    	ElevatorUnitPane.getStyleClass().add("ElevatorUnitPane"); 
    	return ElevatorUnitPane;
    }
    
    /************* Below are methods to build small panels for one floor, one elevator control, and one elevator unit *************/
    //The method for building the panel for one floor
    public VBox BuildOneFloor(double floorwidth, double floorheight)
    {
    	VBox OnefloorPane = new VBox();
    	
    	OnefloorPane.setPrefWidth(floorwidth);
    	OnefloorPane.setMaxWidth(floorwidth);
    	OnefloorPane.setMinWidth(floorwidth);
    	
    	OnefloorPane.setPrefHeight(floorheight);
    	OnefloorPane.setMaxHeight(floorheight);
    	OnefloorPane.setMinHeight(floorheight);
    	
    	return OnefloorPane;
    }
    
    //The method for building the panel for one elevator
    public TilePane BuildOneElevatorButtons(double elevatorwidth, double elevatorheight)
    {
    	TilePane OneElevatorButtonPane = new TilePane();
    	
    	OneElevatorButtonPane.setPrefWidth(elevatorwidth);
    	OneElevatorButtonPane.setMaxWidth(elevatorwidth);
    	OneElevatorButtonPane.setMinWidth(elevatorwidth);
    	
    	OneElevatorButtonPane.setPrefHeight(elevatorheight);
    	OneElevatorButtonPane.setMaxHeight(elevatorheight);
    	OneElevatorButtonPane.setMinHeight(elevatorheight);
    	
    	return OneElevatorButtonPane;
    }
    
    // The method for building one elevator unit 
    public Group BuildOneElevatorUnit()
    {
    	Group elevatorunit = new Group();
    	return elevatorunit;
    }
    
    //The method for building one elevator unit base
    public Rectangle BuildOneElevatorUnitBase(double elevatorunit_width, double elevatorunit_height)
    {
    	Rectangle elevatorunitbase = new Rectangle();	
    	elevatorunitbase.setWidth(elevatorunit_width);
    	elevatorunitbase.setHeight(elevatorunit_height);  	
    	return elevatorunitbase;
    }
    
    // The method for building the left door for the elevator
    public Rectangle BuildOneLeftDoor(double doorwidth, double doorheight)
    {
    	Rectangle leftdoor = new Rectangle();  	
    	leftdoor.setWidth(doorwidth);
    	leftdoor.setHeight(doorheight);    	
    	return leftdoor; 	
    }
    
 // The method for building the right door for the elevator
    public Rectangle BuildOneRightDoor(double doorwidth, double doorheight)
    {
    	Rectangle rightdoor = new Rectangle();
    	rightdoor.setWidth(doorwidth);
    	rightdoor.setHeight(doorheight);
    	return rightdoor;
    }
}

//For future use: fix the stage size according to the scree.
/*Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
primaryStage.setX(primaryScreenBounds.getMinX());
primaryStage.setY(primaryScreenBounds.getMinY());
primaryStage.setWidth(primaryScreenBounds.getWidth());
primaryStage.setHeight(primaryScreenBounds.getHeight());*/

/*public AnchorPane buildFloorPane(AnchorPane BottomPane)
{
	AnchorPane FloorPane = new AnchorPane();
	AnchorPane.setBottomAnchor(BottomPane,0.0);
	AnchorPane.setTopAnchor(BottomPane, 0.0);
	AnchorPane.setRightAnchor(BottomPane,200.0);
	AnchorPane.setLeftAnchor(BottomPane,0.0);
	FloorPane.getStyleClass().add("bottompane");
	return FloorPane;
}*/

/*public AnchorPane buildElevatorPane(AnchorPane BottomPane)
{
	AnchorPane ElevatorPane = new AnchorPane();
	AnchorPane.setBottomAnchor(BottomPane,0.0);
	AnchorPane.setTopAnchor(BottomPane, 0.0);
	AnchorPane.setRightAnchor(BottomPane,200.0);
	AnchorPane.setLeftAnchor(BottomPane,200.0);
	return ElevatorPane;
}*/


/*    	floorbuttons[1].setOnAction((ActionEvent e) -> {
    	Button b =(Button)e.getSource();//.getStyleClass().removeAll("button, focus"); 
        //In this way you're sure you have no styles applied to your object button
    	String s = b.getText();
    	String id = b.getId();
    	int idint = Integer.parseInt(id);
    	//System.out.println(e);
    	if("Up".equalsIgnoreCase(s)){
    		//up clicked
    	    //System.out.println(idint);
    	    
    	}else{
    		//down clicked
    		//System.out.println(idint);
    	}
    	
    	//floorbuttons[1].getStyleClass().add("buttonlit");
        //then you specify the class you would give to the button
    });
   
   floorbuttons[2].setOnAction((ActionEvent e) -> {
   	floorbuttons[1].getStyleClass().removeAll("button, focus"); 
       //In this way you're sure you have no styles applied to your object button
   	floorbuttons[2].getStyleClass().add("buttonlit");
       //then you specify the class you would give to the button
   });
   */