package publications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import exceptions.IllegalAuthorException;
import exceptions.IllegalIssueNumberException;
import exceptions.IllegalWeightException;
import exceptions.IllegalYearException;

public class BookTest {

	private Book book1, book2;
	private String[] authors = { "Wang, Bo", "De Coster, Jeroen", "Wevers, Martine" };

	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post The variable reference10yearsOld refer to a new reference published
	 *       10 years ago.
	 * @post The variable referenceLastYear references a new reference published
	 *       last year.
	 */
	@Before
	public void setUp() throws Exception {
		book1 = new Book("Gas leak rate study of MEMS", authors,  1990, "publisher1");
		book2 = new Book("Gas leak rate study of MEMS", authors, 2015, "publisher2");
	}

	@Test
	public  void extendedConstructor_LegalCase() throws Exception {
		Book newReference = new Book("Gas leak rate study of MEMS", authors,
				1990, "publisher3");
		assertEquals("Gas leak rate study of MEMS", newReference.getTitle());
		assertEquals(authors[0], newReference.getAuthors()[0]);
		assertEquals(authors[1], newReference.getAuthors()[1]);
		assertEquals(authors[2], newReference.getAuthors()[2]);
		assertEquals("publisher3", newReference.getPublisher());
		assertEquals(1990, newReference.getYear());
		assertEquals(1.2, newReference.getWeight(), 0.000001);
	}

	@Test(expected = IllegalStateException.class)
	public  void setPublisher_TerminatedCase() throws Exception {
		book1.terminate();
		book1.setPublisher("test");
	}
	
	@Test
	public void setWeight() throws IllegalWeightException{
		Book.setWeight(0.5);
		assertEquals(0.5, book1.getWeight(), 0.00001);
	}
	
	@Test(expected = IllegalWeightException.class)
	public void setWeight_wrongCase() throws IllegalWeightException{
		Book.setWeight(-0.5);
	}

}