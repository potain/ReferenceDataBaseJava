package referenceDB;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import exceptions.IllegalAuthorException;
import exceptions.IllegalIncrementIDException;
import exceptions.IllegalPublicationIdException;
import publications.Publication;

/**
 * A class representing a reference database, i.e., a collection of
 * publications.
 * 
 * @invar The ReferenceDataBase must have proper publications.
 * @version 1.0
 * @author wangbo
 *
 */
public class ReferenceDataBase {

	/**
	 * Initialize this new ReferenceDatabase with no publications attached to
	 * it. Only set its incrementID to 1001 so all the ID of new added publications
	 * start counting from 1001.
	 * 
	 * @post No publications are attached to this ReferenceDataBase.
	 */
	@Raw
	public ReferenceDataBase(){
		ReferenceDataBase.incrementID = 1001;
	}

	/**
	 * Check whether this DataBase is already terminated.
	 */
	@Basic
	@Raw
	public boolean isTerminated() {
		return this.isTerminated;
	}

	/**
	 * Terminate this DataBase.
	 * 
	 * @post This DataBase is terminated.
	 * @post All publication belonging to this DataBase have been Removed.
	 */
	public void terminate() {
		if (!isTerminated) {
			this.publications.clear();
		}
		this.isTerminated = true;
	}

	/**
	 * Variable registering whether or not this DataBase is terminated.
	 */
	private boolean isTerminated;

	/**
	 * Check whether this DataBase has the given publication as one of the
	 * publications attached to it.
	 * 
	 * @param publication
	 *            The publication to check.
	 */
	@Raw
	public boolean hasPublication(Publication publication) {
		return this.publications.containsKey(publication.getId());
	}

	/**
	 * Check whether publication with the given ID in the database.
	 * 
	 * @param id
	 *            The id to be checked.
	 * @return True if the publication with given in the dataBase, otherwise
	 *         return false.
	 */
	@Basic
	public boolean hasPublicationID(int id) {
		return this.publications.containsKey(id);
	}

	/**
	 * Get the publication in the database by the given ID.
	 * 
	 * @param id
	 *            The publication ID.
	 * @return The publication if the id in the database, otherwise return null.
	 * @throws IllegalPublicationIdException
	 *             If the given ID do not exise in the publication DataBase.
	 */
	@Basic
	public Publication getPublicationWithID(int id) throws IllegalPublicationIdException {
		if (!this.hasPublicationID(id))
			throw new IllegalPublicationIdException(id);
		return this.publications.get(id);
		
	}

	/**
	 * Check whether this DataBase has proper publications associated with it.
	 * 
	 * @return True if and only if this DataBase can have each of its
	 *         publications as a element of its publications set.
	 */
	public boolean hasProperPublications() {
		for (Publication publication : this.getAllPublications())
			if (!canHaveAsPublication(publication))
				return false;
		return true;
	}

	/**
	 * Check whether this Database can have the given publication as one of its
	 * elements.
	 * 
	 * @param publication
	 *            The publication to check
	 * @return False if the given publication is not effective.
	 */
	public boolean canHaveAsPublication(Publication publication) {
		return (publication != null) && (!publication.isTerminated());
	}

	/**
	 * Add the given publication to the set of publications attached to this
	 * Database.
	 * 
	 * @param publication
	 *            The publication to be added.
	 * @post This Database has the given publication as one of its publications.
	 * @throws IllegalArgumentException
	 *             The given publication is already attached to the DataBase.
	 *             The given publication can not be attached to the DataBase.
	 */
	public void addAsPublication(Publication publication) throws IllegalArgumentException {
		if (!canHaveAsPublication(publication) || hasPublication(publication))
			throw new IllegalArgumentException();
		publication.setId(incrementID++);
		this.publications.put(publication.getId(), publication);
	}

	/**
	 * Remove the given publication from the set of publications attached to
	 * this DataBase.
	 * 
	 * @param publication
	 *            The publication to be removed.
	 * @post This Database does not have the given publication as one of its
	 *       publications.
	 * @post If this Database has the given publication as one of it
	 *       publications, the given publication terminated, the association
	 *       between this publication and all the other publication are removed.
	 */
	public void removePublication(Publication publication) {
		if (hasPublication(publication)) {
			publication.terminate();
			this.publications.remove(publication.getId());
		}
	}

	/**
	 * Return a set collecting all publications associated with this Database.
	 */
	@Basic
	@Raw
	public Set<Publication> getAllPublications() {
		return new HashSet<Publication>(this.publications.values());
	}

	/**
	 * Set collecting references to publications attached to this DataBase
	 * 
	 * @invar The set of publications is effective.
	 */
	private final Map<Integer, Publication> publications = new HashMap<>();

	/**
	 * Check if the single authors name is valid. The valid name of an author is
	 * given as initialOfFirstName. lastName, e.g., “A. Einstein”.
	 * 
	 * @param author
	 *            The author name to be checked.
	 * @return true if this is a valid author name.
	 */
	public static boolean isValidAuthor(String author) {
		return author.matches("^[a-zA-Z]{1}. [a-zA-Z][a-zA-Z ]{1,20}$");
	}

	/**
	 * Find all publications authored by an author.
	 * 
	 * @param authorName
	 *            The author name to be searched, given as “initialOfFirstName.
	 *            lastName”, e.g., A. Einstein;
	 * @return all publications authored by an author.
	 * @throws IllegalArgumentException
	 *             If the given authorName is not in format "initialOfFirstName.
	 *             lastName"
	 */
	public Set<Publication> findByAuthor(String authorName) throws IllegalAuthorException {
		if (!isValidAuthor(authorName))
			throw new IllegalAuthorException(authorName);
		Set<Publication> referenceSet = new HashSet<Publication>();
		for (Publication publication : this.getAllPublications()) {
			if (Arrays.asList(publication.getAuthorsNames()).contains(authorName)) {
				referenceSet.add(publication);
			}
		}
		return referenceSet;
	}

	/**
	 * Returns all publications that have a given word in their title;
	 * 
	 * @param word
	 *            The word to be searched in title.
	 * @return Set of publications that have a given word in their title;
	 */
	public Set<Publication> findByTitleWord(String word) {
		Set<Publication> referenceSet = new HashSet<Publication>();
		for (Publication publication : this.getAllPublications()) {
			if (publication.getTitle().toLowerCase().contains(word.toLowerCase())) {
				referenceSet.add(publication);
			}
		}
		return referenceSet;
	}

	/**
	 * Add a citation relationship of two publications. Given as a pair of
	 * publication identifiers where the second represents an publication cited
	 * by the first;
	 * 
	 * @param publicationID1
	 *            The publication id that cites publication2
	 * @param publicationID2
	 *            The publication id that cited by publication1
	 * @throws IllegalArgumentException
	 *             The publication with publicationID is not in the DataBase The
	 *             publication1 can not cites publication2.
	 */
	public void addCitation(int publicationID1, int publicationID2) throws IllegalPublicationIdException {
		Publication publication1 = getPublicationWithID(publicationID1);
		Publication publication2 = getPublicationWithID(publicationID2);
		publication1.addAsCites(publication2);
	}

	/**
	 * Calculate the citation index of given author. The citation index is
	 * defined as the weighted sum of the citations of all the author's
	 * publications. The weight depend on the type of publication the author is
	 * cited in.
	 * 
	 * @param author
	 * @return The author citation index
	 */
	public double authorCitationIndex(String authorName) throws IllegalAuthorException {
		double citationIndex = 0;

		Set<Publication> publications = this.findByAuthor(authorName);
		for (Publication publication : publications) {
			citationIndex += publication.getWeight();
		}
		return citationIndex;
	}

	/**
	 * For the given publication, returns all publications that directly or
	 * indirectly cite this publication.
	 * 
	 * @param id
	 *            The publication id to be searched
	 * @return The set of publication that direct/indirectly cites this
	 *         publication.
	 */
	public Set<Publication> findDirIndirCites(int id) throws IllegalPublicationIdException {
		Set<Publication> publicationsSet = this.getPublicationWithID(id).getAllCitedBy();
		Set<Publication> results = new HashSet<>(publicationsSet);
		if (publicationsSet.size() != 0) {
			for (Publication pub : publicationsSet) {
				results.addAll(findDirIndirCites(pub.getId()));
			}
		}
		return results;
	}

	/**
	 * Get the current incrementID.
	 * 
	 * @return the current increment ID.
	 */
	@Basic
	public static int getCurrentIncrementID() {
		return incrementID;
	}

	/**
	 * Set the IncrementID to the given value.
	 * @param ID: The id number to be set.
	 * @throws IllegalIncrementIDException: if the given ID is out of bound.
	 */
	@Basic
	public static void setCurrentIncrementID(int ID) throws IllegalIncrementIDException{
		if (ID <= 0)
			throw new IllegalIncrementIDException(ID);
		incrementID = ID;
	}
	
	/**
	 * The Unique ID to be assigned to each newly added publications. After each
	 * publications been added, this ID increment 1, this ID start at 1001.
	 */
	private static int incrementID = 1001;
}
