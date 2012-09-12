package demo

/**
 * @author: Bob Florian
 */
class Person 
{
	String username
	String firstName
	String lastName
	List posts
	Set friends

	static hasMany = [posts: Post, friends: Person]

	static cassandraMapping = [
			primaryKey: 'username',
			keySpace: "demo"
	]

	Boolean isFriendOf(Person person)
	{
		friendsCount(start: person.id, finish: person.id) > 0
	}
}
