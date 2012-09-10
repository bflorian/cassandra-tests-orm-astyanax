package docs.expandos

/**
 * @author: Bob Florian
 */
class Event 
{
	UUID uuid
	String accountId
	String eventType
	Map payload

	static cassandraMapping = [
	        primaryKey: "uuid",
			explicitIndexes: ["accountId",["accountId","eventType"],["accountId","eventType","source"]],
			expandoMap: "payload",

			keySpace: "docs_expandos"
	]
}
