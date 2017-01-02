package referenceDB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import exceptions.IllegalAuthorException;
import exceptions.IllegalIncrementIDException;
import exceptions.IllegalPublicationIdException;
import publications.Book;
import publications.ConferencePaper;
import publications.JournalArticle;
import publications.Publication;

/**
 * @author wangbo
 *
 */
public class ReferenceDataBaseTest{

	private ReferenceDataBase DB;
	private	Publication publication1, publication2, publication3, publication4, 
			publication5, publication6, publication7, publication8, 
			publication_SameAs1,publication_Terminated;
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
		publication1 = new JournalArticle("publication1", authors, "journal of MEMS", 123, 2016);
		publication2 = new Book("publication2", new String[] { "Eric, Steegmans" }, 2014, "acco");
		publication3 = new ConferencePaper("publication3", new String[] { "Wang, Bo", "Ann, WitVrouw" },
				2012, "Transducers");
		publication4 = new JournalArticle("publication4", new String[] { "Archesis, Test", "Shengping, Mao" },
				"journal of MEMS", 123, 2010);
		publication5 = new Book("publication5", new String[] { "Els, Wang", "Oliever, Thus", "Wang, Bo" }, 2008,
				"Springer");
		publication6 = new Book("publication6", new String[] { "Hellen, Wang", "Ou, Helen" }, 2006,
				"Springer");
		publication7 = new JournalArticle("Gas leak rate study of MEMS", authors, "journal of MEMS", 123, 1990);
		publication8 = new JournalArticle("Gas leak rate study of MEMS", authors, "journal of MEMS", 123, 2015);
		publication_SameAs1 = new JournalArticle("publication1", authors, "journal of MEMS", 123, 2016);
		publication_Terminated = new JournalArticle("publication_T", authors, "journal of MEMS", 123, 2012);
		publication_Terminated.terminate();
		
		DB = new ReferenceDataBase();
		JournalArticle.setWeight(1.0);
		Book.setWeight(1.2);
		ConferencePaper.setWeight(0.8);
		DB.addAsPublication(publication1);
		DB.addAsPublication(publication2);
		DB.addAsPublication(publication3);
		DB.addAsPublication(publication4);
		DB.addAsPublication(publication5);
		DB.addAsPublication(publication6);


	}

	@Test
	public void extendedConstructor_LegalCase() {
		ReferenceDataBase newReferenceDataBase = new ReferenceDataBase();
	}
	

	@Test
	public void hasPublication_TrueCase(){
		assertTrue(DB.hasPublication(publication1));
	}
	
	@Test
	public void hasPublication_FalseCase(){
		assertFalse(DB.hasPublication(publication7));
	}
	
	@Test
	public void hasPublicationID_TrueCase(){
		assertTrue(DB.hasPublicationID(1001));
	}
	
	@Test
	public void hasPublicationID_FalseCase(){
		assertFalse(DB.hasPublicationID(1000));
	}
	
	@Test
	public void getPublicationWithId() throws Exception{
		assertTrue(publication1 == DB.getPublicationWithID(1001));
	}
	
	@Test(expected = IllegalPublicationIdException.class)
	public void getPublicationWithId_IllegalIDCase() throws Exception{
		DB.getPublicationWithID(999);
	}
	
	@Test
	public void hasProperPublications_trueCase(){
		assertTrue(DB.hasProperPublications());
	}
	
	@Test
	public void canHaveAsPublication_TrueCase(){
		assertTrue(DB.canHaveAsPublication(publication1));
	}
	
	@Test
	public void canHaveAsPublication_FalseCase_dueToNull(){
		assertFalse(DB.canHaveAsPublication(null));
	}
	
	@Test
	public void canHaveAsPublication_FalseCase_dueToTerminated(){
		publication1.terminate();
		assertFalse(DB.canHaveAsPublication(publication1));
	}
	
	@Test
	public void addAsPublication_OK(){
		assertFalse(DB.hasPublication(publication7));
		DB.addAsPublication(publication7);
		assertTrue(DB.hasPublication(publication7));
	}
	
	@Test
	public void addAsPublication_IlleagleCase_addNull(){
		assertFalse(DB.hasPublication(publication7));
		DB.addAsPublication(publication7);
		assertTrue(DB.hasPublication(publication7));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addAsPublication_IlleagleCase_addTerminated(){
		publication7.terminate();
		DB.addAsPublication(publication7);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addAsPublication_IlleagleCase_addDuplicated(){
		DB.addAsPublication(publication7);
		DB.addAsPublication(publication7);
	}
	
	@Test
	public void removePublication(){
		DB.removePublication(publication1);
		assertFalse(DB.hasPublication(publication1));
	}
	
	@Test
	public void removePublication_DoubleRemove(){
		DB.removePublication(publication1);
		DB.removePublication(publication1);
		assertFalse(DB.hasPublication(publication1));
	}
	
	@Test
	public void removePublication_RemoveNonExisted(){
		DB.removePublication(publication7);
		assertFalse(DB.hasPublication(publication7));
	}
	
	@Test
	public void getAllPublications_6Publictions(){
	    Set<Publication> result = DB.getAllPublications();
	    assertNotNull(result);
	    assertEquals(6,result.size());
	}
	
	@Test
	public void isValidAuthor_TrueCase(){
		assertTrue(ReferenceDataBase.isValidAuthor("B. Wang"));
	}
	
	@Test
	public void isValidAuthor_FalseCase(){
		assertFalse(ReferenceDataBase.isValidAuthor("Bo, Wang"));
		
	}
	
	@Test
	public void findByAuthor_OKCase() throws Exception{
		Set<Publication> result = DB.findByAuthor("B. Wang");
		assertTrue(result.contains(publication1));
		assertFalse(result.contains(publication2));
		assertTrue(result.size() == 3);
	}
	
	@Test(expected = IllegalAuthorException.class)
	public void findByAuthor_IllegalAuthorCase() throws Exception{
		Set<Publication> result = DB.findByAuthor("Wang");
	}
	
	@Test
	public void findByTitleWord(){
		Set<Publication> result = DB.findByTitleWord("publication1");
		assertTrue(result.size() == 1);
		assertTrue(result.contains(publication1));
		assertFalse(result.contains(publication7));
		
	}
	
	@Test
	public void addCitation_SingleCase() throws Exception{
		DB.addCitation(1001, 1003);
		assertTrue(publication1.alreadyCites(publication3));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addCitation_IllagleCase_terminated1() throws Exception{
		publication1.terminate();
		DB.addCitation(1001, 1003);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addCitation_IllagleCase_terminated2() throws Exception{
		publication3.terminate();
		DB.addCitation(1001, 1003);
	}
	
	@Test(expected = IllegalPublicationIdException.class)
	public void addCitation_IllagleCase_notexistCase() throws Exception{
		publication3.terminate();
		DB.addCitation(1001, 1009);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addCitation_IllagleCase_reversedTimeCase() throws Exception{
		publication3.terminate();
		DB.addCitation(1003, 1001);
	}
	
	@Test
	public void authorCitationIndex_leagleCase1() throws Exception{
		assertEquals(3, DB.authorCitationIndex("B. Wang"), 0.0001);
	}
	
	@Test
	public void authorCitationIndex_leagleCase2() throws Exception{
		DB.addAsPublication(publication7);
		assertEquals(4, DB.authorCitationIndex("B. Wang"), 0.0001);
	}
	
	@Test (expected =IllegalAuthorException.class )
	public void authorCitationIndex_illeagleCase() throws Exception{
		DB.authorCitationIndex("Wang");
	}
	
	@Test
	public void findDirIndirCites_case1() throws Exception{
		DB.addCitation(1001, 1003);
		DB.addCitation(1002, 1003);
		DB.addCitation(1003, 1005);
		DB.addCitation(1003, 1006);
		DB.addCitation(1004, 1006);
		Set<Publication> citeSet = DB.findDirIndirCites(1005);
		assertTrue(citeSet.size() == 3);
		assertTrue(citeSet.contains(publication1));
		assertTrue(citeSet.contains(publication2));
		assertTrue(citeSet.contains(publication3));
		assertFalse(citeSet.contains(publication4));
	}
	
	@Test
	public void findDirIndirCites_case2() throws Exception{
		DB.addCitation(1001, 1003);
		DB.addCitation(1002, 1003);
		DB.addCitation(1003, 1005);
		DB.addCitation(1003, 1006);
		DB.addCitation(1004, 1006);
		Set<Publication> citeSet = DB.findDirIndirCites(1006);
		assertTrue(citeSet.size() == 4);
		assertTrue(citeSet.contains(publication1));
		assertTrue(citeSet.contains(publication2));
		assertTrue(citeSet.contains(publication3));
		assertTrue(citeSet.contains(publication4));
	}
	
	@Test
	public void getCurrentIncrementID_TestCase1(){
		assertTrue(ReferenceDataBase.getCurrentIncrementID() == 1007);
	}
	
	@Test
	public void setCurrentIncrementID_TestCase2() throws Exception{
		ReferenceDataBase.setCurrentIncrementID(1000);
		assertTrue(ReferenceDataBase.getCurrentIncrementID() == 1000);
	}
	
	@Test(expected = IllegalIncrementIDException.class)
	public void setCurrentIncrementID_IlleagleCase() throws Exception{
		ReferenceDataBase.setCurrentIncrementID(-1000);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
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
	*/
}