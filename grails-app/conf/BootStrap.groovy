class BootStrap
{
    def init = { servletContext ->
		if (System.getProperty('dsePath')) {
			log.info "init"
			runCliScript("etc/schema/drop-schema.txt")
			runCliScript("etc/schema/create-schema.txt")
			runCqlScript("etc/schema/drop-schema-things.cql")
			runCqlScript("etc/schema/create-schema-things.cql")
			log.info "init completed"
		}
    }

    def destroy = {
    }

	static private runCliScript(script) {
		def dsePath = System.getProperty('dsePath') ?: '/usr/local'
		def cassandraCli = "$dsePath/bin/cassandra-cli -h 192.168.111.221 -f"

		def cmd = "$cassandraCli $script"
		def p = cmd.execute()
		def stderr = p.err?.text
		def stdout = p.text
		if (stderr) {
			System.err.println stderr
		} else {
			System.out.println stdout
		}
	}

	static private runCqlScript(script) {
		def dsePath = System.getProperty('dsePath')
		def cassandraCli = "$dsePath/bin/cqlsh 192.168.111.221 -f"

		def cmd = "$cassandraCli $script"
		def p = cmd.execute()
		def stderr = p.err?.text
		def stdout = p.text
		if (stderr) {
			System.err.println stderr
		} else {
			System.out.println stdout
		}
	}
}
