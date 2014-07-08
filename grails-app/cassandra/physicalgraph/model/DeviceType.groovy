package physicalgraph.model

class DeviceType
{
	UUID id
	String name

	static cassandraMapping = [
		primaryKey: "id",
		keySpace: "orm_things"
	]

	String toString() { name }
}
