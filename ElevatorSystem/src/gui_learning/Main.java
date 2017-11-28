package gui_learning;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
			System.out.println(this);
			list.add(this);
			System.out.println(this.scheduler);
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
          	
          	if("Up".equalsIgnoreCase(s))
          	{
          		//up clicked
          	    //System.out.println(idint);
          	    b.getStyleClass().removeAll("floorbutton, focus");
          	    b.getStyleClass().add("buttonlit");          	    
          	}
          	else if ("Down".equalsIgnoreCase(s))
          	{
          		//down clicked
          		//System.out.println(idint);
          		b.getStyleClass().removeAll("floorbutton, focus");
          	    b.getStyleClass().add("buttonlit");          	
          	}
        	Main.list.get(0).update(idint);
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
        	//System.out.println(event);
        	if("Up".equalsIgnoreCase(s)){
        		//up clicked
        	    //System.out.println(elevatorId);
        	    //System.out.println(buttonId);
        	    b.getStyleClass().removeAll("elevatorbutton, focus");
          	    b.getStyleClass().add("buttonlit");
        	}else{
        		//down clicked
        		//System.out.println(elevatorId);
        		//System.out.println(buttonId);
        		 b.getStyleClass().removeAll("elevatorbutton, focus");
           	    b.getStyleClass().add("buttonlit");
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
     * 
     * @param elevator
     * @param request
     */
    public void update(Integer elevator, Integer request) {
    	if(this.scheduler!=null) {
	    	ElevatorThread [] elevators = this.scheduler.getElevators();
	    	//System.out.println("update is called "+ elevator);
	    	ArrayList<Integer> requestList = elevators[elevator].getDestinationList();
	    	requestList.add(request);
	    	elevators[elevator].setDestinationList(requestList);
	    	//destination list got updated 
	    }
    	System.out.println(this);

    	System.out.println(this.scheduler);
    }
    /**
     *
     * @param request
     */
    public void update(Integer request) {
    	if(this.scheduler!=null) {
    		int elevatorTobeCalled = this.scheduler.schedulerAlgorithm(4, request);
    	
    	// call the elevator and do something
    		// set to destinationList
    		// move the elevator
    		// 
    	//System.out.println("update is called "+ request);
    	}
    	System.out.println(this);

    	System.out.println(this.scheduler);

    }
    @Override
    public void start(Stage primaryStage)throws Exception {
    	//Main.this = Main.list.get(0);
    	this.elevatornum = Main.list.get(0).elevatornum;
    	this.floornum = Main.list.get(0).floornum;
    	
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
        	//System.out.println(""+ elevatorunitbases[i].getWidth());     	
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
        }
        
        // Create scene and load css file
        Scene scene = new Scene(BottomPane, Stagewidth, Stageheight);
        scene.getStylesheets().add("gui_learning/application.css");
        primaryStage.setScene(scene);
        primaryStage.show();
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
    	System.out.println(e);
    	if("Up".equalsIgnoreCase(s)){
    		//up clicked
    	    System.out.println(idint);
    	    
    	}else{
    		//down clicked
    		System.out.println(idint);
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