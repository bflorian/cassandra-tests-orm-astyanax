package doc

import org.junit.Test
import static org.junit.Assert.*
import docs.explicitindexes.*

/**
 * @author: Bob Florian
 */
class ExplicitIndexesTests extends ExamplesBase
{
	static document = "../cassandra-orm/src/docs/guide/2.3 Explicit Indexes.gdoc"
	static imports = ["import docs.explicitindexes.*"]
	static initialized = false

	void setup()
	{
		if (!initialized) {
			new Person(username: "sally", emailAddress: "sally@foo.com", phone: "+13015551212", state: "Maryland", city: "Baltimore").save()
			new Person(username: "sue", emailAddress: "sue@foo.com", phone: "+17035551111", state: "Virginia", city: "Reston").save()
			new Person(username: "bill", emailAddress: "bill@foo.com", phone: "+17035551112", state: "Virginia", city: "Fairfax").save()
			new Person(username: "bob", emailAddress: "bob@foo.com", phone: "+14085551111", state: "California", city: "San Francisco").save()
			new Person(username: "mary", emailAddress: "mary@foo.com", phone: "+14085551111", state: "California", city: "San Francisco").save()
			new Person(username: "betty", emailAddress: "betty@foo.com", phone: "+18035551111", state: "California", city: "Los Angeles").save()
			new Person(username: "pete", emailAddress: "pete@foo.com", phone: "+13015551112", state: "Maryland", city: "Frederick").save()
			new Person(username: "sam", emailAddress: "sam@foo.com", phone: "+13015551113", state: "Maryland", city: "Annapolis").save()
			new Person(username: "john", emailAddress: "john@foo.com", phone: "+13015551114", state: "Maryland", city: "Annapolis").save()
			new Person(username: "fred", emailAddress: "fred@foo.com", phone: "+13015551212", state: "Maryland", city: "Rockville").save()
			initialized = true
		}
	}

	@Test
	void testFindByQueries()
	{
		setup()
		def result = processFile(document, imports, "Person.findByPhone")
		assertEquals 1, result[0].size()
	}

	@Test
	void testFindWhereQueries()
	{
		setup()
		def result = processFile(document, imports, "Person.findWhere")
		assertEquals 1, result[0].size()
	}

	@Test
	void testFindByCollection()
	{
		setup()
		def result = processFile(document, imports, "Person.findAllByStateAndCity")
		assertEquals 4, result[0].size()
	}

	@Test
	void testCountBy()
	{
		setup()
		def result = processFile(document, imports, "Person.countByState")
		assertEquals 2, result[0]
	}
}
