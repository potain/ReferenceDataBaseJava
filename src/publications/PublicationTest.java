package publications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author wangbo
 *
 */
public class PublicationTest {

	private Publication reference10yearOld, referenceLastYear, publication1, publication2, publication3, publication4, 
			publication5, publication6, publication_SameAs1,publication_Terminated;
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
		reference10yearOld = new JournalArticle("Gas leak rate study of MEMS", authors, "journal of MEMS", 123, 1990);
		referenceLastYear = new JournalArticle("Gas leak rate study of MEMS", authors, "journal of MEMS", 123, 2015);

		publication1 = new JournalArticle("publication1", authors, "journal of MEMS", 123, 2016);
		publication2 = new Book("publication2", new String[] { "Eric, Steegmans" }, 2014, "acco");
		publication3 = new ConferencePaper("publication3", new String[] { "Wang, Bo", "Ann, WitVrouw" },
				2012, "Transducers");
		publication4 = new JournalArticle("publication4", new String[] { "Archesis, Test", "Shengping, Mao" },
				"journal of MEMS", 123, 2010);
		publication5 = new Book("publication5", new String[] { "Els, Wang", "Oliever, Thus" }, 2008,
				"Springer");
		publication6 = new Book("publication6", new String[] { "Hellen, Wang", "Ou, Helen" }, 2006,
				"Springer");
		publication_SameAs1 = new JournalArticle("publication1", authors, "journal of MEMS", 123, 2016);
		publication_Terminated = new JournalArticle("publication_T", authors, "journal of MEMS", 123, 2012);
		publication_Terminated.terminate();
		
		publication1.addAsCites(publication3);
		publication2.addAsCites(publication3);
		publication5.addAsCitedBy(publication3);
		publication6.addAsCitedBy(publication3);
		publication4.addAsCites(publication6);

	}

	@Test(expected = IllegalStateException.class)
	public final void setID_TerminatedCase() throws Exception {
		publication1.terminate();
		publication1.setId(300);
	}
	
	@Test(expected = IllegalStateException.class)
	public final void setAuthors_TerminatedCase() throws Exception {
		publication1.terminate();
		publication1.setAuthors(new String[] {"Bo, Wang"});
	}
	
	@Test(expected = IllegalStateException.class)
	public final void setYear_TerminatedCase() throws Exception {
		publication1.terminate();
		publication1.setYear(2000);
	}
	
	@Test(expected = IllegalStateException.class)
	public final void setTitle_TerminatedCase() throws Exception {
		publication1.terminate();
		publication1.setTitle("test");
	}
	
	@Test
	public void isValidAuthors_TrueCase() {
		assertTrue(Publication.isValidAuthors(authors));
	}

	@Test
	public void isValidAuthors_FalseCase_twospace() {
		String[] authors = { "Wang,  Bo", "De Coster, Jeroen", "Wevers, Martine" };
		assertFalse(Publication.isValidAuthors(authors));
	}

	@Test
	public void isValidAuthors_FalseCase_noComma() {
		String[] authors = { "Wang  Bo", "De Coster, Jeroen", "Wevers, Martine" };
		assertFalse(Publication.isValidAuthors(authors));
	}

	@Test
	public void getAuthorsNumber_SingleCase() {
		assertEquals(3, reference10yearOld.getAuthorsNumber());
	}
	
	@Test
	public void getAuthorsNames_SingleCase() {
		assertEquals("B. Wang", reference10yearOld.getAuthorsNames()[0]);
		assertEquals("J. De Coster", reference10yearOld.getAuthorsNames()[1]);
		assertEquals("M. Wevers", reference10yearOld.getAuthorsNames()[2]);
	}


	@Test
	public void isValidYear_TrueCase1() {
		assertTrue(Publication.isValidYear(2016));
	}

	@Test
	public void isValidYear_TrueCase2() {
		assertTrue(Publication.isValidYear(1945));
	}

	@Test
	public void isValidYear_FalseCase_largerthan2016() {
		assertFalse(Publication.isValidYear(2500));
	}

	@Test
	public void isValidYear_FalseCase_smallerthan2016() {
		assertFalse(Publication.isValidYear(1499));
	}

	@Test
	public void isValidYear_FalseCase_negativenumber() {
		assertFalse(Publication.isValidYear(-2015));
	}

	@Test
	public void capitalizeTitle_SingleCase() {
		reference10yearOld.capitalizeTitle();
		assertEquals("Gas Leak Rate Study Of MEMS", reference10yearOld.getTitle());
	}

	@Test
	public void is10YearsOld_TrueCase() {
		assertTrue(reference10yearOld.is10YearsOld());
	}

	@Test
	public void is10YearsOld_FalseCase() {
		assertFalse(referenceLastYear.is10YearsOld());

	}
	
	@Test
	public void isTheSameAs_FalseCase() {
		assertFalse(publication1.isTheSameAs(publication2));
	}
	
	@Test
	public void isTheSameAs_TrueCase() {
		assertTrue(publication1.isTheSameAs(publication_SameAs1));
	}
	
	@Test
	public void getAllCites_twoCites() {
	    Set<Publication> result = publication3.getAllCites();
	    assertNotNull(result);
	    assertEquals(2,result.size());
	}
	
	@Test
	public void getAllCites_nullCites() {
	    Set<Publication> result = publication5.getAllCites();
	    assertNotNull(result);
	    assertEquals(0,result.size());
	}
	
	@Test
	public void getAllCites_terminatedCase() {
		publication3.terminate();
	    Set<Publication> result = publication3.getAllCites();
	    assertNotNull(result);
	    assertEquals(0,result.size());
	}
	
	@Test
	public void getAllCitedBy_twoCitedBy() {
	    Set<Publication> result = publication3.getAllCites();
	    assertNotNull(result);
	    assertEquals(2,result.size());
	}
	
	@Test
	public void getAllCitedBy_nullCitedBy() {
	    Set<Publication> result = publication4.getAllCitedBy();
	    assertNotNull(result);
	    assertEquals(0,result.size());
	}
	
	@Test
	public void getAllCitedBy_nullCitedBy_TerminatedCase() {
		publication4.terminate();
	    Set<Publication> result = publication4.getAllCitedBy();
	    assertNotNull(result);
	    assertEquals(0,result.size());
	}
	
	@Test
	public void canCites_trueCase(){
		assertTrue(publication3.canCites(publication5));
	}
	
	@Test
	public void canCites_falseCase_dueToNull(){
		assertFalse(publication3.canCites(null));
	}
	
	
	@Test
	public void canCites_falseCase_dueToTerminated(){
		publication3.terminate();
		assertFalse(publication3.canCites(publication4));
	}
	
	@Test
	public void canCites_falseCase_dueToTerminatedCites(){
		publication4.terminate();
		assertFalse(publication3.canCites(publication4));
	}
	
	
	@Test
	public void canCites_falseCase_dueToTime(){
		assertFalse(publication3.canCites(publication1));
	}
	
	@Test
	public void canBeCitedBy_trueCase(){
		assertTrue(publication3.canBeCitedBy(publication1));
	}
	
	@Test
	public void canBeCiteBy_falseCase_dueToNull(){
		assertFalse(publication3.canBeCitedBy(null));
	}
	
	@Test
	public void canBeCitedBy_falseCase_dueToTerminated(){
		publication3.terminate();
		assertFalse(publication3.canBeCitedBy(publication1));
	}
	
	@Test
	public void canBeCitedBy_falseCase_dueToTerminatedCites(){
		publication1.terminate();
		assertFalse(publication3.canBeCitedBy(publication1));
	}
	
	@Test
	public void canBeCitedBy_falseCase_dueToTime(){
		assertFalse(publication3.canBeCitedBy(publication5));
	}
	
	@Test
	public void alreadyCites_TrueCase(){
		assertTrue(publication3.alreadyCites(publication5));
		
	}
	
	@Test
	public void alreadyCites_FalseCase(){
		assertFalse(publication3.alreadyCites(publication1));
	}
	
	@Test
	public void alreadyCites_FalseCase_DuetoTerminated(){
		publication3.terminate();
		assertFalse(publication3.alreadyCites(publication5));
	}
	
	@Test
	public void alreadyCites_FalseCase_DuetoTerminatedCites(){
		publication5.terminate();
		assertFalse(publication3.alreadyCites(publication5));
	}
	
	@Test
	public void alreadyCitedBy_TrueCase(){
		assertTrue(publication3.alreadyCitedBy(publication1));
		
	}
	
	@Test
	public void alreadyCitedBy_FalseCase(){
		assertFalse(publication3.alreadyCitedBy(publication6));
	}
	
	@Test
	public void alreadyCitedBy_FalseCase_DuetoTerminated(){
		publication3.terminate();
		assertFalse(publication3.alreadyCitedBy(publication1));
	}
	
	@Test
	public void alreadyCitedBy_FalseCase_DuetoTerminatedCitedBY(){
		publication1.terminate();
		assertFalse(publication3.alreadyCitedBy(publication1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addAsCites_terminatedCase(){
		publication_Terminated.addAsCites(publication4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addAsCites_citesSelf(){
		publication3.addAsCites(publication3);
	}
	
	@Test
	public void removeAsCites(){
		publication3.removeAsCites(publication5);
		assertFalse(publication3.alreadyCites(publication5));
		assertFalse(publication5.alreadyCitedBy(publication3));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addAsCitedBy_terminatedCase(){
		publication_Terminated.addAsCitedBy(publication1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addAsCitedBy_citedBySelf(){
		publication3.addAsCitedBy(publication3);
	}
	
	@Test
	public void removeAsCitedBy(){
		publication3.removeAsCitedBy(publication1);
		assertFalse(publication1.alreadyCites(publication3));
		assertFalse(publication3.alreadyCitedBy(publication1));
		
	}
	
	@Test
	public void hasProperCites_TrueCase(){
		assertTrue(publication1.hasProperCites());
		assertTrue(publication2.hasProperCites());
		assertTrue(publication3.hasProperCites());
		assertTrue(publication4.hasProperCites());
		assertTrue(publication5.hasProperCites());
		assertTrue(publication6.hasProperCites());
	}
	
	public void hasProperCites_True_TerminatedCase(){
		publication1.terminate();
		publication2.terminate();
		publication3.terminate();
		publication4.terminate();
		publication5.terminate();
		publication6.terminate();
		assertTrue(publication1.hasProperCites());
		assertTrue(publication2.hasProperCites());
		assertTrue(publication3.hasProperCites());
		assertTrue(publication4.hasProperCites());
		assertTrue(publication5.hasProperCites());
		assertTrue(publication6.hasProperCites());
	}
	
	@Test
	public void hasProperCitedBy_TrueCase(){
		assertTrue(publication1.hasProperCitedBy());
		assertTrue(publication2.hasProperCitedBy());
		assertTrue(publication3.hasProperCitedBy());
		assertTrue(publication4.hasProperCitedBy());
		assertTrue(publication5.hasProperCitedBy());
		assertTrue(publication6.hasProperCitedBy());
	}
	
	public void hasProperCitedBy_True_TerminatedCase(){
		publication1.terminate();
		publication2.terminate();
		publication3.terminate();
		publication4.terminate();
		publication5.terminate();
		publication6.terminate();
		assertTrue(publication1.hasProperCitedBy());
		assertTrue(publication2.hasProperCitedBy());
		assertTrue(publication3.hasProperCitedBy());
		assertTrue(publication4.hasProperCitedBy());
		assertTrue(publication5.hasProperCitedBy());
		assertTrue(publication6.hasProperCitedBy());
	}
	
	@Test
	public void getID(){
		assertTrue(publication1.getId() == -1);
	}
	
	@Test
	public void setID(){
		publication1.setId(1000);
		assertTrue(publication1.getId() == 1000);
	}
		
	

}
