package console

import org.junit.Test
import groovy.json.JsonSlurper
import websites.WebsiteVisit
import java.text.SimpleDateFormat

/**
 * @author: Bob Florian
 */
class WebsiteVisitTests 
{
	@Test
	void testLoad()
	{
		def ISO_TS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		ISO_TS.setTimeZone(TimeZone.getTimeZone("GMT"))

		def file = new File("etc/data/visits.json")
		def json = new JsonSlurper().parse(new FileReader(file))
		json.each {
			new WebsiteVisit(
					visitId: it.visitId.toUUID(),
					visitorId: it.visitorId.toUUID(),
					occurTime: ISO_TS.parse(it.occurTime),
					refUrl: it.refUrl,
					refClass: it.scid ? "ReachLocal" : "Organic",
					refType: it.refType,
					refName: it.refName,
					refKeyword: it.refKeyword,
					bounced: it.bounced as Boolean,
					timeOnSite: it.timeOnSite.toLong(),
					totalActions: it.totalActions.toInteger()
			).save()
		}
	}
}
