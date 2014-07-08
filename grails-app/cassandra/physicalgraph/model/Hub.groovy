package physicalgraph.model

class Hub
{
	UUID id
	String name
	Location location
	String radioId

	static belongsTo = [location: Location]

	static hasMany = [devices: Device]

	static cassandraMapping = [
		primaryKey: "id",
		keySpace: "orm_things"
	]

	String toString() { name }
}
