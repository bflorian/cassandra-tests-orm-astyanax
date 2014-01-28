package console

import grails.converters.JSON

/**
 * @author: Bob Florian
 */
class ConsoleController 
{
	def consoleService
	def keyspace = "websites"
	def columnFamilies =
		[
			//Visit:[keyspace:"websites", package: "websites"], Action:[keyspace:"websites", package: "websites"],
			//Event:[keyspace:"smartthings", package: "physicalgraph.event.cassandra"],
			//DeviceState:[keyspace:"smartthings", package: "physicalgraph.device.cassandra"]
			//Person:"example", Post:"example", Comment:"example"
			//Like:"misc"
			Visit:[keyspace:"docs_counters", package: "docs.counters"],
			Household:[keyspace:"orm_test", package: "orm"],
			Student:[keyspace:"orm_test", package: "orm"]
		]

	def index()
	{
		[columnFamilies : columnFamilies]
	}

	def executeScript()
	{
		def script = params.script
		def columnFamily = params.columnFamily
		def className = columnFamily.split("_")[0]
		def ks = columnFamilies[className].keyspace

		def result = [columnFamilyName: columnFamily]

		try {
			def imports = columnFamilies.collect{"import ${it.value.package}.*;"}.join("\n")
			result.output = consoleService.executeScript(imports + "\n" + script)
			if (ks && columnFamily) {
				result.columnFamily = session.displayType == "data" ?
					consoleService.showColumnFamily(ks, columnFamily)  :
					source(className)
			}
			scripts.add(script)
		}
		catch (Exception e) {
			result.error = stackTrace(e)
		}

		render result as JSON
	}

	def showColumnFamily()
	{
		session.displayType = "data"
		def columnFamily = params.columnFamily
		def className = columnFamily.split("_")[0]
		def result = [
				columnFamilyName: columnFamily,
				columnFamily: consoleService.showColumnFamily(columnFamilies[className].keyspace,columnFamily)
		]
		render result as JSON
	}

	def showSource()
	{
		session.displayType = "source"
		def name = params.name
		def result = [source: source(name), name: "${name}"]
		render result as JSON
	}

	def prevScript()
	{
		render text: scripts.previous()
	}

	def nextScript()
	{
		render text: scripts.next()
	}

	private stackTrace(e)
	{
		def sw = new StringWriter()
		e.printStackTrace(new PrintWriter(sw))
		return sw.toString()
	}

	private source(name)
	{
		def path = columnFamilies[name].package.replaceAll("\\.","/")
		def file = new File("grails-app/cassandra/${path}/${name}.groovy")
		return file.text
	}

	private ScriptSet getScripts()
	{
		def result = session.getAttribute(SCRIPT_KEY)
		if (!result) {
			result = new ScriptSet(session.id)
			session.setAttribute(SCRIPT_KEY, result)
		}
		return result
	}

	static final String SCRIPT_KEY = "ScriptSet"
}
