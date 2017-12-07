package operations;

import java.util.ArrayList;

public interface IOperation {

	//ArrayList<Integer> update();
	boolean storeMaps();
	Integer storeValues(ArrayList<Integer> values);
	boolean clearSignals();
	ArrayList<Integer> update(int floor, int elevator, int button, String operation);
}
