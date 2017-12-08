package utility;

import javafx.scene.Group;
import javafx.scene.control.Button;

public class Work {

	public Group getElevatorunit() {
		return elevatorunit;
	}
	public void setElevatorunit(Group elevatorunit) {
		this.elevatorunit = elevatorunit;
	}
	public Button getB() {
		return b;
	}
	public void setB(Button b) {
		this.b = b;
	}
	public int getRequest() {
		return request;
	}
	public void setRequest(int request) {
		this.request = request;
	}
	public double getConstant() {
		return constant;
	}
	public void setConstant(double constant) {
		this.constant = constant;
	}
	private Group elevatorunit; 
	private Button b; 
	private int request;
	private double constant;
	public Work() {
		super();
	}
	public Work(Group elevator,Button b,int request,double constant) {
		super();
		this.setElevatorunit(elevator);
		this.setB(b);
		this.setConstant(constant);
		this.setRequest(request);
	}

}
