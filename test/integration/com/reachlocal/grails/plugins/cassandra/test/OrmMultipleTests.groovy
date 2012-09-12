package com.reachlocal.grails.plugins.cassandra.test

import com.reachlocal.grails.plugins.cassandra.test.orm.Household
import com.reachlocal.grails.plugins.cassandra.test.orm.School
import com.reachlocal.grails.plugins.cassandra.test.orm.Course
import com.reachlocal.grails.plugins.cassandra.test.orm.Student
import com.reachlocal.grails.plugins.cassandra.test.orm.Transcript
import com.reachlocal.grails.plugins.cassandra.test.orm.Grade

/**
 * @author: Bob Florian
 */
class OrmMultipleTests extends GroovyTestCase
{

	void testCompositeIndex()
	{
		def household = new Household(emailAddress: "jones@local.com", city: "Hometown", state: "MD", zip: "21020")
		def school = new School(name: "Little Red Schoolhouse", minGrade: 1, maxGrade: 12)
		def course1 = new Course(title: "Reading", description: "One of the three basics")
		def course2 = new Course(title:  "Writing", description: "Another of the tree basics")
		def student1 = new Student(firstName: "Johnny", lastName: "Jones", age: 9)

		school.save()
		household.save().addToStudents(student1)

		student1.addToCourses(course1).addToStudents(student1)
		student1.addToCourses(course2).addToStudents(student1)
		
		def transcript1 = new Transcript(school: school, student: student1)
		school.addToTranscripts(transcript1)

		def grade1 = new Grade(date: new Date(), pointValue: 3.5, course: course1)
		def grade2 = new Grade(date: new Date(), pointValue: 3.0, course: course1)
		transcript1.addToGrades(grade1)
		transcript1.addToGrades(grade2)
	}
}
