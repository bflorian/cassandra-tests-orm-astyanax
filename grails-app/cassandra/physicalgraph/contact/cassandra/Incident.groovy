package physicalgraph.contact.cassandra

class Incident
{
	UUID id
	String title
	String installedSmartAppId
	String rootSmartAppId
	String locationId
	Boolean active
	Date date
	List escalations

	static hasMany = [escalations: Escalation, messages: IncidentMessage]

	static cassandraMapping = [
		unindexedPrimaryKey: "id",
		explicitIndexes: [
			["installedSmartAppId", "active"],
			["rootSmartAppId", "active"],
			["locationId", "active"]
		],
		keySpace: "smartthings"
	]

	static transients = ["encodedId", "acceptedBy", "closedBy", "accepted", "closed"]

	List getAcceptedBy() {
		def contactIds = escalations.findAll{it.acceptedAt}.collect{it.contactId} as Set
		contactIds.collect{Contact.get(it)}
	}

	Boolean getAccepted() {
		acceptedAt != null
	}

	Boolean getClosed() {
		closedAt != null
	}

	String getEncodedId() {
		id.toString()//id.toUrlSafeString()
	}
}
