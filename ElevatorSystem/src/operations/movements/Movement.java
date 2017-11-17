/**
 * 
 */
package operations.movements;

import elevatorunit.motors.IMotor;
import elevatorunit.motors.Motor;

/**
 * @author Zheyuan Liu
 *
 */
public class Movement implements IMovement {
	
	IMotor motor;
	int liftmap[];
	int maxfloor;
	int req_floor;
	
	public Movement(int liftId,int req_floor) {

		super();
		motor = new Motor();
		if (liftmap[liftId]< req_floor)
		{
			moveUp(liftId,req_floor);
		}
		else if (liftmap[liftId] == req_floor)
		{
			stop(liftId);
		}
		else 
		{
			moveDown(liftId,req_floor);
		}
		
	}
	/* (non-Javadoc)
	 * @see operations.movements.IMovement#moveUp(int)
	 */
	@Override
	public int moveUp(int liftId, int req_floor) {
		motor.beginMotor(liftId);
		if (liftmap[liftId] == maxfloor){
			return maxfloor;//floor is at top floor
		}
		else
		{
			if (liftmap[liftId]> req_floor)// not sure if I put it here or outside moveup method
			{
				return liftmap[liftId];
			}
			else
			{
				return req_floor;
			}
		}
	}

	/* (non-Javadoc)
	 * @see operations.movements.IMovement#moveDown(int)
	 */
	@Override
	public int moveDown(int liftId, int req_floor) {
		motor.beginMotor(liftId);
		if (liftmap[liftId] == 0){
			return 0;// floor is at floor 0
		}
		else
		{
			
			if (liftmap[liftId]< req_floor)// not sure if I put it here or outside moveup method
			{
				return liftmap[liftId];
			}
			else
			{
				return req_floor;
			}
		}
	}

	/* (non-Javadoc)
	 * @see operations.movements.IMovement#stop(int)
	 */
	@Override
	public int stop(int liftId) {
		
		motor.shutMotor(liftId);
		return liftmap[liftId];
	}

}
