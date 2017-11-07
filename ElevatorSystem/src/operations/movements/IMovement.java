/**
 * 
 */
package operations.movements;

/**
 * @author Aditya
 *
 */
public interface IMovement {
	int moveUp(int liftId, int req_floor);
	int moveDown(int liftId, int req_floor);
	int stop(int liftId);	
}
