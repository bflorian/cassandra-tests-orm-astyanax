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


/**
 * @author: Bob Florian
 */
class OrmManyToManyTests extends GroovyTestCase
{
	void testSaveCascade()
	{
		def s = new Student(
				firstName:"Albert",
				lastName: "Einstein",
				age: 10
		).save()

		s.addToCourses(new Course(title: "Introductory Physics 110", description: "Classical mechanics")).addToStudents(s)
		s.addToCourses(new Course(title: "Biology 101", description: "Intro biology for non-majors")).addToStudents(s)
		assertEquals(2, s.courses.size())

		s.courses.each {c ->
			assertEquals(1, c.students.size())
			assertEquals(s.uuid, c.students[0].uuid)
		}

		def c = Course.get("Introductory Physics 110")
		assertEquals 1, c.students.size()
		assertEquals "Einstein", c.students[0].lastName
	}

	void testAddTo()
	{
		def s = Student.list().find{it.lastName == "Einstein"}
		def c = new Course(title: "U.S. History 101", description: "History of the United States of America")
		s.addToCourses(c)
		c.addToStudents(s)

		def s1 = Student.get(s.uuid)
		assertEquals 3, s1.courses.size()

		def c1 = Course.get("U.S. History 101")
		assertEquals 1, c1.students.size()
		assertEquals "Einstein", c1.students[0].lastName
	}

	void testSaveCascade2()
	{
		def course = Course.get("Introductory Physics 110")

		def s = new Student(
				firstName:"Niels",
				lastName: "Bohr",
				age: 18,
				courses: [course]
		).save()
		s.addToCourses(course)
		course.addToStudents(s)

		def s1 = Student.get(s.uuid)
		assertEquals(1, s1.courses.size())
		s1.courses.each {c ->
			assertEquals(2, c.students.size())
			assertTrue(c.students.uuid.contains(s1.uuid))
		}

		def c1 = Course.get("Introductory Physics 110")
		assertEquals 2, c1.students.size()
	}

	void testRemove()
	{
		def s = Student.list().find{it.lastName == "Einstein"}
		assertEquals 3, s.courses.size()

		def course = Course.get("Introductory Physics 110")
		assertEquals 2, course.students.size()

		s.removeFromCourses(course)

		assertEquals 2, s.courses.size()
		assertEquals 1, course.students.size()

		assertEquals 2, Student.list().find{it.lastName == "Einstein"}.courses.size()
		assertEquals 1, Course.get("Introductory Physics 110").students.size()

	}
}