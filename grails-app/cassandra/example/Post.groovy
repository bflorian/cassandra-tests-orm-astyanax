package example

/**
 * @author: Bob Florian
 */
class Post
{
	UUID uuid
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
			keySpace: "example"
	]


	Boolean isLikedBy(person)
	{
		likedByCount(start:person.id, finish:person.id) > 0
	}
}
