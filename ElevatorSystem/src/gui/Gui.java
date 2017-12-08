/**
 * 
 */
package gui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
/**
 * @author Aditya
 *
 */
import operations.IOperation;
import operations.Operations;
import utility.AnimationTask;
import utility.TaskHandler;
import utility.Work;


public class Gui extends Application{
	IOperation operation;
	static ArrayList<Gui> list = new ArrayList<Gui>();
	/**
	 * Important Constants : Although they are constants, they are dynamically set
	 */
	private  int elevatornum ;
	private  int floornum;
	private static final double ELEVATORPANEHEIGHT =200;
	private static final double ELEVATORPANEWIDTH =1600;
	private static final double FLOORPANEHEIGHT =700;
	private static final double FLOORPANEWIDTH =200;
	private static final double STAGEWIDTH = 1800;
	private static final double STAGEHEIGHT = 900;
	private Stage primaryStage;
	private final TaskHandler handler;
	/**
	 * Run {@link Application} in default settings
	 */
	public Gui () {
		super();
		operation = new Operations(10,5,4); //  use specialized Constructor
		this.elevatornum = 5;
		this.floornum = 10;
		list.add(this);
		this.handler = new TaskHandler();
	}
	/**
	 * Specialized Settings
	 * @param numberOfFloors
	 * @param numberOfElevators
	 */
	public Gui (int numberOfFloors,int numberOfElevators) {
		super();
		operation = new Operations(numberOfFloors,numberOfElevators,4); //  use specialized Constructor
		this.elevatornum = numberOfElevators;
		this.floornum = numberOfFloors;
		list.add(this);
		this.handler = new TaskHandler();
	}
	/**
	 * Specialized setting with Scheduling plug-in setting
	 * @param numberOfFloors
	 * @param numberOfElevators
	 * @param schedulingAlgo
	 */
	public Gui (int numberOfFloors,int numberOfElevators, int schedulingAlgo) {
		super();
		operation = new Operations(numberOfFloors,numberOfElevators,schedulingAlgo); //  use specialized Constructor
		this.elevatornum = numberOfElevators;
		this.floornum = numberOfFloors;
		list.add(this);
		this.handler = new TaskHandler();
	}
	
	public Gui getInstance() {
        return this;
    }
	
	@SuppressWarnings("rawtypes")
	EventHandler floorbuttonhandler = new EventHandler() 
	{
		@Override
		public void handle(Event event) 
		{
			Button b =(Button)event.getSource();
          	String operation = b.getText();
          	String id = b.getId();
          	int idint = Integer.parseInt(id);
          	if(idint%2==1){
          		idint++; //if value is odd add 1 and divide by 2 which will give floor number
          	}
          	idint=idint/2;
        	Gui.list.get(0).update(idint,-1,-1,operation.toLowerCase()); // pass floor Value 
			event.consume();
		}
	};
           
	@SuppressWarnings("rawtypes")
	EventHandler elevatorbuttonhandler = new EventHandler() 
	{
		@Override
		public void handle(Event event) 
		{
			Button b =(Button)event.getSource();
          	String s = b.getText(); //TODO Look what value doe this return
          	String id = b.getId();
        	String[] splittedId= id.split("_"); // id is of form #elevatorId_#buttonId
        	int elevatorId = Integer.parseInt(splittedId[0]);
        	int buttonId = Integer.parseInt(splittedId[1]);
        	Gui.list.get(0).update(-1,elevatorId,buttonId,s);
		    event.consume();
		}
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gui app = new Gui();
    	app.initiate(args,app);
	}
	public void initiate(String args[], Gui gui) {
		Application.launch(args);
	}
	/**
	 * TODO : copy stuff from working UI
	 */
	/**
	 * update is method called on event happens on GUI
	 * @param floorId
	 * @param elevatorId
	 * @param buttonId
	 * @param operationId
	 */
	List<AnimationTask> list1 = Collections.synchronizedList(new ArrayList<AnimationTask>());
	boolean hasFutureWork = false;
	public void update(int floorId, int elevatorId, int buttonId, String operationId) {
		ArrayList<Integer> values = operation.update(floorId,elevatorId,buttonId,operationId); //--> this will send me request, elevatorUnit and constant
		//values = playStub(values);
		AnimationTask task = lookupTask("_"+values.get(0));
		if(list1.isEmpty() || task == null) {
			task = this.handler.getInstance("_"+values.get(0)); // check which elevator we want to operate
			list1.add(task);
		}
		playAnimation(task,values); // play the animation
		operation.storeValues(values); // set some values
	}
	public AnimationTask lookupTask(String name) {
		java.util.Iterator<AnimationTask> i = list1.iterator();
		AnimationTask task = null;
		boolean found = false;
		while(i.hasNext()) {
			task = i.next();
			if(name.equalsIgnoreCase(task.getName())) {
				task.setHasFutureWork(true);
				found = true;
				break;
			}
		}
		if(!found) {
			task = new AnimationTask(name);
			list1.add(task);
		}
		return task;
	}
	/**
	 * Random Mode
	 * @param values
	 * @return
	 */
	public ArrayList<Integer> playStub(ArrayList<Integer> values) {
		if(values != null) {
			values = new ArrayList<Integer>();
		}
		Random rand = new Random(Calendar.getInstance().getTimeInMillis());
		values.add(rand.nextInt(5));
		values.add(rand.nextInt(10));
		return values;
	}
	/**
	 * Play Animation
	 * @param task
	 * @param values
	 */
	public void playAnimation(AnimationTask task,ArrayList<Integer> values) {
		//Get All of the required Values
		int elevator = values.get(0);
		int request = values.get(1);
		double constant = (double)(FLOORPANEHEIGHT/this.floornum);
    	
		//Set All of the required Values
		Node eleanchorpane = ((BorderPane) this.primaryStage.getScene().getRoot()).getCenter();
    	Group elevatorunit = (Group) eleanchorpane.lookup("#e"+elevator);
    	Node elecontrolpane = ((BorderPane) this.primaryStage.getScene().getRoot()).getBottom();
    	TilePane t = (TilePane) elecontrolpane.lookup("#ec"+elevator);
	    Button b = (Button) t.lookup("#"+elevator+"_"+request);
	    
	    // pass to Animation Player
	    Work w = new Work(elevatorunit, b, request, constant);
		task.setValuesForAnimation(w);
		
		//Play the Animation
		if(!task.hasFutureWork()) {
			//System.out.println("About to run task "+ task.getName());
			task.run();			
		}
		//if(hasFutureWork) {
			//add new destination List
			
		//}
		//hasFutureWork = false;
		this.handler.terminateTask(task);
		removeTasksfromList();//list1.remove(task);
	}
	synchronized public void removeTasksfromList() {
		java.util.Iterator<AnimationTask> i = list1.iterator();
		AnimationTask task = null;
		ArrayList<AnimationTask> removeTaskList = new ArrayList<AnimationTask>();
		while(i.hasNext()) {
			task = i.next();
			if(!task.hasFutureWork()) {
				//list1.remove(task);	
				removeTaskList.add(task);
			}
		}
		int z =0;
		while(z<removeTaskList.size()) {
			list1.remove(removeTaskList.get(z));
			z++;
		}
		removeTaskList = null;
	}
	/**
	 * All Gui related codes goes below
	 */
    @SuppressWarnings("unchecked")
    @Override
	public void start(Stage primaryStage)throws Exception {
    	//Main.this = Main.list.get(0);
    	this.elevatornum = Gui.list.get(0).elevatornum;
    	this.floornum = Gui.list.get(0).floornum;
    	this.primaryStage = primaryStage;
    	Gui.list.get(0).primaryStage = this.primaryStage;
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
        floorbuttons[floornum*2-3].setMaxHeight((FLOORPANEHEIGHT/floornum)/5);
    	floorbuttons[floornum*2-3].setPrefHeight((FLOORPANEHEIGHT/floornum)/5);
    	floorbuttons[floornum*2-3].setMinHeight((FLOORPANEHEIGHT/floornum)/5);
    	floorbuttons[floornum*2-3].setMaxWidth(Double.MAX_VALUE);
    	floorbuttons[floornum*2-3].setText("Down");
    	floorbuttons[floornum*2-3].setId((floornum*2-3)+""); 
    	floorbuttons[floornum*2-3].setOnAction(floorbuttonhandler);
    	floorbuttons[floornum*2-3].getStyleClass().add("floorbutton"); 
    	      
        for(int i=(floornum*2)-4; i>=1;i=i-2)
        {
        	floorbuttons[i] = new Button();
        	floorbuttons[i].setMaxHeight((FLOORPANEHEIGHT/floornum)/5);
        	floorbuttons[i].setPrefHeight((FLOORPANEHEIGHT/floornum)/5);
        	floorbuttons[i].setMinHeight((FLOORPANEHEIGHT/floornum)/5);
        	floorbuttons[i].setMaxWidth(Double.MAX_VALUE);
        	
        	floorbuttons[i-1] = new Button();
        	floorbuttons[i-1].setMaxHeight((FLOORPANEHEIGHT/floornum)/5);
        	floorbuttons[i-1].setPrefHeight((FLOORPANEHEIGHT/floornum)/5);
        	floorbuttons[i-1].setMinHeight((FLOORPANEHEIGHT/floornum)/5);
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
        floorbuttons[0].setMaxHeight((FLOORPANEHEIGHT/floornum)/5);
    	floorbuttons[0].setPrefHeight((FLOORPANEHEIGHT/floornum)/5);
    	floorbuttons[0].setMinHeight((FLOORPANEHEIGHT/floornum)/5);
    	floorbuttons[0].setMaxWidth(Double.MAX_VALUE);
    	floorbuttons[0].setText("Up");
    	floorbuttons[0].setId(0+""); 
    	floorbuttons[0].setOnAction(floorbuttonhandler);
    	floorbuttons[0].getStyleClass().add("floorbutton"); 
    	
        //These are the panels for each floor
        VBox[] floorspane = new VBox[floornum];
        for (int i=floornum-1; i>=0;i--)
        {
        	floorspane[i] = BuildOneFloor(FLOORPANEWIDTH, FLOORPANEHEIGHT/floornum);
        	floorspane[i].setLayoutX(0);
        	floorspane[i].setLayoutY((FLOORPANEHEIGHT/floornum)*i);
        	
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
        		elevatorbutton[i][j].setShape(new Circle((ELEVATORPANEWIDTH/elevatornum)/20));
        		elevatorbutton[i][j].getStyleClass().add("elevatorbutton");
        		elevatorbutton[i][j].setMinSize(2*(ELEVATORPANEWIDTH/elevatornum)/20, 2*(ELEVATORPANEWIDTH/elevatornum)/20);
        		elevatorbutton[i][j].setMaxSize(2*(ELEVATORPANEWIDTH/elevatornum)/20, 2*(ELEVATORPANEWIDTH/elevatornum)/20);
        		elevatorbutton[i][j].setText(j+"");
        		elevatorbutton[i][j].setId(i+"_"+j);
        		elevatorbutton[i][j].setOnAction(elevatorbuttonhandler);
        	}
        }
        
        //These are panels for elevators
        TilePane[] elevatorspane = new TilePane[elevatornum];
        for (int i=0; i<elevatornum;i++)
        {
        	elevatorspane[i] = BuildOneElevatorButtons(ELEVATORPANEWIDTH/elevatornum, ELEVATORPANEHEIGHT);
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
        	elevatorunitbases[i] = BuildOneElevatorUnitBase(ELEVATORPANEWIDTH/elevatornum-20, FLOORPANEHEIGHT/floornum);
        	elevatorunitbases[i].setLayoutX(0);
        	elevatorunitbases[i].setLayoutY(0);
        	elevatorunitbases[i].getStyleClass().add("ElevatorUnit");     	
        }
        
        // These are the elevator left doors
        Rectangle[] elevatorleftdoors = new Rectangle[elevatornum];
        for (int i=0; i<elevatornum;i++) 
        {
        	elevatorleftdoors[i] = BuildOneLeftDoor((ELEVATORPANEWIDTH/elevatornum-20)/8, (FLOORPANEHEIGHT/floornum)* 0.8);
        	elevatorleftdoors[i].setLayoutX((ELEVATORPANEWIDTH/elevatornum-20)*3/8);
        	elevatorleftdoors[i].setLayoutY((FLOORPANEHEIGHT/floornum)* 0.2);
        	elevatorleftdoors[i].getStyleClass().add("ElevatorDoor"); 
        }
        
     // These are the elevator right doors
        Rectangle[] elevatorrightdoors = new Rectangle[elevatornum];
        for (int i=0; i<elevatornum;i++) 
        {
        	elevatorrightdoors[i] = BuildOneRightDoor((ELEVATORPANEWIDTH/elevatornum-20)/8, (FLOORPANEHEIGHT/floornum)* 0.8);
        	elevatorrightdoors[i].setLayoutX((ELEVATORPANEWIDTH/elevatornum-20)*4/8);
        	elevatorrightdoors[i].setLayoutY((FLOORPANEHEIGHT/floornum)* 0.2);
        	elevatorrightdoors[i].getStyleClass().add("ElevatorDoor"); 
        }
        
  
     // These are elevator units
        Group[] elevatorunits = new Group[elevatornum];
        for (int i=0; i<elevatornum;i++)
        {
        	elevatorunits[i] = new Group();
        	elevatorunits[i].setLayoutX((ELEVATORPANEWIDTH/elevatornum)*i + 10);
        	elevatorunits[i].setLayoutY((FLOORPANEHEIGHT/floornum)*(floornum-1));
        	
        	elevatorunits[i].getChildren().addAll(elevatorunitbases[i], elevatorleftdoors[i],elevatorrightdoors[i]);
        	ElevatorUnitPane.getChildren().add(elevatorunits[i]);
        	elevatorunits[i].setId("e"+i);
        }
    
        // Create scene and load css file
        Scene scene = new Scene(BottomPane, STAGEWIDTH, STAGEHEIGHT);
        scene.getStylesheets().add("gui/application.css");
        primaryStage.setScene(scene);
    	Gui.list.get(0).primaryStage = primaryStage;
    	Gui.list.get(0).primaryStage.show();
    }
    
    /************* Below are methods to build large panels for floor buttons, elevator buttons, and elevator units *************/
    //The method for building the panel for all floors
    public VBox BuildFloorButtonPane()
    {
    	VBox floorpane = new VBox();
    	floorpane.setLayoutX(0);
    	floorpane.setLayoutY(0);
    	
    	floorpane.setPrefWidth(FLOORPANEWIDTH);
    	floorpane.setMaxWidth(FLOORPANEWIDTH);
    	floorpane.setMinWidth(FLOORPANEWIDTH);
    	
    	floorpane.setPrefHeight(FLOORPANEHEIGHT);
    	floorpane.setMaxHeight(FLOORPANEHEIGHT);
    	floorpane.setMinHeight(FLOORPANEHEIGHT);
    	
    	//floorpane.setPadding(new Insets(15, 10, 15, 20));
    	//floorpane.setSpacing(10);
    	floorpane.getStyleClass().add("floorpane"); 
    	return floorpane;
    }
    
    //The method for building the panel for all elevators
    public HBox BuildElevatorButtonPane()
    {
    	HBox elevatorpane = new HBox();
    	//elevatorpane.setLayoutX(FLOORPANEWIDTH);
    	//elevatorpane.setLayoutY(FLOORPANEHEIGHT);
    	
    	elevatorpane.setPrefWidth(STAGEWIDTH);
    	elevatorpane.setMaxWidth(STAGEWIDTH);
    	elevatorpane.setMinWidth(STAGEWIDTH);
    
    	elevatorpane.setPrefHeight(ELEVATORPANEHEIGHT);
    	elevatorpane.setMaxHeight(ELEVATORPANEHEIGHT);
    	elevatorpane.setMinHeight(ELEVATORPANEHEIGHT);
    	
    	elevatorpane.setPadding(new Insets(0, 0, 0, FLOORPANEWIDTH));
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


