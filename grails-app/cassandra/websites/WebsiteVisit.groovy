package websites

/**
 * @author: Bob Florian
 */
class WebsiteVisit
{

	UUID visitId
	UUID visitorId
	String siteId
	Date occurTime              // Time of HTTP request (msec since 1/1/1970)
	String refUrl               // Value of Referer HTTP header param
	String refClass             // Paid | Organic
	String refType              // Direct | Search | Directory | Social | Other Website
	String refName              // Name of know sites (i.e. Google, Bing) or domain name
	String refKeyword           // Search expression (set for refType == Search only)
    String refPage
	String pageUrl              // URL of the requested page (i.e. document.URL)
	String pageTitle            // Title of the requested page (i.e. document.title) or the URL if title not set
	Boolean newVisitor = true   // True for first visit by this visitor, otherwise false
	Boolean bounced = true      // True if no subsequent page views occured within session timeout
	Long timeOnSite = 0L        // Occur time of last action during this visit site during that session
	Integer totalActions = 1    // Total number of page views and posts for the visit

	String refCtype = "Organic" // Product name (ReachSearch | ReachDispay | ReachRemarketing | ReachCast | Organic (for non-reachlocal))
	String refPubName = "none"  // Publisher name (derived from sub-campaign ID) or 'none' for non-reachlocal

	static cassandraMapping = [
			unindexedPrimaryKey: 'visitId',

			explicitIndexes: [
					['siteId'],
					['visitorId'],
					['refType'],
					['siteId','refClass','refType'],
			],

			counters: [
					[findBy:['siteId'], groupBy: ['occurTime']],
					[findBy: ['siteId'], groupBy: ['occurTime','refClass','refCtype','refType','newVisitor','bounced']],
					[findBy: ['siteId'], groupBy: ['occurTime','refClass','refCtype','refType','newVisitor','bounced','refPubName','refName']],
					[findBy: ['siteId'], groupBy: ['occurTime','refClass','refCtype','refType','newVisitor','bounced','refPubName','refName','refKeyword']],
					[findBy: ['siteId'], groupBy: ['occurTime','refClass','refCtype','refType','newVisitor','bounced','refPubName','refName','refPage']],
					[findBy: ['siteId'], groupBy: ['occurTime','refClass','refCtype','refType','newVisitor','bounced','pageUrl']],
					[findBy: ['siteId'], groupBy: ['occurTime','refClass','refCtype','refType','newVisitor','bounced','pageTitle']],

					[findBy: ['siteId'], groupBy: ['occurTime']],
					[findBy: ['siteId'], groupBy: ['occurTime','newVisitor']],
					[findBy: ['siteId'], groupBy: ['occurTime','bounced']]
			],

			keySpace: "websites"
	]
}

