package example

/**
 * @author: Bob Florian
 */
class Comment
{
	UUID uuid
	String text
	Date occurTime
	Person person
	Post post
	static belongsTo = [post: Post]

	Set likedBy
	static hasMany = [likedBy: Person]

	static cassandraMapping = [
			primaryKey: 'uuid',
			counters: [
					[findBy: ['post'], groupBy:['occurTime']],
					[findBy: ['person'], groupBy:['occurTime']]
			],
			keySpace: "example"
	]

	Boolean isLikedBy(person)
	{
		likedByCount(start:person.id, finish:person.id) > 0
	}
}
