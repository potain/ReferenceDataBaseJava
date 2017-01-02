package exceptions;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalYearException extends Exception {
	/**
	 * Initialize this new illegalyear exception with given value.
	 * 
	 * @param  value
	 *         The value for this new illegal year exception.
	 * @post   The value of this new illegal year exception is equal
	 *         to the given value.
	 * 
	 */
	public IllegalYearException(int year){
		this.year = year;
	}
	
	/**
	 * 
	 * Return the register for this invalid year.
	 */
	@Basic @Immutable
	public int getValue(){
		return this.year;
	}
	
	/**
	 * 
	 * Variable registering the value involved in this illegal year
	 * exception.
	 */
	private final int year;

}
