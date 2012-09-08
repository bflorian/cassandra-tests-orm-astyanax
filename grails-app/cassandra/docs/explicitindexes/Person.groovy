package docs.explicitindexes

/**
 * @author: Bob Florian
 */
class Person
{
	String username
	String emailAddress
	String phone
	String city
	String state

	static cassandraMapping = [
			primaryKey: 'username',
			explicitIndexes: ["emailAddress", "phone", "state", ["state","city"]],

			keySpace: "docs_explicitindexes"
	]
}