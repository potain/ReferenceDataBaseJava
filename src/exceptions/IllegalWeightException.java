package exceptions;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalWeightException extends Exception {
	/**
	 * Initialize this new illegalWeight exception with given value.
	 * 
	 * @param  value
	 *         The value for this new illegal Weight exception.
	 * @post   The value of this new illegal Weight exception is equal
	 *         to the given value.
	 * 
	 */
	public IllegalWeightException(double Weight){
		this.Weight = Weight;
	}
	
	/**
	 * 
	 * Return the register for this invalid Weight.
	 */
	@Basic @Immutable
	public double getValue(){
		return this.Weight;
	}
	
	/**
	 * 
	 * Variable registering the value involved in this illegal Weight
	 * exception.
	 */
	private final double Weight;

}