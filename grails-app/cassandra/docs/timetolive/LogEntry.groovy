package docs.timetolive

/**
 * @author: Bob Florian
 */
class LogEntry 
{
	UUID uuid
	Date occurTime
	String messageText

	def cassandraMapping = [
	        primaryKey: "uuid",
			timeToLive: 90,

			keySpace: "docs_timetolive"
	]
}
