package gui_learning;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application {
 /**
  * Default Constructor
  */
    public Main() {
		super();
		elevatornum = 5;
		floornum = 10;
		elevatorpaneheight = 200;
		elevatorpanewidth = 1600;
		floorpaneheight = 700;
		floorpanewidth = 200;
		Stagewidth = 1800;
		Stageheight = 900;

	}
    /**
     * @param numberOfFloors
     * @param numberOfElevators
     * Specialized constructor
     */
    public Main(int numberOfFloors, int numberOfElevators) {
		super();
		elevatornum = numberOfElevators;
		floornum = numberOfFloors;
		elevatorpaneheight = 200;
		elevatorpanewidth = 1600;
		floorpaneheight = 700;
		floorpanewidth = 200;
		Stagewidth = 1800;
		Stageheight = 900;    	
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
	
	
	
	EventHandler floorButtonsHandler = new EventHandler() {
           

		@Override
		public void handle(Event event) {
			// TODO Auto-generated method stub
			Button b =(Button)event.getSource();//.getStyleClass().removeAll("button, focus"); 
              //In this way you're sure you have no styles applied to your object button
          	String s = b.getText();
          	String id = b.getId();
          	int idint = Integer.parseInt(id);
          	//System.out.println(event);
          	if("Up".equalsIgnoreCase(s)){
          		//up clicked
          	    System.out.println(idint);
          	    
          	}else{
          		//down clicked
          		System.out.println(idint);
          	}
          	
              event.consume();
		}};

		EventHandler elevatorButtonsHandler = new EventHandler() {
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				Button b =(Button)event.getSource();//.getStyleClass().removeAll("button, focus"); 
	              //In this way you're sure you have no styles applied to your object button
	          	String s = b.getText();
	          	String id = b.getId();
	          	String[] splittedId= id.split("_"); // id is of form #elevatorId_#buttonId
	          	int elevatorId = Integer.parseInt(splittedId[0]);
	          	int buttonId = Integer.parseInt(splittedId[1]);
	          	//System.out.println(event);
	          	if("Up".equalsIgnoreCase(s)){
	          		//up clicked
	          	    System.out.println(elevatorId);
	          	    System.out.println(buttonId);
	          	}else{
	          		//down clicked
	          		System.out.println(elevatorId);
	          		System.out.println(buttonId);
	          	}
	          	
	              event.consume();
			}};

    public static void main(String[] args) {
    	Main app = new Main();
    	app.initiate(args);
    }
    public void initiate(String[] args) {
    	Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage)throws Exception {
    	
        primaryStage.setTitle("Elevator Control System");
        
        BorderPane BottomPane = new BorderPane();
        
        VBox FloorPane = BuildFloorButtonPane();
        HBox ElevatorPane = BuildElevatorButtonPane();
        
        BottomPane.setLeft(FloorPane);
        BottomPane.setBottom(ElevatorPane);
        
        Button[] floorbuttons = new Button[floornum*2];
        for(int i=0; i<floornum*2;i=i+2)
        {
        	floorbuttons[i] = new Button();
        	floorbuttons[i].setMaxHeight((floorpaneheight/floornum)/20);
        	floorbuttons[i+1] = new Button();
        	floorbuttons[i+1].setMaxHeight((floorpaneheight/floornum)/20);
        	floorbuttons[i].setText("Up");
        	floorbuttons[i].setId(i+"");
        	floorbuttons[i].setOnAction(floorButtonsHandler);
        	floorbuttons[i+1].setText("Down");
        	floorbuttons[i+1].setId((i+1)+""); 
        	floorbuttons[i+1].setOnAction(floorButtonsHandler);
        	
        }

        VBox[] floorspane = new VBox[floornum];
        for (int i=0; i<floornum;i++)
        {
        	floorspane[i] = BuildOneFloor(floorpanewidth, floorpaneheight/floornum);
        	floorspane[i].setLayoutX(0);
        	floorspane[i].setLayoutY(floorpaneheight-(floorpaneheight/floornum)*(i+1));
        	floorspane[i].getChildren().add(floorbuttons[i*2]);
        	floorspane[i].getChildren().add(floorbuttons[i*2+1]);
        	floorspane[i].setSpacing(10);
        	floorspane[i].getStyleClass().add("floorspane"); 
        	FloorPane.getChildren().add(floorspane[i]);      	
        }
        
        Scene scene = new Scene(BottomPane, Stagewidth, Stageheight);
        scene.getStylesheets().add("gui_learning/application.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public VBox BuildFloorButtonPane()
    {
    	VBox floorpane = new VBox();
    	floorpane.setLayoutX(0);
    	floorpane.setLayoutY(0);
    	floorpane.setPrefWidth(floorpanewidth);
    	floorpane.setPrefHeight(floorpaneheight);
    	floorpane.setPadding(new Insets(15, 10, 15, 20));
    	floorpane.setSpacing(10);
    	floorpane.getStyleClass().add("floorpane"); 
    	return floorpane;
    }
    
    public HBox BuildElevatorButtonPane()
    {
    	HBox elevatorpane = new HBox();
    	elevatorpane.setLayoutX(floorpanewidth);
    	elevatorpane.setLayoutY(floorpaneheight);
    	elevatorpane.setPrefWidth(elevatorpanewidth);
    	elevatorpane.setPrefHeight(elevatorpaneheight);
    	elevatorpane.setPadding(new Insets(10, 10, 10, 10));
    	elevatorpane.setSpacing(10);
    	elevatorpane.getStyleClass().add("elevatorpane"); 
    	return elevatorpane;
    }
    
    public VBox BuildOneFloor(double floorwidth, double floorheight)
    {
    	VBox OnefloorPane = new VBox();
    	OnefloorPane.setPrefWidth(floorwidth);
    	OnefloorPane.setPrefHeight(floorheight);
    	return OnefloorPane;
    }
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


	
}