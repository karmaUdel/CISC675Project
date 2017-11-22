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
     * @param args the command line arguments
     */
	private final int elevatornum = 5;
	private final int floornum = 10;
	private final double elevatorpaneheight = 200;
	private final double elevatorpanewidth = 1600;
	private final double floorpaneheight = 700;
	private final double floorpanewidth = 200;
	private final double Stagewidth = 1800;
	private final double Stageheight = 900;
	
	EventHandler handler = new EventHandler() {
           

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
        
        
        
        Button btest2 = new Button("elevatorpanetest");
        btest2.setPrefSize(80, 20);
        
        Button[] floorbuttons = new Button[floornum*2];
        for(int i=0; i<floornum*2;i=i+2)
        {
        	floorbuttons[i] = new Button();
        	floorbuttons[i].setMaxHeight((floorpaneheight/floornum)/20);
        	floorbuttons[i+1] = new Button();
        	floorbuttons[i+1].setMaxHeight((floorpaneheight/floornum)/20);
        	floorbuttons[i].setText("Up");
        	floorbuttons[i].setId(i+"");
        	floorbuttons[i].setOnAction(handler);
        	floorbuttons[i+1].setText("Down");
        	floorbuttons[i+1].setId((i+1)+""); 
        	floorbuttons[i+1].setOnAction(handler);
        	
        }
        
     
            
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
        
        
        ElevatorPane.getChildren().add(btest2);
        
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