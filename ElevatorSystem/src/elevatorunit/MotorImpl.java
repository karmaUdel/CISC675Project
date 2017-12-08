/**
 * 
 */
package elevatorunit;

/**
 * @author Aditya
 *
 */
public class MotorImpl implements IMotor {

	/* (non-Javadoc)
	 * @see elevatorunit.IMotor#startMotor()
	 */
	@Override
	public boolean startMotor() {
		// setting flag
		return true;
	}

	/* (non-Javadoc)
	 * @see elevatorunit.IMotor#stopMotor()
	 */
	@Override
	public boolean stopMotor() {
		// Reseting Flag
		return false;
	}

}
