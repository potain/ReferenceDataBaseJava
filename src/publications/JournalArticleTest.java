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

public class JournalArticleTest {

	private JournalArticle journal1, journal2;
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
		journal1 = new JournalArticle("Gas leak rate study of MEMS", authors, "journal of MEMS", 123, 1990);
		journal2 = new JournalArticle("Gas leak rate study of MEMS", authors, "journal of MEMS", 123, 2015);
	}

	@Test
	public  void extendedConstructor_LegalCase() throws Exception {
		JournalArticle newReference = new JournalArticle("Gas leak rate study of MEMS", authors, "journal of MEMS", 123,
				1990);
		assertEquals("Gas leak rate study of MEMS", newReference.getTitle());
		assertEquals(authors[0], newReference.getAuthors()[0]);
		assertEquals(authors[1], newReference.getAuthors()[1]);
		assertEquals(authors[2], newReference.getAuthors()[2]);
		assertEquals("journal of MEMS", newReference.getJournal());
		assertEquals(123, newReference.getIssueNumber());
		assertEquals(1990, newReference.getYear());
		assertEquals(1.0, newReference.getWeight(), 0.000001);
	}

	@Test(expected = IllegalAuthorException.class)
	public  void extendedConstructor_IllLegalAuthorCase() throws Exception {
		JournalArticle newReference = new JournalArticle("Gas leak rate study of MEMS", new String[] { "WangBo" },
				"journal of MEMS", 123, 1990);
	}

	@Test(expected = IllegalYearException.class)
	public  void extendedConstructor_IllLegalYearCase() throws Exception {
		JournalArticle newReference = new JournalArticle("Gas leak rate study of MEMS", authors, "journal of MEMS", 123,
				-1990);
	}

	@Test(expected = IllegalIssueNumberException.class)
	public  void extendedConstructor_IllLegalIssueNumberCase() throws Exception {
		JournalArticle newReference = new JournalArticle("Gas leak rate study of MEMS", authors, "journal of MEMS",
				-123, 1990);
	}

	@Test(expected = IllegalIssueNumberException.class)
	public  void middleConstructor_IllegalIssueNumberCase() throws Exception {
		new JournalArticle("Gas leak rate study of MEMS", new String[] { "Wang, Bo" }, "journal of MEMS", -123, 1990);
	}

	@Test(expected = IllegalStateException.class)
	public  void setIssueNumber_TerminatedCase() throws Exception {
		journal1.terminate();
		journal1.setIssueNumber(123);
	}

	@Test(expected = IllegalStateException.class)
	public  void setJournal_TerminatedCase() throws Exception {
		journal1.terminate();
		journal1.setJournal("test");
	}

	@Test
	public  void isValidIssueNumber_TrueCase() {
		assertTrue(JournalArticle.isValidIssueNumber(123));
	}

	@Test
	public  void isValidIssueNumber_zeronumber() {
		assertFalse(JournalArticle.isValidIssueNumber(0));
	}

	@Test
	public  void isValidIssueNumber_negative() {
		assertFalse(JournalArticle.isValidIssueNumber(-12));
	}
	
	@Test
	public void setWeight() throws IllegalWeightException{
		JournalArticle.setWeight(0.5);
		assertEquals(0.5, journal1.getWeight(), 0.00001);
	}
	
	@Test(expected = IllegalWeightException.class)
	public void setWeight_wrongCase() throws IllegalWeightException{
		JournalArticle.setWeight(-0.5);
	}

}
