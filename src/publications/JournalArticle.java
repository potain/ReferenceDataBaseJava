package publications;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import exceptions.IllegalAuthorException;
import exceptions.IllegalIssueNumberException;
import exceptions.IllegalWeightException;
import exceptions.IllegalYearException;

/**
 * A class of JournalArtical as a special kind of publication. In addition to title,
 * authors, year, JournalArtical have a journalName and a issueNumber.
 * 
 * @invar  The issueNumber must be a valid issueNumber.
 * @versin 1.0
 * @author wangbo
 */

public class JournalArticle extends Publication {

	/**
	 * Initialize this new journalArticle with given title, authors, journal,
	 * issueNumber and year.
	 * 
	 * @pre The given issue number must be a valid number for an journal.
	 * @post The journal of this new publication is equal to the given journal.
	 * @post The issueNumber of this new publication is equal to the given
	 *       issueNumber number.
	 * 
	 * @param title
	 *            The title of the publication.
	 * @param authors
	 *            The authors name of the publication
	 * @param year
	 *            The publish year.
	 * @param journal
	 *            The journal name the publication belongs to.
	 * @param issueNumber
	 *            The issueNumber of the journal.
	 *            
	 * @throws IllegalAuthorException if the given author is invalid
	 * @throws IllegalIssueNumberException if the given issueNumber is invalid
	 * @throws IllegalYearException if the given year is invalid
	 */
	@Raw
	public JournalArticle(String title, String[] authors, String journal, int issueNumber, int year)
			throws IllegalAuthorException, IllegalIssueNumberException, IllegalYearException {
		super(title, authors, year);
		setJournal(journal);
		setIssueNumber(issueNumber);
	}
	
	/**
	 * Returns the Journal name of the article.
	 * 
	 */
	@Basic
	public String getJournal() {
		return journal;
	}

	/**
	 * Set the journal of the journalArticle as the given journal name.
	 * 
	 * @post The journal name of the journalArticle will be equal to the given
	 *       journal.
	 * @param journal
	 *        The journal name of the journalArticle
	 */
	@Basic
	public void setJournal(String journal) {
		if(this.isTerminated()){
			throw new IllegalStateException();
		}
		this.journal = journal;
	}

	/**
	 * Variable registering the name of the journal the article published.
	 */
	private String journal;

	/**
	 * Returns the issue number of the journal.
	 * 
	 */
	@Basic
	public int getIssueNumber() {
		return issueNumber;
	}

	/**
	 * Check whether the given issueNumber is a valid issue number.
	 * 
	 * @param issueNumber
	 *        The issueNumber to check
	 * @return True if and only if the given issueNumber is not negative.
	 */
	public static boolean isValidIssueNumber(int issueNumber) {
		return issueNumber > 0;
	}

	/**
	 * Set the journalArticle issueNumber as the given issueNumber.
	 * 
	 * @post The issueNumber will be equal to the given issue number.
	 * @param issueNumber
	 *        The issue Number of the journal.
	 */
	@Basic
	public void setIssueNumber(int issueNumber) throws IllegalIssueNumberException {
		if(this.isTerminated()){
			throw new IllegalStateException();
		}
		if (!isValidIssueNumber(issueNumber)) {
			throw new IllegalIssueNumberException(issueNumber);
		} else {
			if(!isTerminated());
			this.issueNumber = issueNumber;
		}

	}

	/**
	 * Variable registering the issueNumber of the journal the published in.
	 */
	private int issueNumber;
	
	/**
	 *  
	 * Return the weight of the journalArtical when calculate citation index.
	 */
	@Basic
	public double getWeight(){
		return JournalArticle.weight;
	}

	
	/**
	 * Set the journalArtical weight to the given weight.
	 * @param weight
	 * 		  The weight to be set for the publication
	 */
	@Basic
	public static void setWeight(double weight) throws IllegalWeightException{
		if (! isValidWeight(weight)){
			throw new IllegalWeightException(weight);}
		JournalArticle.weight = weight;
	}
	
	/**
	 * The weight of journalArtical when calculate citation index.
	 * The default value of the journalArticalWeight is 1.0
	 */
	private static double weight = 1.0;

	public String toString() {
		return "JournalArticle: " + this.getTitle() + ", " + this.getAuthorsNames() + 
				", " + this.getYear() +", "+ this.getIssueNumber() + ", " +  this.getJournal();
	}
}
