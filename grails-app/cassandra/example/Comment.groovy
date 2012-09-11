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

	static cassandraMapping = [
			primaryKey: 'uuid',
			counters: [
					[groupBy: ['post']],
					[findBy:  ['person'], groupBy:['occurTime']]
			],
			keySpace: "example"
	]
}
