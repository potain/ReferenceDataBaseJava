package publications;
import be.kuleuven.cs.som.annotate.Basic;
import exceptions.*;

/**
 * A class of ConferencePaper as a special kind of publication. In addition to title,
 * authors, year, conference paper have a conference.
 * 
 * @versin 1.0
 * @author wangbo
 */

/**
 * @author wangbo
 *
 */
public class ConferencePaper extends Publication {

	/**
	 * @post The conference of this new conferencePaper is equal to the given
	 *       conference.
	 * 
	 * @param title
	 *            The title of the publication.
	 * @param authors
	 *            The authors name of the publication
	 * @param year
	 *            The publish year.
	 * @param conference
	 *            The conference name of this conference paper.
	 * 
	 *            
	 * @throws IllegalAuthorException if the given author is invalid
	 * @throws IllegalYearException if the given year is invalid
	 */
	public ConferencePaper(String title, String[] authors, int year, String conference)
			throws IllegalAuthorException, IllegalYearException {
		super(title, authors, year);
		setConference(conference);
	}
	
	/**
	 * Return the name of the conference of this paper.
	 */
	@Basic
	public String getConference(){
		return this.conference;
	}
	
	/**
	 * Set the conference of the conferencePapers as the given conference name.
	 * 
	 * @post The name of the conference will be equal to the given
	 *       conferenceName.
	 * @param conferenceName
	 * 		  The name of the conference to be set.
	 */
	@Basic
	public void setConference(String conferenceName){
		if(this.isTerminated()){
			throw new IllegalStateException();
		}
		if(!isTerminated())
		this.conference = conferenceName;
	}
	
	/**
	 * The conference name of the conference paper published.
	 */
	private String conference;
	
	/**
	 *  
	 * Return the weight of the conferencePaper when calculate citation index.
	 */
	@Basic
	public double getWeight(){
		return ConferencePaper.weight;
	}
	
	
	
	/**
	 * Set the conferencePaper weigh to the given weight.
	 * @param weight
	 * 		  The weight to be set for the conferencePaper
	 */
	@Basic
	public static void setWeight(double weight) throws IllegalWeightException{
		if (! isValidWeight(weight)){
			throw new IllegalWeightException(weight);}
		ConferencePaper.weight = weight;
	}
	
	/**
	 * The weight of conferencePaper when calculate citation index. 
	 * The default value if 0.8.
	 */
	private static double weight = 0.8;

	public String toString() {
		return "ConferencePaper: " + this.getTitle() + ", " + this.getAuthorsNames() + 
			", " + this.getYear() +", "+ this.getConference();
	}
}
