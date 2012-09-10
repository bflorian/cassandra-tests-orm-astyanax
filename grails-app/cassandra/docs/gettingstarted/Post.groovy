package docs.gettingstarted

/**
 * @author: Bob Florian
 */
class Post 
{
	UUID uuid
	String title
	String text
	Date occurTime

	Person user
	static belongsTo = [user: Person]

	static cassandraMapping = [
			primaryKey: 'uuid',
			counters: [
					[groupBy: ['occurTime']],
					[findBy:  ['user'], groupBy:['occurTime']]
			],

			cluster: "gettingstarted"
	]
}
