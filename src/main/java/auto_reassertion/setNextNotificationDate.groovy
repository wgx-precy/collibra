import java.text.DateFormat
import java.text.SimpleDateFormat
import groovy.time.TimeCategory

loggerComponent.info("[[ LOGGING ]] - ********SET NEXT NOTIFICATION DATE SCRIPT BEGIN******");

/*task terminate flag, true to end task if no more notifiy date in notification date list*/
def taskTerminate = false;

Date currentDate =  new Date();

def getISO8601Date = { Date date->
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		String nowAsISO = df.format(date);
		nowAsISO;
	}

def getNextNotificationDate ={List<Date> notificationDateList->
		Date nextNotifcationDate;
		/*Loop through the notification Date List, and find the next one*/
		if(debug){
			loggerComponent.info("[[ DEBUG ]] - ==========notificationDateList=========="+notificationDateList);
		}
		for (notifcationDate in notificationDateList){
			if(currentDate < notifcationDate){
				nextNotifcationDate = notifcationDate;
				break;
			}
		}
		/*If this year does not include any more recertification dates, set nextNotifcationDate to current date and terminate to true */
		if(nextNotifcationDate == null){
			nextNotifcationDate = currentDate;
			taskTerminate = true;
		}	
		nextNotifcationDate;
	}

String nextNotificationDate;

if(debug) {
	currentDate = currentDate;
	use( TimeCategory ) {
	    currentDate = currentDate+ 5.minutes;
	}
	nextNotificationDate = getISO8601Date(currentDate);
	loggerComponent.info("[[ DEBUG ]] - ==========nextNotificationDate=========="+nextNotificationDate);
	execution.setVariable('nextDay', currentDate);
} else {
	nextDay = getNextNotificationDate(notificationDateList);
	nextNotificationDate = getISO8601Date(nextDay);
	loggerComponent.info("[[ LOGGING ]] - ==========testNotificationDate=========="+nextDay);
	loggerComponent.info("[[ LOGGING ]] - ==========isoTestNotificationDate=========="+nextNotificationDate);
	execution.setVariable('nextDay', nextDay);
}
execution.setVariable('nextNotificationDate', nextNotificationDate);
execution.setVariable('taskTerminate', taskTerminate);
loggerComponent.info("[[ LOGGING ]] - ********SET NEXT NOTIFICATION DATE SCRIPT END******");
