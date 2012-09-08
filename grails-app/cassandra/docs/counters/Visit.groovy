package docs.counters

/**
 * @author: Bob Florian
 */
class Visit 
{
	UUID uuid
	String siteName
	String referrerType
	String referrerName
	Date occurTime

	static cassandraMapping = [
			primaryKey: 'uuid',
			counters: [
					[groupBy: ['siteName']],
					[groupBy: ['occurTime']],
					[groupBy: ['occurTime','siteName']],
					[findBy: ['siteName'], groupBy: ['occurTime']],
					[findBy: ['siteName'], groupBy: ['occurTime','referrerType','referrerName']],
			],

			keySpace: "docs_counters"
	]
}
