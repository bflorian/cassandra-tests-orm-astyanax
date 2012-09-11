package docs.timetolive

/**
 * @author: Bob Florian
 */
class LogEntry 
{
	UUID uuid
	Date occurTime
	String messageText

	static cassandraMapping = [
	        primaryKey: "uuid",
			timeToLive: 90,

			keySpace: "docs_timetolive"
	]
}
