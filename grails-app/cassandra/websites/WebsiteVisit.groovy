package websites

/**
 * @author: Bob Florian
 */
class WebsiteVisit
{

	UUID visitId
	UUID visitorId
	Date occurTime
	String refUrl            // Value of Referer HTTP header param
	String refClass          // ReachLocal | Organic
	String refType           // Direct | Search | Directory | Social | Other Website
	String refName           // Name of know sites (i.e. Google, Bing) or domain name
	String refKeyword        // Search expression (set for refType == Search only)

	static hasMany = [actions: WebsiteAction]

	static cassandraMapping = [
			primaryKey: 'uuid',

			explicitIndexes: [
					['visitorId'],
					['refType']
			],

			counters: [
					[groupBy: ['occurTime']],
					[groupBy: ['occurTime','refClass','refType','refName']],
					[groupBy: ['occurTime','refClass','refType','refName','refKeyword']],
					[groupBy: ['occurTime','refClass','refType','pageTitle']]
			],

			keySpace: "websites"
	]
}

