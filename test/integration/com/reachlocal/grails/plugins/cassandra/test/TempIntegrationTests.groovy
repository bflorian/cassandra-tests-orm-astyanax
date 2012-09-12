package com.reachlocal.grails.plugins.cassandra.test

import com.reachlocal.grails.plugins.cassandra.mapping.OrmUtility
import com.reachlocal.grails.plugins.cassandra.test.orm.*

/**
 * @author: Bob Florian
 */
class TempIntegrationTests extends GroovyTestCase
{
	void testFindAllBy1()
	{
		def resp = Car.findAllByMakeAndModel("Ford","Explorer")
		//def resp = Car.findAllWhere(make: "Ford", model: ["Explorer","Mustang"], year: [2011,2000,1964])
		//println resp
	}
}
