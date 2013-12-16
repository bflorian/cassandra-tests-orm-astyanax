package physicalgraph.event.cassandra

import groovy.json.JsonException
import groovy.json.JsonSlurper
import physicalgraph.event.EventSource

import java.text.SimpleDateFormat

class Event
{	UUID uuid
	String hubId
	String deviceId
	String deviceTypeId
	String commandId
	String locationId
	String smartAppId
	String smartAppVersionId
	String installedSmartAppId
	String userId
	String groupId
	String accountId
	String installedSmartAppParentId
	String description
	String descriptionText
	String linkText
	String name
	String value
	String unit
	Date date
	String dateCreated
	String displayed
	String isStateChange
	String handlerName
	String type
	private EventSource source
	String eventType
	String data
	String viewed

	static cassandraMapping = [
		//primaryKey: 'uuid',  // TODO - TEMPORARY
		unindexedPrimaryKey: 'uuid',
		explicitIndexes : [
			['hubId','displayed'],
			['deviceId','displayed'],
			['locationId','displayed'],
			['installedSmartAppId','displayed'],
			['groupId','displayed'],
			['accountId','displayed'],

			['sourceId'],
			['sourceId', 'name'],
			['sourceId', 'eventType'],
			['locationId', 'eventType']
		],
		secondaryIndexes: ["deviceId","locationId", "eventType", "name", "displayed"],

		counters: [
			[groupBy: ['date','source']],
			[groupBy: ['date','locationId','source']],
			[groupBy: ['date','locationId','eventType']],
			[findBy: ['source'], groupBy: ['date','name','isStateChange']]
		],
		keySpace: "smartthings"
	]

	static transients = ['rawDateCreated', 'dateCreated', 'dateString', 'formatter', 'hub', 'device', 'command', 'location',
		'installedSmartApp', 'displayName', 'stringValue', 'integerValue', 'longValue', 'floatValue', 'doubleValue',
		'numberValue', 'numericValue', 'xyzValue', 'dateValue', 'jsonValue', 'eventSource','sourceId','jsonData',
		'user','userIcon']

	Event() {
		super()
	}

	Date getRawDateCreated() {
		date
	}

	String getDateCreated() {
		if (date) {
			return formatter.format(date)
		}
		else {
			return null
		}
	}

	void setDateCreated(String value) {
		date = formatter.parse value
	}

	String getDateString() {
		formatter.format date
	}

	String getIsoDate() {
		formatter.format date
	}

	static getFormatter() {
		// TODO: Replace this and its callers with whatever date code Scott had mentioned he was writing.
		def formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
		formatter.timeZone = TimeZone.getTimeZone("GMT")
		formatter
	}

	String toString() { description }

	String getUserIcon() {
		def result = null
		if (locationId) {
			result = user?.icon(location)
		}
		result
	}

	Boolean isStateChange()
	{
		return isStateChange == "true"
	}

	Boolean isPhysical() {
		type == "physical"
	}

	Boolean isDigital() {
		type == "digital"
	}

	Boolean wasViewed() {
		viewed == "true"
	}

	String getDisplayName() {
		linkText
	}

	EventSource getSource() {
		if (!source) {
			if (locationId) {
				if (deviceId) {
					if (commandId) {
						if (installedSmartAppId) {
							source =  EventSource.APP_COMMAND
						} else {
							source = EventSource.COMMAND
						}
					} else {
						source = EventSource.DEVICE
					}
				} else if (hubId) {
					source = EventSource.HUB
				} else if (installedSmartAppId) {
					source = EventSource.APP
				} else {
					source = EventSource.LOCATION
				}
			}
		}
		source
	}

	String getEventSource() {
		getSource()
	}

	String getSourceId() {
		String sourceId
		switch (getSource()) {
			case EventSource.APP:
				sourceId = installedSmartAppId
				break
			case EventSource.LOCATION:
				sourceId = locationId
				break
			case EventSource.HUB:
				sourceId = hubId
				break
			default:
				sourceId = deviceId
				break
		}
		sourceId
	}

	void setSource(EventSource value) {
		this.source = value
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

	void setData(String value) {
		data = value
	}

	void setData(Map value) {
		setJsonData(value)
	}

	void setData(Collection value) {
		setJsonData(value)
	}

	String getData() {
		data
	}

	def getJsonData() {
		try {
			data ? new JsonSlurper().parseText(data) : null
		}
		catch (JsonException e) {
			null
		}
	}

	void setJsonData(value) {
		data = value ? value.encodeAsJSON() : value
	}

	private initDate(dateCreated) {
		this.date = toDate(dateCreated)
		this.uuid = UUID.timeUUID(this.date)
	}

	static private toDate(String dateCreated) {
		getFormatter().parse(dateCreated)
	}

	static private toDate(Date date) {
		date
	}

	static private toDate(date) {
		if (date != null) {
			throw new IllegalArgumentException("Cannot convert ${date.getClass().name} into a Date")
		}
		else {
			new Date()
		}
	}
}
