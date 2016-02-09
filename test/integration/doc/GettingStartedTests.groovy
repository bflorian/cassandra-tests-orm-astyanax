package doc

import org.junit.Test
import static org.junit.Assert.*
import docs.gettingstarted.Person

/**
 * @author: Bob Florian
 */
class GettingStartedTests extends ExamplesBase
{
	static document = "../cassandra-orm/src/docs/guide/1.1 Getting Started.gdoc"
	static imports = ["import docs.gettingstarted.*"]

	void testAll() {
		testNewPerson()
		testGetPerson()
		testAddToPosts()
		testPrintPosts()
		testReverse3()
		testTitleColumn()
		testPostsCount()
		testGroupByCountry()
		testTimePeriods()
		testPostUser()
	}

	private testNewPerson()
	{
		def results = processFile(document, imports, "new Person(username")
		assertEquals 1, results.size()
		assertTrue results[0] instanceof docs.gettingstarted.Person
	}


	private testGetPerson()
	{
		def result = processFile(document, imports, "def user1 = Person.get")
		assertEquals 1, result.size()
	}


	private testAddToPosts()
	{
		def p = Person.get("jdoe")
		def result = processFile(document, imports, "user.addToPosts(new Post(title: \"Post 1\"", [user: p])
		assertEquals 1, result.size()
		assertEquals 5, p.postsCount()
	}


	private testPrintPosts()
	{
		def result = processFile(document, imports, "user.posts.each", [user: Person.get("jdoe")])
		assertEquals 1, result.size()
	}


	private testReverse3()
	{
		def result = processFile(document, imports, "user.posts(reversed: true", [user: Person.get("jdoe")])
		assertEquals 1, result.size()
	}


	private testTitleColumn()
	{
		def result = processFile(document, imports, "user.posts(column: \"title\").each", [user: Person.get("jdoe")])
		assertEquals 1, result.size()
	}


	private testPostsCount()
	{
		def result = processFile(document, imports, "user.postsCount()", [user: Person.get("jdoe")])
		assertEquals 5, result[0]
	}


	private testGroupByCountry()
	{
		def result = processFile(document, imports, "Person.getCountsGroupByCountry()", [user: Person.get("jdoe")])
		assertEquals 1, result[0].USA
	}


	private testTimePeriods()
	{
		def result = processFile(document, imports, "Post.getCountsGroupByOccurTime()", [user: Person.get("jdoe")])
		assertEquals 1, result.size()
	}


	private testPostUser()
	{
		def result = processFile(document, imports, "Post.list().each {", [user: Person.get("jdoe")])
		assertEquals 1, result.size()
	}
}
