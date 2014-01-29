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

import orm.Student

/**
 * @author: Bob Florian
 */
class OrmOneToManyTests extends GroovyTestCase
{
	void testAll()
	{
		//void testSaveCascade()
		One:
		{
			def h = new Household(
					emailAddress: "orm_one_to_many1@localhost.com"
			)
			h.save().addToStudents(new Student(firstName:"John", lastName: "Doe", age: 10))

			def h1 = Household.get("orm_one_to_many1@localhost.com")
			assertEquals(1, h1.students.size())
			assertEquals("John", h1.students[0].firstName)
			assertEquals("orm_one_to_many1@localhost.com", h1.students[0].household.emailAddress)
		}

		//void testAdd1()
		Two:
		{
			def h1 = Household.get("orm_one_to_many1@localhost.com")
			h1.addToStudents(new Student(firstName:"Jane", lastName: "Doe", age: 12))

			assertEquals(2, Household.get("orm_one_to_many1@localhost.com").students.size())
			Household.get("orm_one_to_many1@localhost.com").students.each {s ->
				assertEquals("orm_one_to_many1@localhost.com", s.household.emailAddress)
			}
		}

		//void testAddLimit()
		Three:
		{
			def h1 = Household.get("orm_one_to_many1@localhost.com")
			h1.addToStudents(new Student(firstName:"Sandy", lastName: "Beach", age: 14))

			assertEquals(3, Household.get("orm_one_to_many1@localhost.com").students.size())
			assertEquals(2, Household.get("orm_one_to_many1@localhost.com").students(max:2).size())
		}

		//void testCount()
		Four:
		{
			def household = Household.get("orm_one_to_many1@localhost.com")
			household.addToStudents(new Student(firstName:"Jimmy", lastName: "Jones", age: 15))

			//household = Household.get("orm_one_to_many1@localhost.com")
			def students = household.students
			assertEquals(4, household.studentsCount())
			assertEquals(3, household.students(start: students[1].uuid).size())
			assertEquals(2, household.students(start: students[1].uuid, finish: students[2].uuid).size())
		}

		//void testRemove()
		Five:
		{
			def h1 = Household.get("orm_one_to_many1@localhost.com")
			def s1 = h1.students[0]
			h1.removeFromStudents(s1)

			assertEquals 3, h1.students.size()
			assertNull s1.household

			def h2 = Household.get("orm_one_to_many1@localhost.com")
			assertEquals 3, h2.students.size()

		}

		//void testDelete()
		Six:
		{
			def h1 = Household.get("orm_one_to_many1@localhost.com")
			def students = h1.students.toList()
			assertEquals 3, students.size()

			students[2].delete()

			def h2 = Household.get("orm_one_to_many1@localhost.com")
			assertEquals 2, h2.students.size()
		}
	}
}
