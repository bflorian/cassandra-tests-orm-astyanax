package physicalgraph.model

class Location
{
	UUID id
	String name
	BigDecimal latitude
	BigDecimal longitude
	Mode mode

	static hasMany = [hubs: Hub, modes: Mode]

	static cassandraMapping = [
		primaryKey: "id",
		keySpace: "orm_things"
	]

	String toString() { name }
}
