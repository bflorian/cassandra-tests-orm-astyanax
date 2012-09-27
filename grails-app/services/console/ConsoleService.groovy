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
		def sb = new StringBuilder()
		astyanaxService.keyspace(keyspace).prepareQuery(name)
				.getKeyRange(null,null,'0','0',maxRows)
				.withColumnRange(new RangeBuilder().setMaxSize(maxColumns).build())
				.execute()
				.result.each{row ->

			sb << "${row.key} =>\n"
			row.columns.each {col ->
				try {
					sb << "    ${col.name} => '${col.stringValue}'\n"
				}
				catch (Exception ex) {
					sb << "    ${col.name} => ${col.longValue}\n"
				}
			}
		}
		return sb.toString()
	}
}
