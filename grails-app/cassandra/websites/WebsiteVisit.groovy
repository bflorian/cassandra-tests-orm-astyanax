package websites

/**
 * @author: Bob Florian
 */
class WebsiteVisit
{

	UUID visitId
	UUID visitorId
	String siteId
	Date occurTime
	String refUrl            // Value of Referer HTTP header param
	String refClass          // ReachLocal | Organic
	String refType           // Direct | Search | Directory | Social | Other Website
	String refName           // Name of know sites (i.e. Google, Bing) or domain name
	String refKeyword        // Search expression (set for refType == Search only)
	Boolean bounced = true   // True if no subsequent page views occured within session timeout
	Long timeOnSite = 0L     // Occur time of last action during this visit site during that session
	Integer totalActions = 1 // Total number of page views and posts for the visit

	static hasMany = [actions: WebsiteAction]

	static cassandraMapping = [
			unindexedPrimaryKey: 'visitId',

			explicitIndexes: [
					['siteId'],
					['visitorId'],
					['refClass'],
					['refType']
			],

			counters: [
					[findBy:['siteId'], groupBy: ['occurTime']],
					[findBy:['siteId'], groupBy: ['occurTime','refClass','refType']],
					[findBy:['siteId'], groupBy: ['occurTime','refClass','refType','refName']],
					[findBy:['siteId'], groupBy: ['occurTime','refClass','refType','refName','refKeyword']]
			],

			keySpace: "websites"
	]
}

