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

package orm

import orm.Household

/**
 * @author: Bob Florian
 */
class OrmSecondaryIndexTests extends GroovyTestCase
{
	void testFindAllBy1()
	{
		new Household(emailAddress: "ormsi1@localhost.com", city:"Olney", state: "MD", zip:"20832").save()
		new Household(emailAddress: "ormsi2@localhost.com", city:"Olney", state: "MD", zip:"20834").save()
		new Household(emailAddress: "ormsi3@localhost.com", city:"Reston", state: "VA", zip:"20191").save()
		new Household(emailAddress: "ormsi4@localhost.com", city:"Brookeville", state: "MD", zip:"20834").save()
		new Household(emailAddress: "ormsi5@localhost.com", city:"Olney", state: "IL", zip:"20834").save()
		def list = Household.findAllByCity("Olney")
		assertEquals(3, list.size())
	}

	void testFindAllBy2()
	{
		def list = Household.findAllByCityAndState("Olney", "MD")
		assertEquals(2, list.size())
	}

	void testFindBy1()
	{
		def obj = Household.findByCity("Olney")
		assertEquals("Olney", obj.city)
	}

	void testFindBy1A()
	{
		def obj = Household.findByCity("Reston")
		assertEquals("Reston", obj.city)
		assertEquals("VA", obj.state)
		assertEquals("20191", obj.zip)
	}

	void testFindAllBy3()
	{
		def list = Household.findAllByCityAndStateAndZip("Olney", "MD", "20834")
		assertEquals(1, list.size())
	}

	void testFindAllLimit()
	{
		def list = Household.findAllByCity("Olney", [max:2])
		assertEquals(2, list.size())
	}

	void testChangeFindAll()
	{
		def obj = Household.get("ormsi5@localhost.com")
		obj.state = "MD"
		obj.save()

		assertEquals(3, Household.findAllByCityAndState("Olney", "MD").size())
		assertEquals(0, Household.findAllByState("IL").size())

		obj.state = "IL"
		obj.save()

		assertEquals(2, Household.findAllByCityAndState("Olney", "MD").size())
		assertEquals(1, Household.findAllByState("IL").size())
	}
}
