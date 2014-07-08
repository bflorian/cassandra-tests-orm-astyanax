package physicalgraph.model

import org.junit.Test

class ThingsLocationTests
{
	@Test
	void testAll() {

		def location = new Location(name: "Home", latitude: 40.0, longitude: -76.0).save()

		["Away", "Day", "Night"].each {
			location.addToModes(new Mode(name: it))
		}

		["House","Barn"].each {
			location.addToHubs(new Hub(name: it, radioId: "00000-$it"))
		}

		location.mode = location.modes.toList().get(0)
		location.save()

		def t1 = new DeviceType(name: "Contact Sensor").save()
		def t2 = new DeviceType(name: "Motion Sensor").save()
		def t3 = new DeviceType(name: "Switch").save()

		def hub1 = location.hubs.find{it.name == "House"}
		[1,2].each {
			hub1.addToDevices(new Device(name: "$hub1.name Contact $it", deviceNetworkId: "000$it", deviceType: t1))
		}
		[1,2,3].each {
			hub1.addToDevices(new Device(name: "$hub1.name Motion $it", deviceNetworkId: "001$it", deviceType: t2))
		}
		[1,2,3].each {
			hub1.addToDevices(new Device(name: "$hub1.name Switch $it", deviceNetworkId: "002$it", deviceType: t3))
		}

		def hub2 = location.hubs.find{it.name == "Barn"}
		[1,2,3].each {
			hub2.addToDevices(new Device(name: "$hub2.name Contact $it", deviceNetworkId: "100$it", deviceType: t1))
		}
		[1,2].each {
			hub2.addToDevices(new Device(name: "$hub2.name Motion $it", deviceNetworkId: "101$it", deviceType: t2))
		}
		[1,2,3,4].each {
			hub2.addToDevices(new Device(name: "$hub2.name Switch $it", deviceNetworkId: "102$it", deviceType: t3))
		}

		println location.name
		println "    modes:"
		location.modes.each {
			println "        $it"
		}
		println "    hubs:"
		location.hubs.each {h ->
			println "        $h ($h.location)"
			println "            devices:"
			h.devices.each {d ->
				println "                $d ($d.deviceType)"
			}
		}
		println ""
		println Hub.get(hub1.id)
		println Hub.get(hub1.id).location
	}
}
