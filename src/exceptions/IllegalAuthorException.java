package exceptions;
import java.util.ArrayList;
import java.util.Arrays;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

public class IllegalAuthorException extends Exception {
	
	/**
	 * Initialize this new illegalanme exception with given value.
	 * 
	 * @param  value
	 *         The value for this new illegal author exception.
	 * @post   The value of this new illegal author exception is equal
	 *         to the given value.
	 * 
	 */
	public IllegalAuthorException(String[] authornames){
		this.authornames = authornames;
	}
	
	public IllegalAuthorException(String authorname){
		this.authornames = new String[] {authorname};
	}
	
	/**
	 * 
	 * Return the name register for this invalid name.
	 */
	@Basic @Immutable
	public String[] getValue(){
		return this.authornames;
	}
	
	/**
	 * 
	 * Variable registering the value involved in this illegal authornames
	 * exception.
	 */
	private final String[] authornames;

}
