package physicalgraph.model

class Device
{
	UUID id
	String name
	Hub hub
	String deviceNetworkId
	DeviceType deviceType

	static belongsTo = [hub: Hub]

	static cassandraMapping = [
		primaryKey: "id",
		keySpace: "orm_things"
	]

	String toString() { name }
}
