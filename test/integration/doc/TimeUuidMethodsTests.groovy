package doc

import org.junit.Test
import static org.junit.Assert.*

/**
 * @author: Bob Florian
 */
class TimeUuidMethodsTests  extends ExamplesBase
{
	static document = "../cassandra-orm/src/docs/guide/1.2 TimeUUID Methods.gdoc"
	static imports = []

	@Test
	void testAll()
	{
		processFile(document, imports, "ALL")
	}
}
