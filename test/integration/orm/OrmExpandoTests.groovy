package orm

import orm.Car
import orm.Person

/**
 * @author: Bob Florian
 */
class OrmExpandoTests extends GroovyTestCase
{
	static GUID = UUID.randomUUID()

	void testAll()
	{
		testConstructor()
		testSetter()
		testGet1()
		testGet2()
		testInsertNonNull()
		testInsertNull()
		testSaveNull()
		testMappedObjectIds()
	}

	private testConstructor()
	{
		def car = new Car(
				uuid:  "${GUID}-1",
				make:  "Ford",
				model: "Explorer",
				color:  "Black",
				year:  2011,
				drivetrain: "AWD",
				trimLevel: "XLT"
		)
		assertEquals "Ford", car.make
		assertEquals "AWD", car.drivetrain
		assertEquals "XLT", car.trimLevel

		car.save()
	}

	private testSetter()
	{
		def car = new Car(
				uuid:  "${GUID}-2",
				make:  "Honda",
				model: "Accord",
				color:  "Gray",
				year:  2006
		)
		car.drivetrain = "FWD"
		car.trimLevel = "EX-V6"

		assertEquals "Honda", car.make
		assertEquals "FWD", car.drivetrain
		assertEquals "EX-V6", car.trimLevel

		car.save()

	}

	private testGet1()
	{
		def car = Car.get("${GUID}-1")
		assertEquals "Ford", car.make
		assertEquals "AWD", car.drivetrain
		assertEquals "XLT", car.trimLevel
	}

	private testGet2()
	{
		def car = Car.get("${GUID}-2")
		assertEquals "Honda", car.make
		assertEquals "FWD", car.drivetrain
		assertEquals "EX-V6", car.trimLevel
	}

	private testInsertNonNull()
	{
		def car = Car.get("${GUID}-2")
		car.insert(trimLevel: "EX")

		assertEquals "EX", Car.get("${GUID}-2").trimLevel
	}

	private testInsertNull()
	{
		def car = Car.get("${GUID}-2")
		car.insert(trimLevel: null)

		assertNull Car.get("${GUID}-2").trimLevel
	}


	private testSaveNull()
	{
		def car = Car.get("${GUID}-2")
		assertEquals "FWD", car.drivetrain

		car.drivetrain = null
		car.save()

		assertNull Car.get("${GUID}-2").drivetrain
	}

	private testMappedObjectIds()
	{
		def uuid = "${GUID}-3"
		def c1 = new Car(
				uuid:  uuid,
				make:  "Accura",
				model: "TSX",
				color:  "Red",
				year:  2013,
				engine: "V6"
		)
		def p = new Person(emailAddress: "expando1@florian.org", firstCar: c1, firstName:"Bob", lastName:"Florian", dealerId:"DLR-001")
		p.save(cascade:true)

		def p1 = Person.get("expando1@florian.org")
		assertEquals "DLR-001", p1.data.dealerId
		assertEquals uuid, p1.firstCarId
		assertNull p1.data.firstCarId
	}
}
