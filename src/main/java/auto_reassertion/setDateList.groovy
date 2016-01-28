import com.collibra.dgc.core.exceptions.DGCException
import java.text.DateFormat
import java.text.SimpleDateFormat

loggerComponent.info("[[ LOGGING ]] - ********SET DATE TIME LIST SCRIPT BEGIN******");
if(debug){
	loggerComponent.info("[[ LOGGING ]] - ==========YEAR INPUTS==========" + yearInput);
	loggerComponent.info("[[ LOGGING ]] - ==========DATE INPUTS==========" + dateInputs);
}

int year = Calendar.getInstance().get(Calendar.YEAR);

/*Obtain and sort the provided dates (MM/dd)entered on start form*/
List<Date> notificationDateList	= [];
List<String> notificationDatStringList = utility.toList(dateInputs);
notificationDatStringList.sort();

/*Date list validation*/
notificationDatStringList.each { date ->
	try{
		validDate = new SimpleDateFormat('MM/dd/yyyy HH:mm:ss').parse(date.concat('/').concat(yearInput).concat(' 9:00:00'));
		notificationDateList.add(validDate);
	}catch(java.text.ParseException e){
		dgcError = new DGCException("${date} - Dates must be in MM/DD format");
		dgcError.setTitleCode ("Unparseable Date");
		throw dgcError
	}
}
/*Target year validation*/
if(year.toString()>yearInput) {
	dgcError = new DGCException("${yearInput} - Dates must be equal or larger than ${year}");
	dgcError.setTitleCode ("Invalid target year");
	throw dgcError;
}

execution.setVariable('notificationDateList', notificationDateList);
if(debug){
	loggerComponent.info("[[ LOGGING ]] - ==========NOTIFICATION DATA LIST==========" + notificationDateList);
}
loggerComponent.info("[[ LOGGING ]] - ********SET DATE TIME LIST SCRIPT END******");