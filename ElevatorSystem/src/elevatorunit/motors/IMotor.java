/**
 * 
 */
package elevatorunit.motors;

/**
 * @author Aditya
 *
 */
public interface IMotor {
	int beginMotor(int liftId);
	boolean shutMotor(int liftId);
}
