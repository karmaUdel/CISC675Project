package elevatorunit;

public class LightImpl implements ILights {
	int buttonLights[];
	public LightImpl() {
		// TODO Auto-generated constructor stub
	}
	public LightImpl(int numberOfFloors) {
		buttonLights = new int[numberOfFloors];
	}
	@Override
	public boolean turnOn(int button) {
		// Send Signal to turn on the lights
		buttonLights[button] = 1;
		return true;
	}

	@Override
	public boolean turnOff(int button) {
		// send signal to turn off the light
		buttonLights[button] = 0;
		return false;
	}

}
