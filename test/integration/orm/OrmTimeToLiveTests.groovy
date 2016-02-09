package orm

import orm.Car
import orm.LogEntry
import orm.Person

/**
 * @author: Bob Florian
 */
class OrmTimeToLiveTests extends GroovyTestCase
{
	static GUID = UUID.randomUUID()

	def astyanaxService

	void testAll() {
		item_TtlFieldExpiration()
		item_TtlFieldExpirationInsert()
		item_TtlObjectExpiration()
		item_TtlFieldParameter()
		item_TtlObjectParameter()
		item_TtlInsertParameter()
		item_TtlInsertParameterMap()
	}

	void item_TtlFieldExpiration()
	{
		new Car(
				uuid:  "${GUID}-1",
				make:  "Ford",
				model: "Explorer",
				color:  "Black",
				year:  2011,
				price: 35000
		).save()

		def car = Car.get("${GUID}-1")
		assertEquals "Ford", car.make
		assertEquals 35000, car.price

		Thread.sleep(11000L)
		car = Car.get("${GUID}-1")
		assertEquals "Ford", car.make
		assertNull car.price
	}

	void item_TtlFieldExpirationInsert()
	{
		def car = Car.get("${GUID}-1")
		assertEquals "Ford", car.make
		assertNull car.price

		car.insert([price: 38000], [ttl:5])

		car = Car.get("${GUID}-1")
		assertEquals "Ford", car.make
		assertEquals 38000, car.price

		Thread.sleep(6000L)
		car = Car.get("${GUID}-1")
		assertEquals "Ford", car.make
		assertNull car.price
	}

	void item_TtlObjectExpiration()
	{
		def uuid = UUID.randomUUID()
		new LogEntry(uuid: uuid, message: "Log message text").save()

		def entry = LogEntry.get(uuid)
		assertEquals "Log message text", entry.message
		astyanaxService.showColumnFamilies(["LogEntry", "LogEntry_IDX"], "orm_test")

		Thread.sleep(11000L)
		entry = LogEntry.get(uuid)
		assertNull entry
		astyanaxService.showColumnFamilies(["LogEntry", "LogEntry_IDX"], "orm_test")
	}

	void item_TtlFieldParameter()
	{
		new Person(
				emailAddress:  "ttl@person.com" ,
				firstName:  "Test",
				lastName:  "User"
		).save(ttl: [lastName:  10])

		def p = Person.get("ttl@person.com")
		assertEquals "Test", p.firstName
		assertEquals "User", p.lastName

		Thread.sleep(11000L)
		p = Person.get("ttl@person.com")
		assertEquals "Test", p.firstName
		assertNull p.lastName
	}

	void item_TtlObjectParameter()
	{
		new Person(
				emailAddress:  "ttl2@person.com" ,
				firstName:  "Test2",
				lastName:  "User2"
		).save(ttl:  10)

		def p = Person.get("ttl2@person.com")
		assertEquals "Test2", p.firstName
		assertEquals "User2", p.lastName

		Thread.sleep(11000L)
		p = Person.get("ttl2@person.com")
		assertNull p
	}

	void item_TtlInsertParameter()
	{
		def p = Person.get("ttl@person.com")
		p.insert([firstName:  "TestTTL"], [ttl:5])

		p = Person.get("ttl@person.com")
		assertEquals "TestTTL", p.firstName

		Thread.sleep(6000L)
		p = Person.get("ttl@person.com")
		assertNull p.firstName
	}

	void item_TtlInsertParameterMap()
	{
		def p = Person.get("ttl@person.com")
		p.insert([firstName:  "TestTTL2", lastName:  "UserTTL2"], [ttl: [firstName:  6, lastName:  3]])

		p = Person.get("ttl@person.com")
		assertEquals "TestTTL2", p.firstName
		assertEquals "UserTTL2", p.lastName

		Thread.sleep(4000L)
		p = Person.get("ttl@person.com")
		assertEquals "TestTTL2", p.firstName
		assertNull p.lastName

		Thread.sleep(4000L)
		p = Person.get("ttl@person.com")
		assertNull p.firstName
		assertNull p.lastName
	}
}
