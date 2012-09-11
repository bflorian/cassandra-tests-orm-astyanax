package example

/**
 * @author: Bob Florian
 */
class Post 
{
	UUID uuid
	String title
	String text
	Date occurTime

	Person person
	static belongsTo = [person: Person]

	List comments
	static hasMany = [comments: Comment]

	static cassandraMapping = [
			primaryKey: 'uuid',
			counters: [
					[groupBy: ['occurTime']],
					[findBy:  ['person'], groupBy:['occurTime']]
			],
			keySpace: "example"
	]
}
