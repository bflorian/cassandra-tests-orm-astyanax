package doc

import org.junit.Test
import docs.secondaryindexes.*

/**
 * @author: Bob Florian
 */
class SecondaryIndexesTests  extends ExamplesBase
{
	static document = "../cassandra-orm/src/docs/guide/2.2 Secondary Indexes.gdoc"
	static imports = ["import docs.secondaryindexes.*"]

	@Test
	void testSetup()
	{
		new Person(username: "sally", state: "Maryland", country: "USA", gender: "Female").save()
		new Person(username: "sue", state: "New York", country: "USA", gender: "Female").save()
		new Person(username: "bill", state: "New York", country: "USA", gender: "Male").save()
		new Person(username: "bob", state: "Maine", country: "USA", gender: "Male").save()
		new Person(username: "betty", state: "California", country: "USA", gender: "Femail").save()
		new Person(username: "pete", state: "Maine", country: "USA", gender: "Male").save()
	}

	@Test
	void testGormQueries()
	{
		def result = processFile(document, imports, "Person.findAllByGender")
	}

}
