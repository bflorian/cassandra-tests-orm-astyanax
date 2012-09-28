package console

import grails.converters.JSON

/**
 * @author: Bob Florian
 */
class ConsoleController 
{
	def consoleService
	def keyspace = "websites"
	def columnFamilies = [
			Visit:"websites", Action:"websites", WebsiteVisit:"websites",
			Person:"example", Post:"example", Comment:"example"]

	def index()
	{
		[columnFamilies : columnFamilies]
	}

	def executeScript()
	{
		def script = params.script
		def columnFamily = params.columnFamily
		def className = columnFamily.split("_")[0]
		def ks = columnFamilies[className]

		def result = [columnFamilyName: columnFamily]

		try {
			result.output = consoleService.executeScript("import example.*;\nimport websites.*;" + script)
			if (ks && columnFamily) {
				result.columnFamily = consoleService.showColumnFamily(ks, columnFamily)
			}
			scripts.add(0, script)
			scriptIndex = 0
		}
		catch (Exception e) {
			result.error = stackTrace(e)
		}

		render result as JSON
	}

	def showColumnFamily()
	{
		def columnFamily = params.columnFamily
		def className = columnFamily.split("_")[0]
		def result = [
				columnFamilyName: columnFamily,
				columnFamily: consoleService.showColumnFamily(columnFamilies[className],columnFamily)
		]
		render result as JSON
	}

	def showSource()
	{
		def name = params.name
		def result = [source: source(name), name: "${name}"]
		render result as JSON
	}

	def prevScript()
	{
		if (scriptIndex < scripts.size()-1) {
			scriptIndex++
			render text: scripts[scriptIndex]
		}
		else {
			render text:  ''
		}
	}

	def nextScript()
	{
		if (scriptIndex > 0) {
			scriptIndex--
			render text: scripts[scriptIndex]
		}
		else {
			render text:  ''
		}
	}

	private stackTrace(e)
	{
		def sw = new StringWriter()
		e.printStackTrace(new PrintWriter(sw))
		return sw.toString()
	}

	private source(name)
	{
		def file = new File("grails-app/cassandra/${columnFamilies[name]}/${name}.groovy")
		return file.text
	}

	static scriptIndex = 0;
	static scripts = []
}
