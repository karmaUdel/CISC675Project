/**
 * 
 */
package operations.movements;

/**
 * @author Aditya
 *
 */
public interface IMovement {
	int moveUp(int liftId);
	int moveDown(int liftId);
	int stop(int liftId);	
}
