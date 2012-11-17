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

import orm.Car
import orm.Person

/**
 * @author: Bob Florian
 */
class OrmOneToOneTests extends GroovyTestCase
{
	void testIndividual()
	{
		def c1 = new Car(uuid: "0001", make: "Honda", model: "Accord", color: "Gray", year: 2006)
		c1.save()

		def c2 = new Car(uuid: "0002", make: "Dodge", model: "Durango", color: "Blue", year: 2000)
		c2.save()

		def p = new Person(emailAddress: "bob@florian.org", firstCar: c1, secondCar:c2, firstName:"Bob", lastName:"Florian")
		p.save()

		def p1 = Person.get("bob@florian.org")
		assertEquals c1.uuid, p1.firstCar.uuid
		assertEquals c2.uuid, p1.secondCar.uuid

		def p1a = Person.findByFirstCar(c1)
		assertNotNull p1a
		assertEquals p.emailAddress, p1a.emailAddress
	}

	void testReplaceSave()
	{
		def p = Person.get("bob@florian.org")
		def c3 = new Car(uuid: "0003", make: "Ford", model: "Explorer", color: "Black", year: 2011)
		c3.save()

		p.secondCar = c3
		assertEquals c3.uuid, p.secondCar.uuid

		p.save()

		def p1 = Person.get("bob@florian.org")
		assertEquals "0001", p1.firstCar.uuid
		assertEquals c3.uuid, p1.secondCar.uuid
	}

	void testNullSave()
	{
		def p = Person.get("bob@florian.org")
		p.firstName = "Robert"
		p.secondCar = null

		p.save()

		def p1 = Person.get("bob@florian.org")
		assertNull p1.secondCar
	}

	void testCascadeSave()
	{
		def p = new Person(
				emailAddress: "vicky@florian.org",
				firstCar: Car.get("0001"),
				secondCar: new Car(uuid: "0004", make: "Chrysler", model: "Sebring Convertible", color: "Red", year: 2000)
		)
		p.save(cascade: true)

		def c = Car.get("0004")
		assertNotNull c
		assertEquals "Chrysler", c.make
	}
}
