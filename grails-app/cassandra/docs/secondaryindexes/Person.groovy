package docs.secondaryindexes

/**
 * @author: Bob Florian
 */
class Person
{
	String username
	String state
	String country
	String gender

	static cassandraMapping = [
			primaryKey: 'username',
			secondaryIndexes: ["gender","country", "state"],

			keySpace: "docs_secondaryindexes"
	]
}