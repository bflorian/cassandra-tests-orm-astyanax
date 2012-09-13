package orm

import org.junit.Test
import static org.junit.Assert.*
import orm.Car
import com.netflix.astyanax.model.ConsistencyLevel

/**
 * @author: Bob Florian
 */
class OrmConsistencyLevelTests 
{
	@Test
	void testSave()
	{
		new Car(uuid: "CLT-0001", make: "Honda", model: "Accord", color: "Gray", year: 2006).save(consistencyLevel: "CL_ONE")
		new Car(uuid: "CLT-0001", make: "Honda", model: "Accord", color: "Gray", year: 2006).save(consistencyLevel: ConsistencyLevel.CL_ONE)
	}

	@Test
	void testSaveInvalid()
	{
		def exceptionOccured = false
		try {
			new Car(uuid: "CLT-0001", make: "Honda", model: "Accord", color: "Gray", year: 2006).save(consistencyLevel: "CL_ONETWO")
		}
		catch (Exception e) {
			exceptionOccured = true
		}
		assertTrue exceptionOccured
	}
}
