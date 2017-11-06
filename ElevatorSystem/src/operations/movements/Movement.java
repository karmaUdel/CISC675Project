/**
 * 
 */
package operations.movements;

import elevatorunit.motors.IMotor;
import elevatorunit.motors.Motor;

/**
 * @author Aditya
 *
 */
public class Movement implements IMovement {
	
	IMotor motor;
	int liftmap[];
	public Movement() {
		// TODO Auto-generated constructor stub
		super();
		motor = new Motor();
		liftmap = new int [100];
	}
	/* (non-Javadoc)
	 * @see operations.movements.IMovement#moveUp(int)
	 */
	@Override
	public int moveUp(int liftId) {
		// TODO Auto-generated method stub
		
		motor.beginMotor(liftId);
		return 0;
	}

	/* (non-Javadoc)
	 * @see operations.movements.IMovement#moveDown(int)
	 */
	@Override
	public int moveDown(int liftId) {
		// TODO Auto-generated method stub
		motor.beginMotor(liftId);
		if (liftmap[liftId] == 0){
			return 0;// floor is at floor 0
		}
		else
			return liftmap[liftId];
	}

	/* (non-Javadoc)
	 * @see operations.movements.IMovement#stop(int)
	 */
	@Override
	public int stop(int liftId) {
		// TODO Auto-generated method stub
		motor.shutMotor(liftId);
		return 0;
	}

}
