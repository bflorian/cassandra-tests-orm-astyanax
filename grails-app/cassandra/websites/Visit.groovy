package websites

/**
 * @author: Bob Florian
 */
class Visit 
{
	UUID uuid
	String visitorId
	Date occurTime
	RefType refType
	String refName
	Boolean bounced = true
	Long timeOnSite = 0L

	static hasMany = [actions: Action]

	static cassandraMapping = [
			primaryKey: 'uuid',
			explicitIndexes: [
					['visitorId'],
					['refType']
			],

			counters: [
					[groupBy: ['occurTime','refType','refName']],
			],
			keySpace: "websites"
	]
}

enum RefType {DIRECT, SEARCH, SOCIAL, DIRECTORY, OTHER}
