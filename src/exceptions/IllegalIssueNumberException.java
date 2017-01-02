package exceptions;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalIssueNumberException extends Exception {
	/**
	 * Initialize this new illegalIssueNumber exception with given value.
	 * 
	 * @param  value
	 *         The value for this new illegalIssueNumber exception.
	 * @post   The value of this new illegalIssueNumber exception is equal
	 *         to the given value.
	 * 
	 */
	public IllegalIssueNumberException(int issueNumber){
		this.issueNumber = issueNumber;
	}
	
	/**
	 * 
	 * Return the register for this invalid issueNumber.
	 */
	@Basic @Immutable
	public int getValue(){
		return this.issueNumber;
	}
	
	/**
	 * 
	 * Variable registering the value involved in this illegal issueNumber
	 * exception.
	 */
	private final int issueNumber;

}
