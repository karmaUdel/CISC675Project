package elevatorunit;

public class DoorImpl implements IDoor {
	boolean open;
	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		this.open = true;
		return this.open;
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		this.open = false;
		return this.open;	}

}
