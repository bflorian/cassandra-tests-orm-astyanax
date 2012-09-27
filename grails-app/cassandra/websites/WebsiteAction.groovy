package websites

/**
 * @author: Bob Florian
 */
class WebsiteAction 
{
	UUID actionId            // Time-based UUID unique to this action
	UUID visitId             // Time-based UUID unique to this visit (i.e. sequence of actions in a session)
	UUID visitorId           // Globally unique identified of this visitor (i.e. person, as far as can be determined)
	Date occurTime
	String pageTitle
	String method
	Map postData
	WebsiteVisit visit
	static belongsTo = [visit: WebsiteVisit]

	static cassandraMapping = [
			primaryKey: 'uuid',

			explicitIndexes: [
					['method'],
					['visitId']
			],

			counters: [
					[groupBy: ['occurTime']],
					[groupBy: ['occurTime','pageTitle']],
					[findBy: ['method'], groupBy: ['occurTime','pageTitle']]
			],

			keySpace: "websites"
	]
}
