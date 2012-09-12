/*
 * Copyright 2011 ReachLocal Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reachlocal.grails.plugins.cassandra.test

import com.reachlocal.grails.plugins.cassandra.test.orm.Household
import com.reachlocal.grails.plugins.cassandra.test.orm.Student
import com.reachlocal.grails.plugins.cassandra.test.orm.Course
import com.reachlocal.grails.plugins.cassandra.mapping.OrmUtility
import com.reachlocal.grails.plugins.cassandra.test.orm.School

/**
 * @author: Bob Florian
 */
class OrmPrimaryKeyTests extends GroovyTestCase
{
	void testGet()
	{
		println '\n--- new Household(emailAddress: "ormpk1@localhost.com").save() ---'
		new Household(emailAddress: "ormpk1@localhost.com").save()
		def obj = Household.get("ormpk1@localhost.com")
		assertEquals("ormpk1@localhost.com", obj.emailAddress)

		println '\n--- new Household(emailAddress: "ormpk2@localhost.com").save() ---'
		new Household(emailAddress: "ormpk2@localhost.com").save()
		def list = Household.list()
		assertTrue(list.size() > 1)
		assertTrue(list.emailAddress.contains("ormpk1@localhost.com"))
		assertTrue(list.emailAddress.contains("ormpk2@localhost.com"))

		println "\n--- new Household(emailAddress: \"ormpk3@localhost.com\").save() ---"
		new Household(emailAddress: "ormpk3@localhost.com").save()
		def list1 = Household.list()
		assertTrue(list1.size() > 2)
		def list2 = Household.list([max:2])
		assertTrue(list2.size() == 2)

		println '\n--- Household.get("ormpk2@localhost.com") ---'
		def obj2 = Household.get("ormpk2@localhost.com")
		assertEquals("ormpk2@localhost.com", obj2.emailAddress)
	}
	

}
