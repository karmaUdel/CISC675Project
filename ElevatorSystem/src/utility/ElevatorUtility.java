/**
 * 
 */
package utility;

/**
 * @author Aditya
 *
 */
public class ElevatorUtility {
	
	public Integer knowDirection(int destination, int location){
		Integer direction = destination - location;
		if(direction > 1) { // going up
			direction = 1;
		}else if(direction <0){ //going down
			direction = -1;
		}else {
			direction = 0; // at same floor
		}
		
		return direction;
	}

}
