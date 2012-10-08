package console

import java.text.DecimalFormat

/**
 * @author: Bob Florian
 */
class ScriptSet 
{
	public ScriptSet(String sessionId)
	{
		directory = new File(root, sessionId)
		directory.mkdirs()
		index = 0
		size = 0
	}

	Integer getSize()
	{
		return size
	}

	void add(script)
	{
		file(getSize()) << script
		index = size
		size++
	}

	def current()
	{
		return file(index).text
	}

	def previous()
	{
		if (index > 0) {
			index--
			return current()
		}
		else {
			return ""
		}
	}

	def next()
	{
		if (index < size - 1) {
			index++
			return current()
		}
		else {
			return ""
		}
	}

	private file(index)
	{
		String name = "script_${NF.format(index)}"
		new File(directory, name)
	}

	private File root = new File("target/scripts")
	private File directory
	private Integer index
	private Integer size

	static NF = new DecimalFormat("000")
}
