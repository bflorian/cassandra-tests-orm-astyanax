package doc

import org.junit.Test
import static org.junit.Assert.*
import docs.expandos.*

/**
 * @author: Bob Florian
 */
class ExpandosTests extends ExamplesBase
{
	static document = "../cassandra-orm/src/docs/guide/2.5 Expando Classes.gdoc"
	static imports = ["import docs.expandos.*"]

	@Test
	void testAll()
	{
		testSave()
		testGetProperty()
		testSetupRemainder()
		testFindAllByAccountIdAndEventTypeAndSource()
	}

	private testSave()
	{
		def result = processFile(document, imports, "new Event(accountId:")
		assertEquals 1, result.size()
		assertTrue result[0] instanceof Event
	}

	private testGetProperty()
	{
		def result = processFile(document, imports, "Event.list().each { println it.destination }")
		assertEquals 1, result.size()
	}

	private testSetupRemainder()
	{
		new Event(accountId: "A001", eventType: "PhoneCall", source: "+13015551212", destination: "+14085551212").save()
		new Event(accountId: "A001", eventType: "PhoneCall", source: "+14105551212", destination: "+14155551212").save()
		new Event(accountId: "A001", eventType: "PhoneCall", source: "+13015551212", destination: "+17035551212").save()
		new Event(accountId: "A001", eventType: "EmailMsg", source: "jdoe@any.com", destination: "me@myplace.com").save()
		new Event(accountId: "A001", eventType: "EmailMsg", source: "jdoe@any.com", destination: "me@otherplace.com").save()
		new Event(accountId: "A001", eventType: "EmailMsg", source: "ssmith@any.com", destination: "me@myplace.com").save()

		new Event(accountId: "A002", eventType: "PhoneCall", source: "+13015551212", destination: "+14155551212").save()
		new Event(accountId: "A002", eventType: "PhoneCall", source: "+13015551212", destination: "+17035551212").save()
		new Event(accountId: "A002", eventType: "EmailMsg", source: "jdoe@any.com", destination: "me@myplace.com").save()
		new Event(accountId: "A002", eventType: "EmailMsg", source: "jdoe@any.com", destination: "me@otherplace.com").save()
	}

	private testFindAllByAccountIdAndEventTypeAndSource()
	{
		def result = processFile(document, imports, "Event.findAllByAccountIdAndEventTypeAndSource")
		assertEquals 1, result.size()
		assertEquals 3, result[0].size()
	}
}
