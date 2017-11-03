/**
 * 
 */
package elevatorunit.lights;

/**
 * @author Aditya
 *
 */
public class Light implements ILight {

	/* (non-Javadoc)
	 * @see elevatorunit.lights.ILight#isLightOn(int)
	 */
	@Override
	public boolean isLightOn(int liftId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see elevatorunit.lights.ILight#turnOn(int)
	 */
	@Override
	public boolean turnOn(int liftId) {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see elevatorunit.lights.ILight#turnOff(int)
	 */
	@Override
	public boolean turnOff(int liftId) {
		// TODO Auto-generated method stub
		return true;
	}

}
