package websites

/**
 * @author: Bob Florian
 */
class Action 
{
	UUID uuid
	Date occurTime
	String pageTitle
	String method
	Map postData
	Visit visit
	static belongsTo = [visit: Visit]

	static cassandraMapping = [
			primaryKey: 'uuid',

			explicitIndexes: [
					['method']
			],

			counters: [
					[groupBy: ['occurTime']],
					[groupBy: ['occurTime','pageTitle']],
					[findBy: ['method'], groupBy: ['occurTime','pageTitle']]
			],

			keySpace: "websites"
	]
}
