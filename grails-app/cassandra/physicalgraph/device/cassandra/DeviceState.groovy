package physicalgraph.device.cassandra

import groovy.json.JsonSlurper

import java.text.SimpleDateFormat

class DeviceState {
	UUID uuid
	String deviceId
	String name
	String value
	String unit
	Date date
	String type

	static cassandraMapping = [
		unindexedPrimaryKey: 'uuid',
		explicitIndexes : [
			['deviceId','name']
			// TODO - additional indexes?
			//,['deviceId','name','value'],
		],
		keySpace: "smartthings"
	]

	static transients = ["dateCreated","rawDateCreated","isoDate","formatter","stringValue","integerValue","longValue","floatValue","doubleValue","numberValue","numericValue","xyzValue","dateValue","jsonValue"]

	DeviceState() {
		super()
	}

	DeviceState(event) {
		super()
		uuid = UUID.timeUUID(event.date.time)
		name = event.name
		value = event.value
		unit = event.unit
		date = formatter.parse(event.dateCreated)
		type = event.type
		deviceId = event.deviceId
	}

	String getDateCreated() {
		formatter.format(date)
	}

	Date getRawDateCreated() {
		date
	}

	String getIsoDate() {
		dateCreated
	}

	Boolean isPhysical() {
		type == "physical"
	}

	Boolean isDigital() {
		type == "digital"
	}

	def getFormatter() {
		// TODO: Replace this and its callers with whatever date code Scott had mentioned he was writing.
		def formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		formatter.timeZone = TimeZone.getTimeZone("GMT")
		formatter
	}

	String getStringValue() {
		value
	}

	Integer getIntegerValue() {
		try {
			value as Integer
		}
		catch (NumberFormatException e) {
			(value as Double) as Integer
		}
	}

	Long getLongValue() {
		try {
			value as Long
		}
		catch (NumberFormatException e) {
			(value as Double) as Long
		}
	}

	Float getFloatValue() {
		value as Float
	}

	Double getDoubleValue() {
		value as Double
	}

	Number getNumberValue() {
		value ? new BigDecimal(value) : null
	}

	Number getNumericValue() {
		value ? new BigDecimal(value) : null
	}

	Map<String, Number> getXyzValue() {
		def a = value.split(",").collect{it ? new BigDecimal(it) : null}
		[x: a[0], y: a[1], z: a[2]]
	}

	Date getDateValue() {
		if (value.isNumber()) {
			new Date(longValue)
		}
		else {
			formatter.parse value
		}
	}

	def getJsonValue() {
		new JsonSlurper().parseText(value)
	}
}