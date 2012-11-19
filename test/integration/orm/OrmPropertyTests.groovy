package orm

/**
 * @author: Bob Florian
 */
class OrmPropertyTests extends GroovyTestCase
{
	static GUID = UUID.randomUUID()

	void testExplorer()
	{
		def car = new Car(
				uuid:  "${GUID}-1",
				make:  "Ford",
				model: "Explorer",
				color:  "Black",
				year:  2011,
				engineType:  EngineType.GASOLINE
		)
		car.save()

		assertEquals "Ford", car.make
		assertEquals "Explorer", car.model
		assertEquals "Black", car.color
		assertEquals 2011, car.year
		assertEquals EngineType.GASOLINE, car.engineType

		car = Car.get("${GUID}-1")
		car.insert(color: "White")
		assertEquals "White", Car.get("${GUID}-1").color


		car = Car.get("${GUID}-1")
		car.insert(color: null)
		assertNull Car.get("${GUID}-1").color


		car = Car.get("${GUID}-1")
		car.model = null
		car.color = "Red"
		car.save()

		car = Car.get("${GUID}-1")
		assertEquals "Red", car.color
		assertNull car.model

	}

}
