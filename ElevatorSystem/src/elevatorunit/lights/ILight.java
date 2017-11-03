/**
 * 
 */
package elevatorunit.lights;

/**
 * @author Aditya
 *
 */
public interface ILight {
	boolean isLightOn(int liftId);
	boolean turnOn(int liftId);
	boolean turnOff(int liftId);
}
