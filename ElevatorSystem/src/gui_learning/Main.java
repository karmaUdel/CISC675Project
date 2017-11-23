package gui_learning;

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


public class Main extends Application {
 
    /**
     * @param args the command line arguments
     */
	private final int elevatornum = 5;
	private final int floornum = 5;
	private final double elevatorpaneheight = 200;
	private final double elevatorpanewidth = 1600;
	private final double floorpaneheight = 700;
	private final double floorpanewidth = 200;
	private final double Stagewidth = 1800;
	private final double Stageheight = 900;
	
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
          	    System.out.println(idint);
          	    b.getStyleClass().removeAll("floorbutton, focus");
          	    b.getStyleClass().add("buttonlit");          	    
          	}
          	else if ("Down".equalsIgnoreCase(s))
          	{
          		//down clicked
          		System.out.println(idint);
          		b.getStyleClass().removeAll("floorbutton, focus");
          	    b.getStyleClass().add("buttonlit");          	
          	}
          	
              event.consume();
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
          	//int idint = Integer.parseInt(id);
          	
          	b.getStyleClass().removeAll("elevatorbutton, focus");
      	    b.getStyleClass().add("buttonlit");
          	
          	/*if("Up".equalsIgnoreCase(s))
          	{
          		//up clicked
          	    System.out.println(idint);
          	    b.getStyleClass().removeAll("floorbutton, focus");
          	    b.getStyleClass().add("buttonlit");          	    
          	}
          	else if ("Down".equalsIgnoreCase(s))
          	{
          		//down clicked
          		System.out.println(idint);
          		b.getStyleClass().removeAll("floorbutton, focus");
          	    b.getStyleClass().add("buttonlit");          	
          	}*/
          	
              event.consume();
		}
	};
	
    public static void main(String[] args) {
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
        for(int i=(floornum*2)-1; i>=0;i=i-2)
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
        

        VBox[] floorspane = new VBox[floornum];
        for (int i=floornum-1; i>=0;i--)
        {
        	floorspane[i] = BuildOneFloor(floorpanewidth, floorpaneheight/floornum);
        	floorspane[i].setLayoutX(0);
        	floorspane[i].setLayoutY((floorpaneheight/floornum)*i);
        	floorspane[i].getChildren().add(floorbuttons[i*2+1]);
        	floorspane[i].getChildren().add(floorbuttons[i*2]);
        	
        	floorspane[i].getStyleClass().add("floorspane"); 
        	floorspane[i].setPadding(new Insets(10, 70, 10, 70));
        	FloorPane.getChildren().add(floorspane[i]);      	
        }
        
        
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