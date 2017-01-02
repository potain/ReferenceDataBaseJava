package exceptions;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalIncrementIDException extends Exception {
	/**
	 * Initialize this new illegalIncrementID exception with given value.
	 * 
	 * @param  value
	 *         The value for this new illegalIncrementID exception.
	 * @post   The value of this new illegalIncrementID exception is equal
	 *         to the given value.
	 * 
	 */
	public IllegalIncrementIDException(int IncrementID){
		this.IncrementID = IncrementID;
	}
	
	/**
	 * 
	 * Return the register for this invalid IncrementID.
	 */
	@Basic @Immutable
	public int getValue(){
		return this.IncrementID;
	}
	
	/**
	 * 
	 * Variable registering the value involved in this illegal IncrementID
	 * exception.
	 */
	private final int IncrementID;

}