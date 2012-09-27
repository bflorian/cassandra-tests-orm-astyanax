package websites

/**
 * @author: Bob Florian
 */
class Visit 
{
	UUID uuid
	String visitorId
	Date occurTime
	String refType
	String refName
	Boolean bounced = true
	Long timeOnSite = 0L
	Integer totalActions = 1

	static hasMany = [actions: Action]

	static cassandraMapping = [
			primaryKey: 'uuid',

			explicitIndexes: [
					['visitorId'],
					['refType']
			],

			counters: [
					[groupBy: ['occurTime']],
					[groupBy: ['occurTime','refType','refName']],
					[findBy: ['visitorId'], groupBy: ['occurTime','refType','refName']]
			],

			keySpace: "websites"
	]
}
