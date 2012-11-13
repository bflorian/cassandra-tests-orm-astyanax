package misc

/**
 * @author: Bob Florian
 */
class Like
{
	Long postId
	Long userId
	Date occurTime
	static cassandraMapping = [
			primaryKey: ['postId','userId'],

			keySpace: "misc"
	]
}