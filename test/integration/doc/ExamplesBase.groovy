package doc

/**
 * @author: Bob Florian
 */
class ExamplesBase 
{
	def astyanaxService

	def processFile(filename, imports, firstLine, map=[:])
	{
		def results = []
		def inCode = false
		def script = []
		def scripts = []
		def file = new File(filename)
		file.eachLine{line ->
			def trim = line.trim()
			if (trim == "{code}") {
				if (inCode) {
					inCode = false
					if (script[0]?.startsWith(firstLine) || firstLine == "ALL") {
						scripts << script.join("\n")
					}
					script = []
				}
				else {
					inCode = true
				}
			}
			else if (inCode) {
				script << line
			}
		}

		scripts.each {
			def result = runScript(it, imports, map)
			results << result
		}
		return results
	}

	private runScript(script, imports, map)
	{
		def fullScript = imports.join("\n") + "\n" + script
		println "script:\n$fullScript"
		def binding = new Binding(map)
		def shell = new GroovyShell(this.class.classLoader, binding)
		def result = shell.evaluate(fullScript)
		println "result:\n$result"
		return result
	}
}
