package console

import com.netflix.astyanax.model.ColumnFamily
import com.netflix.astyanax.serializers.StringSerializer
import com.netflix.astyanax.util.RangeBuilder

/**
 * @author: Bob Florian
 */
class ConsoleService 
{
	def grailsApplication
	def astyanaxService

	def executeScript(script) throws Exception
	{
		def consoleOut = new ConsoleLog()
		def consoleErr = new ConsoleLog()
		def shell = new GroovyShell(this.class.classLoader, new Binding(
				ctx: grailsApplication.mainContext,
				out: consoleOut,
				err: consoleErr,
				print: {s -> out.print(s)},
				println: {s -> out.println(s)})
		)

		def result = shell.run(script, "Script", [])

		if (consoleErr.size()) {
			throw new Exception(consoleErr.toString())
		}
		else if (consoleOut.size()) {
			return consoleOut.toString()
		}
		else {return result?.toString() ?: ''
		}
	}

	def showColumnFamily(keyspace, name, maxRows=500, maxColumns=100)
	{
		def cf = astyanaxService.keyspace(keyspace).describeKeyspace().getColumnFamily(name)

		def sb = new StringBuilder()
		astyanaxService.keyspace(keyspace).prepareQuery(name)
				.getKeyRange(null,null,'0','0',maxRows)
				.withColumnRange(new RangeBuilder().setMaxSize(maxColumns).build())
				.execute()
				.result.each{row ->
			def cols = row.columns
			if (cols?.size() > 0) {
				sb << "${rowKey(row, cf)} =>\n"
				cols.each {col ->
					sb << "    ${columnName(col, cf)} => '${columnValue(col, cf)}'\n"
				}
			}
		}
		return sb.toString()
	}

	private rowKey(row, cf) {
		def vc = cf.keyValidationClass
		if (vc.endsWith("UUIDType")) {
			UUID.fromBytes(row.rawKey.array()).toString()
		}
		else {
			row.key
		}
	}
	private columnName(col, cf) {
		def ct = cf.comparatorType
		if (ct.endsWith("UUIDType)")) {
			UUID.fromBytes(col.rawName.array()).toString()
		}
		else {
			col.name
		}
	}

	private columnValue(col, cf) {
		def cdl = cf.columnDefinitionList
		def cd = cdl.find{it.name == col.name}
		def vc = cd?.validationClass ?: cf.defaultValidationClass
		if (vc) {
			def type = vc.split("\\.")[-1]
			switch(type) {
				case "UUIDType":
					return col.UUIDValue
				case "LongType":
				case "CounterColumnType":
					return col.longValue
				case "BooleanType":
					return col.booleanValue
				case "DateType":
					return col.dateValue
				default:
					return col.stringValue
			}
		}
		else {
			return col.stringValue
		}
	}
}
