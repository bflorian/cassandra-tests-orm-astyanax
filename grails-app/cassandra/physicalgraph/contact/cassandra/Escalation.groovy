package physicalgraph.contact.cassandra

import grails.util.Holders

class Escalation
{
	UUID id
	String installedSmartAppId
	String contactId
	String contactProfileId
	Date date

	Incident incident

	static belongsTo = [incident: Incident]

	static cassandraMapping = [
		unindexedPrimaryKey: "id",
		explicitIndexes: [
			["incident","contactProfileId"]
		],
		keySpace: "smartthings"
	]
}
