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
        //file = new File("etc/data/browns-arlington-honda.json")
		def json = new JsonSlurper().parse(new FileReader(file))
        def t0 = System.currentTimeMillis()
		json.each {
			new WebsiteVisit(
					siteId:  "SITE1",
					visitId: it.visitId.toUUID(),
					visitorId: it.visitorId.toUUID(),
					occurTime: ISO_TS.parse(it.occurTime),
					refUrl: it.refUrl,
					refClass: it.scid ? "Paid" : "Organic",
					refType: it.refType,
					refName: it.refName,
                    refPage:  it.refPage,
					refKeyword: it.refKeyword,
                    refCtype: it.refCtype,
                    refPubName: it.refPubName,
                    pageUrl:  it.pageUrl,
                    pageTitle: it.pageTitle,
                    newVisitor: it.newVisitor,
					bounced: it.bounced as Boolean,
					timeOnSite: it.timeOnSite.toLong(),
					totalActions: it.totalActions.toInteger()
			).save(nocheck: true)
		}
        def num = json.size()
        def elapsed = System.currentTimeMillis() - t0
        println "Inserted $num records in $elapsed msec, ${(num / (elapsed / 1000.0)).toInteger()} rec/sec, ${elapsed / num} msec/rec"
	}
}
