class BootStrap {

    def init = { servletContext ->
		if (System.getProperty('dsePath')) {
			log.info "init"
			runCqlScript("etc/drop-schema.txt")
			runCqlScript("etc/create-schema.txt")
			log.info "init completed"
		}
    }

    def destroy = {
    }

	static private runCqlScript(script) {
		def dsePath = System.getProperty('dsePath')
		def cassandraCli = "$dsePath/bin/cassandra-cli -h localhost -f"

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
