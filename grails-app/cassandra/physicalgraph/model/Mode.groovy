package physicalgraph.model

/**
 * User: bflorian
 * Date: 7/8/14
 * Time: 8:52 AM
 * To change this template use File | Settings | File Templates.
 */
class Mode
{
	UUID id
	String name
	Location location

	static belongsTo = [location: Location]

	static cassandraMapping = [
		primaryKey: "id",
		keySpace: "orm_things"
	]

	String toString() { name }
}
