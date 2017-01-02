package publications;
import be.kuleuven.cs.som.annotate.Basic;
import exceptions.*;

/**
 * A class of book as a special kind of publication. In addition to title,
 * authors, year, book have a publisher.
 * 
 * @versin 1.0
 * @author wangbo
 */

public class Book extends Publication {

	/**
	 * @post The publisher of this new book is equal to the given
	 *       publisher.
	 * 
	 * @param title
	 *            The title of the publication.
	 * @param authors
	 *            The authors name of the publication
	 * @param year
	 *            The publish year.
	 * @param publisher
	 *            The publisher name of this book.
	 * 
	 * @throws IllegalAuthorException if the given author is invalid
	 * @throws IllegalYearException if the given year is invalid
	 */
	public Book(String title, String[] authors, int year, String publisher) 
			throws IllegalAuthorException, IllegalYearException {
		super(title, authors, year);
		setPublisher(publisher);
	}
	
	/**
	 * Return the name of the publisher of this book.
	 */
	@Basic
	public String getPublisher(){
		return this.publisher;
	}
	
	/**
	 * 
	 * Set the publisher of the book as the given publisher name.
	 * 
	 * @post The name of the publisher will be equal to the given
	 *       publisherName.
	 * @param publisherName
	 * 		  The name of the publisher to be set.
	 */
	public void setPublisher(String publisherName){
		if(this.isTerminated()){
			throw new IllegalStateException();
		}
		this.publisher = publisherName;
	}
	

	/**
	 * The publisher name of the book.
	 */
	private String publisher;
	
	/**  
	 * Return the weight of the book when calculate citation index.
	 */
	@Basic
	public double getWeight(){
		return Book.weight;
	}
	
	/**
	 * Set the book weight to the given weight.
	 * @param weight
	 * 		  The weight to be set for the book
	 */
	@Basic
	public static void setWeight(double weight) throws IllegalWeightException{
		//if (weight<0){
		//	throw new IllegalWeightException(weight);}
		if (! isValidWeight(weight)){
					throw new IllegalWeightException(weight);}
		Book.weight = weight;
	}
	/**
	 * The weigh of book when calculate citation index.
	 * The default value is 1.2
	 */
	private static double weight = 1.2;
	
	public String toString() {
		return "Book: " + this.getTitle() + ", " + this.getAuthorsNames() + 
			", " + this.getYear() +", "+ this.getPublisher();
	}
}
