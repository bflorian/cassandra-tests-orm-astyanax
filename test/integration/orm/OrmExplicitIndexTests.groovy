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
class OrmExplicitIndexTests extends GroovyTestCase
{
	void testFindAllBy1()
	{
		new Household(emailAddress: "ormse1@localhost.com", phone:"301-570-1111", city: "Pleasanton").save()
		new Household(emailAddress: "ormse2@localhost.com", phone:"301-570-1234", city: "Pleasanton").save()
		new Household(emailAddress: "ormse3@localhost.com", phone:"301-570-1111", city: "Pleasanton").save()
		new Household(emailAddress: "ormse4@localhost.com", phone:"301-555-1212", city: "Ellicott City").save()
		new Household(emailAddress: "ormse5@localhost.com", phone:"301-555-1111", city: "Ellicott City").save()
		def list = Household.findAllByPhone("301-570-1111")
		assertEquals(2, list.size())
		list.each {
			assertTrue it instanceof Household
		}
	}

	void testFindAllByColumn()
	{
		def list = Household.findAllByPhone("301-570-1111", [column: 'emailAddress'])
		assertEquals(2, list.size())
		list.each {
			assertTrue it instanceof String
		}
	}

	void testFindAllByColumns()
	{
		def list = Household.findAllByPhone("301-570-1111", [columns: ['emailAddress','city']])
		assertEquals(2, list.size())
		list.each {
			assertTrue it instanceof Map
			it.each {k,c ->
				assertTrue c instanceof String
			}
		}
	}

	void testFindAllByRawColumn()
	{
		def list = Household.findAllByPhone("301-570-1111", [rawColumn: 'emailAddress'])
		assertEquals(2, list.size())
		list.each {
			assertTrue it instanceof byte[]
		}
	}

	void testFindAllByRawColumns()
	{
		def list = Household.findAllByPhone("301-570-1111", [rawColumns: ['emailAddress','city']])
		assertEquals(2, list.size())
		list.each {
			assertTrue it instanceof Map
			it.each {k,c ->
				assertTrue c instanceof byte[]
			}
		}
	}
	
	void testFindAllByLimit()
	{
		def list = Household.findAllByPhone("301-570-1111", [max:1])
		assertEquals(1, list.size())
	}

	void testFindAllBy2()
	{
		def list = Household.findAllByCity("Pleasanton")
		assertEquals(3, list.size())
	}

	void testFindBy1()
	{
		def obj = Household.findByPhone("301-570-1111")
		assertEquals("301-570-1111", obj.phone)
	}

	void testChangeFindAll()
	{
		def obj = Household.get("ormse1@localhost.com")
		obj.phone = "301-570-2222"
		obj.save()

		assertEquals(1, Household.findAllByPhone("301-570-2222").size())
		assertEquals(1, Household.findAllByPhone("301-570-1111").size())

		obj.phone = "301-570-1111"
		obj.save()
		assertEquals(0, Household.findAllByPhone("301-570-2222").size())
		assertEquals(2, Household.findAllByPhone("301-570-1111").size())
	}

	void testCountAll1()
	{
		def count = Household.countByPhone("301-570-1111")
		assertEquals 2, count
	}

	void testCountBy2()
	{
		def count = Household.countByCity("Pleasanton")
		assertEquals 3, count
	}
}
