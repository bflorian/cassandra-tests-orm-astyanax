package physicalgraph.contact.cassandra

class IncidentMessage
{
	UUID id
	Incident incident
	String contactId
	String text
	Date date

	static belongsTo = [incident: Incident]

	static cassandraMapping = [
		unindexedPrimaryKey: "id",
		explicitIndexes: [
			["contactId"]
		],
		keySpace: "smartthings"
	]
}

