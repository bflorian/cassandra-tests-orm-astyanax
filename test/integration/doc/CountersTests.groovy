package doc

import org.junit.Test
import static org.junit.Assert.*
import docs.counters.*
import java.text.SimpleDateFormat

/**
 * @author: Bob Florian
 */
class CountersTests extends ExamplesBase
{
	static document = "../cassandra-orm/src/docs/guide/2.4 Counters.gdoc"
	static imports = ["import docs.counters.*"]

	void testSetUp()
	{
		new Visit(siteName: "www.siteone.com", referrerType: "Search", referrerName: "Google", occurTime: df.parse("2011-12-25")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Search", referrerName: "Bing", occurTime: df.parse("2012-01-01")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Search", referrerName: "Google", occurTime: df.parse("2012-01-12")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Search", referrerName: "Google", occurTime: df.parse("2012-02-10")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Search", referrerName: "Google", occurTime: df.parse("2012-02-12")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Search", referrerName: "Bing", occurTime: df.parse("2012-02-15")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Search", referrerName: "Google", occurTime: df.parse("2012-02-16")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Search", referrerName: "Bing", occurTime: df.parse("2012-02-17")).save()

		new Visit(siteName: "www.siteone.com", referrerType: "Social", referrerName: "Facebook", occurTime: tf.parse("2011-12-24 22:00")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Social", referrerName: "Twitter", occurTime: df.parse("2012-01-05")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Social", referrerName: "Twitter", occurTime: df.parse("2012-01-13")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Social", referrerName: "Facebook", occurTime: df.parse("2012-02-12")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Social", referrerName: "Twitter", occurTime: df.parse("2012-02-22")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Social", referrerName: "Facebook", occurTime: df.parse("2012-02-25")).save()

		new Visit(siteName: "www.siteone.com", referrerType: "Direct", referrerName: "", occurTime: df.parse("2011-12-25")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Direct", referrerName: "", occurTime: df.parse("2012-01-01")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Direct", referrerName: "", occurTime: df.parse("2012-02-12")).save()
		new Visit(siteName: "www.siteone.com", referrerType: "Direct", referrerName: "", occurTime: df.parse("2012-02-13")).save()

		for (i in 1..2) {
			new Visit(siteName: "www.sitetwo.com", referrerType: "Search", referrerName: "Google", occurTime: tf.parse("2011-12-24 23:00")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Search", referrerName: "Bing", occurTime: df.parse("2012-01-01")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Search", referrerName: "Google", occurTime: df.parse("2012-01-12")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Search", referrerName: "Google", occurTime: df.parse("2012-02-10")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Search", referrerName: "Google", occurTime: df.parse("2012-02-16")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Search", referrerName: "Bing", occurTime: df.parse("2012-02-17")).save()

			new Visit(siteName: "www.sitetwo.com", referrerType: "Social", referrerName: "Facebook", occurTime: df.parse("2011-12-25")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Social", referrerName: "Twitter", occurTime: df.parse("2012-01-05")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Social", referrerName: "Twitter", occurTime: df.parse("2012-02-22")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Social", referrerName: "Facebook", occurTime: df.parse("2012-02-25")).save()

			new Visit(siteName: "www.sitetwo.com", referrerType: "Direct", referrerName: "", occurTime: df.parse("2011-12-25")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Direct", referrerName: "", occurTime: df.parse("2012-01-01")).save()
			new Visit(siteName: "www.sitetwo.com", referrerType: "Direct", referrerName: "", occurTime: df.parse("2012-02-12")).save()

		}
	}

	void testGetCountsGroupBySiteName()
	{
		def result = processFile(document, imports, "Visit.getCounts(groupBy: 'siteName')")
		assertEquals 1, result.size()
		assertEquals 2, result[0].size()
	}

	void testGetCountsGroupByOccurTime()
	{
		def result = processFile(document, imports, "Visit.getCountsGroupByOccurTime()")
		assertEquals 1, result.size()
		assertEquals 15, result[0].size()
	}

	void testGetCountsGroupByOccurTimeMonth()
	{
		def result = processFile(document, imports, "Visit.getCountsGroupByOccurTime(grain: Calendar.DAY_OF_MONTH)")
		assertEquals 1, result.size()
		assertEquals 2, result[0].size()
	}

	void testGetCountsGroupByOccurTimeAndSiteName()
	{
		def result = processFile(document, imports, "Visit.getCountsGroupByOccurTimeAndSiteName(grain: Calendar.MONTH)")
		assertEquals 1, result.size()
		assertEquals 3, result[0].size()
	}

	void testGetCountsWhere()
	{
		def result = processFile(document, imports, "Visit.getCounts(where: [siteName: 'www.siteone.com'], groupBy: 'occurTime', grain: Calendar.MONTH)")
		assertEquals 1, result.size()
		assertEquals 3, result[0].size()
	}

	void testGetCountsBySiteNameGroupByOccurTimeAndReferrerType()
	{
		def result = processFile(document, imports, "Visit.getCountsBySiteNameGroupByOccurTimeAndReferrerType")
		assertEquals 1, result.size()
		assertEquals 3, result[0].size()
	}

	void testGetCountsBySiteNameGroupByOccurTimeAndReferrerName()
	{
		def result = processFile(document, imports, "Visit.getCountsBySiteNameGroupByOccurTimeAndReferrerName")
		assertEquals 1, result.size()
		assertEquals 3, result[0].size()
	}

	void testTimeZone()
	{
		def result = processFile(document, imports, "Visit.getCountsGroupByOccurTime(grain: Calendar.DAY_OF_MONTH, timeZone: TimeZone.getDefault())")
		assertEquals 1, result.size()
		assertEquals 15, result[0].size()
	}

	void testFill()
	{
		def result = processFile(document, imports, "Visit.getCountsGroupByOccurTime(grain: Calendar.DAY_OF_MONTH, fill: true)")
		assertEquals 1, result.size()
		assertEquals 63, result[0].size()
	}

	void testStartOnly()
	{
		def result = processFile(document, imports, "Visit.getCountsGroupByOccurTime(fill: true, grain: Calendar.DAY_OF_MONTH, start")
		assertEquals 1, result.size()
		assertEquals 16, result[0].size()
	}

	void testStartFinish()
	{
		def result = processFile(document, imports, "Visit.getCountsGroupByOccurTime(grain: Calendar.DAY_OF_MONTH, fill: true,")
		assertEquals 1, result.size()
		assertEquals 6, result[0].size()
	}

	static df = new SimpleDateFormat("yyyy-MM-dd")
	static tf = new SimpleDateFormat("yyyy-MM-dd HH:mm")
}
