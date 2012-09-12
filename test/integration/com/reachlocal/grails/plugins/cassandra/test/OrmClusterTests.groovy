package com.reachlocal.grails.plugins.cassandra.test

import org.junit.Test;
import static org.junit.Assert.*
import com.reachlocal.grails.plugins.cassandra.test.orm.Car
import com.reachlocal.grails.plugins.cassandra.test.orm.Household
import com.reachlocal.grails.plugins.cassandra.test.orm.Student
import com.reachlocal.grails.plugins.cassandra.test.orm.Person
import java.text.SimpleDateFormat

/**
 * @author: Bob Florian
 */
class OrmClusterTests 
{
	@Test
	void testSave()
	{
		if (System.getProperty("cluster2.host")) {
			def c1 = new Car(uuid: "CT-0001", make: "Honda", model: "Accord", color: "Gray", year: 2006)
			c1.save()

			def c2 = new Car(uuid: "CT-0002", make: "Dodge", model: "Durango", color: "Blue", year: 2000)
			c2.save(cluster: "test2")

			assertNull Car.get("CT-0002")
			assertEquals "Blue", Car.get("CT-0002", [cluster: "test2"]).color
		}
	}

	@Test
	void testAddToHasOne()
	{
		if (System.getProperty("cluster2.host")) {
			def p = new Person(emailAddress: "orm_cluster0@localhost.com", firstName:  "Jed", lastName: "Clampet").save(cluster: "test2")
			p.firstCar = new Car(uuid: "CT-0003", make: "For", model: "T", color: "Black", year: 1917)
			p.save(cascade: true)
			assertNull Car.get("CT-0003")
			assertEquals "Black", Car.get("CT-0003", [cluster: "test2"]).color
		}
	}

	@Test
	void testAddToHasMany()
	{
		if (System.getProperty("cluster2.host")) {
			def h = new Household(emailAddress: "orm_cluster1@localhost.com").save(cluster: "test2")
			h.addToStudents(new Student(uuid:  "vickyvic0001", firstName:"Vicky", lastName: "Victoria", age: 16))
			assertNull Student.get("vickyvic0001")

			def s = Student.get("vickyvic0001", [cluster: "test2"])
			assertEquals 16, s.age
			assertEquals "test2", s.cassandraCluster
			assertEquals "orm_cluster1@localhost.com", s.household.emailAddress
			assertEquals "test2", s.household.cassandraCluster

			assertEquals 1, Household.get("orm_cluster1@localhost.com", [cluster: "test2"]).students.size()
		}
	}

	@Test
	void testFindWhere()
	{
		if (System.getProperty("cluster2.host")) {
			hondas()
			def c = Car.findWhere(make: 'Honda', model: 'CRV', year:  '2008', [cluster: "test2"])
			assertEquals "White", c.color
		}
	}

	@Test
	void testFindAllWhere()
	{
		if (System.getProperty("cluster2.host")) {
			def c = Car.findAllWhere(make: 'Honda', model: 'Civic', [cluster: "test2"])
			assertEquals 2, c.size()
		}
	}

	@Test
	void testFindBy()
	{
		if (System.getProperty("cluster2.host")) {
			def c = Car.findByMakeAndModelAndYear("Honda","Accord",2006, [cluster: "test2"])
			assertEquals "Gray", c.color
		}
	}

	@Test
	void testFindAllBy()
	{
		if (System.getProperty("cluster2.host")) {
			def c = Car.findAllByMakeAndModel("Honda","Civic", [cluster: "test2"])
			assertEquals 2, c.size()
		}
	}

	@Test
	void testCountBy()
	{
		if (System.getProperty("cluster2.host")) {
			def c = Car.countByMakeAndModel("Honda","Civic", [cluster: "test2"])
			assertEquals 2, c
		}
	}

	@Test
	void testGetCounts()
	{
		if (System.getProperty("cluster2.host")) {
			persons()
			def c = Person.getCounts(groupBy: "state", cluster: "test2")
			println c
			assertEquals 3, c.size()
		}
	}

	@Test
	void testGetCountTotal()
	{
		if (System.getProperty("cluster2.host")) {
			def c = Person.getCountTotal(where: [gender: "M"], cluster: "test2")
			assertEquals 5, c
		}
	}

	@Test
	void testGetCountsGroupBy()
	{
		if (System.getProperty("cluster2.host")) {
			def c = Person.getCountsByGenderGroupByState("F", [cluster: "test2"])
			println c
			assertEquals 3, c.size()
			assertEquals 2, c.CA
			assertEquals 2, c.MD
			assertEquals 1, c.VA
		}
	}

	private void hondas()
	{
		new Car(uuid: "CT-0011", make: "Honda", model: "Accord", color: "Gray", year: 2006).save(cluster: "test2")
		new Car(uuid: "CT-0021", make: "Honda", model: "Civic", color: "Silver", year: 2007).save(cluster: "test2")
		new Car(uuid: "CT-0022", make: "Honda", model: "Civic", color: "Green", year: 2009).save(cluster: "test2")
		new Car(uuid: "CT-0031", make: "Honda", model: "CRV", color: "White", year: 2008).save(cluster: "test2")
		new Car(uuid: "CT-0041", make: "Honda", model: "Pilot", color: "Black", year: 2006).save(cluster: "test2")
		new Car(uuid: "CT-0051", make: "Honda", model: "CRV", color: "Red", year: 2008).save()
	}

	private void persons()
	{
		new Person(emailAddress: "orm_cluster1@local.com", state: "MD", city: "Olney", gender: "F", birthDate: FMT.parse('1985-09-14')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster2@local.com", state: "MD", city: "Olney", gender: "M", birthDate: FMT.parse('1986-03-14')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster3@local.com", state: "MD", city: "Rockville", gender: "F", birthDate: FMT.parse('1985-07-14')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster4@local.com", state: "MD", city: "Baltimore", gender: "M", birthDate: FMT.parse('1984-02-14')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster5@local.com", state: "CA", city: "Pleasanton", gender: "F", birthDate: FMT.parse('1984-11-14')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster6@local.com", state: "CA", city: "San Francisco", gender: "M", birthDate: FMT.parse('1984-07-14')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster7@local.com", state: "CA", city: "San Francisco", gender: "F", birthDate: FMT.parse('1991-09-10')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster8@local.com", state: "VA", city: "Reston", gender: "M", birthDate: FMT.parse('1992-06-14')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster9@local.com", state: "VA", city: "Reston", gender: "F", birthDate: FMT.parse('1992-09-14')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster0@local.com", state: "VA", city: "Fairfax", gender: "M", birthDate: FMT.parse('1995-12-14')).save(cluster: "test2")
		new Person(emailAddress: "orm_cluster11@local.com", state: "VA", city: "Reston", gender: "F", birthDate: FMT.parse('1992-09-11')).save()
		new Person(emailAddress: "orm_cluster12@local.com", state: "VA", city: "Fairfax", gender: "M", birthDate: FMT.parse('1995-12-11')).save()
	}

	static protected FMT = new SimpleDateFormat("yyyy-MM-dd")

	static {
		FMT.setTimeZone(TimeZone.getTimeZone("America/New_York"))
	}
}
