package com.reachlocal.grails.plugins.cassandra.test.orm

/**
 * @author: Bob Florian
 */
class LogEntry 
{
	UUID uuid
	String message

	static cassandraMapping = [
			primaryKey: 'uuid',
			timeToLive: 10,
			keySpace: "orm_test"
	]
}
