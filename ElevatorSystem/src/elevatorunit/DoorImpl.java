package elevatorunit;

public class DoorImpl implements IDoor {
	boolean open = false; //default door is closed
	@Override
	public boolean open() {
		this.open = !this.open;
		return this.open;
	}

	@Override
	public boolean close() {
		this.open = !this.open;
		return this.open;	
	}
	@Override
	public boolean operate() {
		if (this.open) {
			return close();
		}else {
			return open();
		}
	}

}
