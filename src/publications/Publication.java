package publications;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import exceptions.IllegalAuthorException;
import exceptions.IllegalWeightException;
import exceptions.IllegalYearException;

/**
 * A class of scientific publications, which involving title, authors, year of
 * publication and a ID number.
 * 
 * @invar The year of the publication must be a valid year.
 * @invar The authors name of the publication must be valid names. The name of
 *        an author is given as last name, first name, e.g., “Einstein, Albert”.
 * @invar The publication must has a proper cites and proper citedBy property.
 * 
 * @version 1.0
 * @author wangbo
 * 
 */
public abstract class Publication {

	/**
	 * Initialize this new publication with given title, authors and year.
	 * 
	 * @param title
	 *            The title of the publication.
	 * @param authors
	 *            The authors name of the publication
	 * @param year
	 *            The publish year.
	 * 
	 * @post The title of this new publication is equal to the given title.
	 * @post The authors of this new publication is equal to the given authors.
	 * @post The year of this new publication is equal to the give year.
	 * 
	 * @throws IllegalAuthorException
	 *             if the given author is invalid
	 * @throws IllegalYearException
	 *             if the given year is invalid
	 */
	@Raw
	@Model
	protected Publication(String title, String[] authors, int year)
			throws IllegalAuthorException, IllegalYearException {
		setTitle(title);
		setAuthors(authors);
		setYear(year);
	}

	/**
	 * Check whether this publication is already terminated.
	 */
	@Basic
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Terminate this publication.
	 * 
	 * @post This publication is terminated.
	 * @post No publication are attached any longer to the cites set and citedBy
	 *       set of this Publication. The cites and citeBy set of those
	 *       publications also removed this publication.
	 */
	public void terminate() {
		for (Publication citedPublication : this.getAllCites())
			if (!citedPublication.isTerminated()) {
				this.removeAsCites(citedPublication);
			}
		for (Publication publicationCitedThis : this.getAllCitedBy())
			if (!publicationCitedThis.isTerminated()) {
				this.removeAsCitedBy(publicationCitedThis);
			}
		this.isTerminated = true;
	}

	/**
	 * Variable registering whether or not this publication is terminated.
	 * 
	 */
	private boolean isTerminated;

	/**
	 * Return the title of this publication.
	 * 
	 */
	@Basic
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the publication as give title.
	 * 
	 * @post The title of this publication is equal to the given title.
	 * @param title
	 *            The title of this publication.
	 */
	@Basic
	public void setTitle(String title) {
		if (this.isTerminated())
			throw new IllegalStateException();
		this.title = title;
	}

	/**
	 * Variable registering the title of the publication.
	 */
	private String title;

	/**
	 * Returns the Authors name string list.
	 */
	@Basic
	public String[] getAuthors() {
		return authors;
	}

	/**
	 * Check if the list of authors name is valid. The valid name of an author
	 * is given as last name, first name, e.g., “Einstein, Albert”.
	 * 
	 * @return true if and only for all the authors names, isValidAuthor(author)
	 *         is true.
	 */
	public static boolean isValidAuthors(String[] authors) {
		boolean isValid = true;
		for (String author : authors) {
			if (!isValidAuthor(author)) {
				isValid = false;
			}
		}

		return isValid;
	}

	/**
	 * Check if the single authors name is valid. The valid name of an author is
	 * given as last name, first name, e.g., “Einstein, Albert”.
	 * 
	 * @param author
	 *            authorName to be checked.
	 * @return true if this is a valid authorName.
	 */
	public static boolean isValidAuthor(String author) {
		return author.matches("^[a-zA-Z ]{1,20}, [a-zA-Z][a-zA-Z ]{1,20}$");
	}

	/**
	 * Set the authors of the publication as the given authors.
	 * 
	 * @post The authors of the publication will be equal to the given authors.
	 * @param authors
	 *            The authors name list.
	 */
	@Basic
	public void setAuthors(String[] authors) throws IllegalAuthorException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (!isValidAuthors(authors)) {
			throw new IllegalAuthorException(authors);
		} else {
			this.authors = authors;
		}
	}

	/**
	 * Check the number of authors of the publication.
	 * 
	 * @return the number of the authors.
	 */
	public int getAuthorsNumber() {
		return this.getAuthors().length;
	}

	/**
	 * returns an array of authors, but represented as strings consisting the
	 * author’s initial and the last name, e.g., “A. Einstein”;
	 *
	 */
	public String[] getAuthorsNames() {
		String[] AuthorsShortNames = new String[this.getAuthorsNumber()];
		for (int i = 0; i < this.getAuthorsNumber(); i++) {
			AuthorsShortNames[i] = this.getAuthors()[i].split(", ")[1].substring(0, 1).toUpperCase() + ". "
					+ this.getAuthors()[i].split(", ")[0];
		}

		return AuthorsShortNames;
	}

	/**
	 * Variable registering the authors of the publication.
	 */
	private String[] authors;

	/**
	 * Returns the year of the publication.
	 * 
	 */
	@Basic
	public int getYear() {
		return year;
	}

	/**
	 * Check if the given year is a valid year.
	 * 
	 * @param year
	 *            The year of the publication published.
	 * @return true if and only if the 1000<= year <= currentYeaer+1.
	 */
	public static boolean isValidYear(int year) {

		Calendar now = Calendar.getInstance();// Gets the current date and time
		int currentYear = now.get(Calendar.YEAR);// The current year
		return year >= 1500 && year <= currentYear + 1;
	}

	/**
	 * Set the year of the publication as the given year.
	 * 
	 * @post The year of the publication will be equals to the give year.
	 * @param year
	 *            The publish year of the publication
	 */
	@Basic
	public void setYear(int year) throws IllegalYearException {
		if (this.isTerminated())
			throw new IllegalStateException();
		if (!isValidYear(year)) {
			throw new IllegalYearException(year);
		} else {
			this.year = year;
		}
	}

	/**
	 * Variable registering the year of the publication published.
	 */
	private int year;

	/**
	 * Capitalizes the title, if the original title was “Brownian motion in
	 * fluids”, it should be changed to “Brownian Motion In Fluids”;
	 * 
	 * @post the title of the publication will be captalized.
	 */
	public void capitalizeTitle() {
		String newTitle = toTitleCase(this.getTitle());
		this.setTitle(newTitle);
	}

	/**
	 * Set the first letter of each word of given string to upperCase
	 * 
	 * @param givenString
	 * @return String that captalized.
	 */
	private static String toTitleCase(String givenString) {
		String[] arr = givenString.split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < arr.length; i++) {
			sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
		}
		return sb.toString().trim();
	}

	/**
	 * Check if the publication was published more that 10 years ago.
	 * 
	 * @return true if the publication was published more that 10 years ago.
	 */
	public boolean is10YearsOld() {
		return this.year < 2006;
	}

	/**
	 * Check if the given publication is the same as this.
	 * 
	 * @param other
	 *            The publication to be checked.
	 * @return True if the authors, title, year and the class type of this and
	 *         other are all the same.
	 */
	public boolean isTheSameAs(Publication other) {
		return (this.getAuthors() == other.getAuthors()) && (this.getClass() == other.getClass())
				&& (this.getTitle() == other.getTitle()) && (this.getYear() == other.getYear());
	}

	/**
	 * Return a set collecting shallow copy of all publications that this
	 * publication has been cited.
	 * 
	 * @return the set of publications that this publication cited.
	 */
	public Set<Publication> getAllCites() {
		return new HashSet<Publication>(this.cites);
	}

	/**
	 * Check whether this publication can cite the given publication.
	 * 
	 * @return False if the given publication is not effective. False if the
	 *         given publication is newer than this publication False if the
	 *         given publication is the same as this. Otherwise, true if and
	 *         only if this publication and the given publication is not yet
	 *         terminated.
	 */
	public boolean canCites(Publication publication) {
		return (publication != null) && (!this.isTheSameAs(publication)) && (this.getYear() >= publication.getYear())
				&& !this.isTerminated() && !publication.isTerminated();
	}

	/**
	 * Add the given publication as cited publication of this publication.
	 * 
	 * @param publication
	 *            The article to be add as cited article.
	 * @post This publication has the given publication as one of its cited
	 *       publication in its cites Set.
	 * @post The given publication reference this publication as one of its
	 *       citedBy publication in its citedBy set.
	 * @throws IllegalArgumentException
	 *             This publication cannot have the given publication as one of
	 *             its cites publication.
	 */
	public void addAsCites(Publication publication) throws IllegalArgumentException {
		if (!canCites(publication))
			throw new IllegalArgumentException();
		this.cites.add(publication);
		publication.citedBy.add(this);
	}

	/**
	 * Check whether the given publication has already been cited by this
	 * publication.
	 * 
	 * @return True if and only if this publication has the given publication as
	 *         one of its elements in the cites Set.
	 */
	public boolean alreadyCites(Publication publication) {
		return this.cites.contains(publication);
	}

	/**
	 * Remove the given publication from the cites set attached to this
	 * publication
	 * 
	 * @param publication
	 *            The publication to be removed.
	 * @post This publication does not have the given publication as one of its
	 *       cited publication.
	 * @post If this publication has the given publication as one of its cited
	 *       publication, the given publication remove this publication from its
	 *       citedBy set.
	 */
	public void removeAsCites(Publication publication) {
		if (alreadyCites(publication)) {
			this.cites.remove(publication);
			publication.citedBy.remove(this);
		}
	}

	/**
	 * Check whether this publication has proper cites attached to it.
	 * 
	 * @return True if and only if this publication can have each of its cites
	 *         attached to it, and each of its cites references this publication
	 *         as their citedBy set element.
	 */
	public boolean hasProperCites() {
		for (Publication publication : this.cites) {
			if (!canCites(publication))
				return false;
			if (!publication.alreadyCitedBy(this))
				return false;
		}
		return true;
	}

	/**
	 * Set collecting references to publication that cited by this publication
	 * 
	 * @invar Each element in the set of publication references a publication
	 *        can be cited by this publication.
	 * @invar Each publication in the set of publication references this
	 *        publication in the citedBy set.
	 */
	private final Set<Publication> cites = new HashSet<Publication>();

	/**
	 * Check whether the given publication has already cites this publication.
	 * 
	 * @return True if and only if this publication has the given publication as
	 *         one of its elements in the citesBy Set.
	 */
	@Basic
	public boolean alreadyCitedBy(Publication publication) {
		return this.citedBy.contains(publication);
	}

	/**
	 * Return a set collecting shallow copy of all publication that cites this
	 * publication.
	 * 
	 * @return the set of publications that cited this.
	 */
	public Set<Publication> getAllCitedBy() {
		return new HashSet<Publication>(this.citedBy);
	}

	/**
	 * Check whether this publication can be cited by the given publication.
	 * 
	 * @return False if this publication is not effective or if the given
	 *         publication is older than this publication, or if the given
	 *         publication is the same as this, otherwise, true if and only if
	 *         this publication and the given publication is not yet terminated.
	 */
	public boolean canBeCitedBy(Publication publication) {
		return (publication != null) && (!this.isTheSameAs(publication)) && (this.getYear() <= publication.getYear())
				&& !this.isTerminated() && !publication.isTerminated();
	}

	/**
	 * Add the given publication as citedBy publication of this publication.
	 * 
	 * @param publication
	 *            The publication to be add as citedBy publication.
	 * @post This publication has the given publication as one of its citedBy
	 *       publication in its citedBy Set.
	 * @post The given publication reference this publication as one of its
	 *       cites publication in its cites set.
	 * @throws IllegalArgumentException
	 *             This publication cannot have the given publication as one of
	 *             its citedBy article.
	 */
	public void addAsCitedBy(Publication publication) throws IllegalArgumentException {
		if (!canBeCitedBy(publication))
			throw new IllegalArgumentException();
		this.citedBy.add(publication);
		publication.cites.add(this);
	}

	/**
	 * Remove the given publication from the citedBy set attached to this
	 * publication
	 * 
	 * @param publication
	 *            The publication to be removed from the citedBy set.
	 * @post This publication dose not have the given publication as one of its
	 *       cetiedBy publication.
	 * @post If this publication has the given publication as one of its citedBy
	 *       publication, the given publication remove this publication from its
	 *       cites Set.
	 */
	public void removeAsCitedBy(Publication publication) {
		if (alreadyCitedBy(publication)) {
			this.citedBy.remove(publication);
			publication.cites.remove(this);
		}
	}

	/**
	 * Check whether this publication has proper citedBy publication attached to
	 * it.
	 * 
	 * @return True if and only if this publication can have each of its citedBy
	 *         attached to it, and each of its citedBy references this
	 *         publication as their cites set element.
	 */
	public boolean hasProperCitedBy() {
		for (Publication publication : this.citedBy) {
			if (!canBeCitedBy(publication))
				return false;
			if (!publication.alreadyCites(this))
				return false;
		}
		return true;
	}

	/**
	 * Set collecting references to publications that cite this publication.
	 * 
	 * @invar Each element in the set of publications references a publication
	 *        that can cite this publications.
	 * @invar Each element in the set of publications references this
	 *        publications in their cites set.
	 */
	private final Set<Publication> citedBy = new HashSet<Publication>();

	/**
	 * Return the ID number of this.
	 * 
	 * @return The id number of this.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the ID number of this.
	 * 
	 * @param id
	 *            The id number tobe set.
	 */
	public void setId(int id) {
		if (this.isTerminated())
			throw new IllegalStateException();
		this.id = id;
	}

	/**
	 * The unique id number that refer to this, the initial value is -1.
	 */
	private int id = -1;

	/**
	 * 
	 * Return the weight of the publication when calculate citation index.
	 */
	public abstract double getWeight();

	protected static boolean isValidWeight(double weight) throws IllegalWeightException {
		if (weight < 0) {
			throw new IllegalWeightException(weight);
		} else {
			return true;
		}

	}

}
