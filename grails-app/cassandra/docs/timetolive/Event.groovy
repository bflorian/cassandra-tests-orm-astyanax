package docs.timetolive

/**
 * @author: Bob Florian
 */
class Event 
{
	UUID uuid
	String accountId
	String eventType
	String ipAddress
	Map payload

	static cassandraMapping = [
			primaryKey: "uuid",
			explicitIndexes: ["accountId",["accountId","eventType"],["accountId","eventType","source"]],
			expandoMap: "payload",
			timeToLive: [ipAddress: 90,transcript: 30],

			keySpace: "docs_timetolive"
	]
}
