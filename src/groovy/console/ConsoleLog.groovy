package console

/**
 * @author: Bob Florian
 */
class ConsoleLog 
{
	void print(object)
	{
		buffer << object
	}

	synchronized void println(object)
	{
		buffer << object
		buffer << "\n"
	}

	String toString()
	{
		buffer.toString()
	}

	int size()
	{
		buffer.size()
	}

	private buffer = new StringBuffer()
}
