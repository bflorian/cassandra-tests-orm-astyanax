package docs.gettingstarted

/**
 * @author: Bob Florian
 */
class Person
{
	String username
	String emailAddress
	String firstName
	String lastName
	String country

	List posts
	static hasMany = [posts: Post]

	static cassandraMapping = [
			primaryKey: 'username',
			explicitIndexes: ["emailAddress", "country"],
			counters: [[groupBy: "country"]],

			cluster: "gettingstarted"
	]
}
