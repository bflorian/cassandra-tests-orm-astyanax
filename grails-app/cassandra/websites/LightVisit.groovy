package websites

/**
 * @author: Bob Florian
 */
class LightVisit 
{
	UUID uuid
	String visitorId
	Date occurTime
	String refClass
	String refType
	String refName
	String refKeyword
	String refUrl
	String pageUrl

	static cassandraMapping = [
			unindexedPrimaryKey: 'uuid',

			//explicitIndexes: [
			//		['visitorId']
			//],

			keySpace: "websites"
	]
}
