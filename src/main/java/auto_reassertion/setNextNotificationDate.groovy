import java.text.DateFormat
import java.text.SimpleDateFormat
import groovy.time.TimeCategory

loggerComponent.info("[[ LOGGING ]] - ********SET NEXT NOTIFICATION DATE SCRIPT BEGIN******");

Date currentDate =  new Date();

def getISO8601Date = { Date date->
		TimeZone tz = TimeZone.getTimeZone("UTC")
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
		df.setTimeZone(tz)
		String nowAsISO = df.format(date)
		nowAsISO
	}

def getNextNotificationDate ={List<Date> notificationDateList->
		Date nextNotifcationDate
		/*Loop through the notification Date List, and find the next one*/
		loggerComponent.info("[[ LOGGING ]] - ==========notificationDateList=========="+notificationDateList);
		for (notifcationDate in notificationDateList){
			if(currentDate < notifcationDate){
				nextNotifcationDate = notifcationDate
				break;
			}
		}
		/*If this year does not include any more recertification dates, set nextNotifcationDate to current date and terminate to true */
		if(nextNotifcationDate == null){
			nextNotifcationDate = currentDate
		}	
		nextNotifcationDate
	}
def testNotificationDate = getNextNotificationDate(notificationDateList);
loggerComponent.info("[[ LOGGING ]] - ==========testNotificationDate=========="+testNotificationDate);

use( TimeCategory ) {
    currentDate = currentDate+ 1.minutes
}

String nextNotificationDate = getISO8601Date(currentDate);
loggerComponent.info("[[ LOGGING ]] - ==========nextCertificationDate=========="+nextNotificationDate);
execution.setVariable('nextCertificationDate', nextNotificationDate);
loggerComponent.info("[[ LOGGING ]] - ********SET NEXT NOTIFICATION DATE SCRIPT END******");
