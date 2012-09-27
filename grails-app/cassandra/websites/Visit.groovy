package websites

/**
 * @author: Bob Florian
 */
class Visit 
{
	UUID uuid
	String visitorId
	String refType
	String refName
	Date occurTime

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
