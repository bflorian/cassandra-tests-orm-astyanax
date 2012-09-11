package demo

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
	Set likedBy
	static hasMany = [comments: Comment, likedBy: Person]

	static cassandraMapping = [
			primaryKey: 'uuid',
			counters: [
					[groupBy: ['occurTime']]
			],
			keySpace: "demo"
	]
}
