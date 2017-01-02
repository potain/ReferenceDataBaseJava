import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import publications.*;
import referenceDB.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ReferenceDataBaseTest.class, PublicationTest.class, JournalArticleTest.class,
	BookTest.class, ConferencePaperTest.class  })
public class AllTests {
}