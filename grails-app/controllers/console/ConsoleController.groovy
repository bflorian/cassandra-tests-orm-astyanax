package console

import grails.converters.JSON

/**
 * @author: Bob Florian
 */
class ConsoleController 
{
	def consoleService
	def keyspace = "websites"
	def columnFamilies = ["Visit":"", "Action":""]

	def index()
	{
		[columnFamilies : columnFamilies]
	}

	def executeScript()
	{
		def script = params.script
		def columnFamily = params.columnFamily

		def result = [columnFamilyName: columnFamily]

		try {
			result.output = consoleService.executeScript(script)
			if (columnFamily) {
				result.columnFamily = consoleService.showColumnFamily(keyspace,columnFamily)
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
		def result = [
				columnFamilyName: columnFamily,
				columnFamily: consoleService.showColumnFamily(keyspace,columnFamily)
		]
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

	static scriptIndex = 0;
	static scripts = []
}
