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

public class ConferencePaperTest {

	private ConferencePaper ConferencePaper1, ConferencePaper2;
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
		ConferencePaper1 = new ConferencePaper("Gas leak rate study of MEMS", authors,  1990, "conference1");
		ConferencePaper2 = new ConferencePaper("Gas leak rate study of MEMS", authors, 2015, "conference2");
	}

	@Test
	public  void extendedConstructor_LegalCase() throws Exception {
		ConferencePaper newReference = new ConferencePaper("Gas leak rate study of MEMS", authors,
				1990, "Conference3");
		assertEquals("Gas leak rate study of MEMS", newReference.getTitle());
		assertEquals(authors[0], newReference.getAuthors()[0]);
		assertEquals(authors[1], newReference.getAuthors()[1]);
		assertEquals(authors[2], newReference.getAuthors()[2]);
		assertEquals("Conference3", newReference.getConference());
		assertEquals(1990, newReference.getYear());
		assertEquals(0.8, newReference.getWeight(), 0.000001);
	}

	@Test(expected = IllegalStateException.class)
	public  void setConference_TerminatedCase() throws Exception {
		ConferencePaper1.terminate();
		ConferencePaper1.setConference("test");
	}
	
	@Test
	public void setWeight() throws IllegalWeightException{
		ConferencePaper.setWeight(0.5);
		assertEquals(0.5, ConferencePaper1.getWeight(), 0.00001);
	}
	
	@Test(expected = IllegalWeightException.class)
	public void setWeight_wrongCase() throws IllegalWeightException{
		ConferencePaper.setWeight(-0.5);
	}

}