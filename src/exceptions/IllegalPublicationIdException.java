package exceptions;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalPublicationIdException extends Exception {

	/**
	 * Initialize this new illegalPublicationId exception with given value.
	 * 
	 * @param  value
	 *         The value for this new illegalPublicationId exception.
	 * @post   The value of this new illegalPublicationId exception is equal
	 *         to the given value.
	 * 
	 */
	public IllegalPublicationIdException(int publicationId){
		this.publicationId = publicationId;
	}
	
	/**
	 * 
	 * Return the register for this invalid PublicationId.
	 */
	@Basic @Immutable
	public int getValue(){
		return this.publicationId;
	}
	
	/**
	 * 
	 * Variable registering the value involved in this illegal publicationId
	 * exception.
	 */
	private final int publicationId;


}
