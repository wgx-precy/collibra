import java.text.DateFormat
import java.text.SimpleDateFormat
import groovy.time.TimeCategory

loggerComponent.info("[[ LOGGING ]] - ********SET NEXT NOTIFICATION DATE SCRIPT BEGIN******");

def getISO8601Date = { Date date->
		TimeZone tz = TimeZone.getTimeZone("UTC")
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
		df.setTimeZone(tz)
		String nowAsISO = df.format(date)
		nowAsISO
	}

Date currentDate =  new Date();
loggerComponent.info("[[ LOGGING ]] - ==========currentDate=========="+currentDate);

use( TimeCategory ) {
    currentDate = currentDate+ 1.minutes
}

String nextNotificationDate = getISO8601Date(currentDate);
loggerComponent.info("[[ LOGGING ]] - ==========nextCertificationDate=========="+nextNotificationDate);
execution.setVariable('nextCertificationDate', nextNotificationDate);

loggerComponent.info("[[ LOGGING ]] - ********SET NEXT NOTIFICATION DATE SCRIPT END******");
