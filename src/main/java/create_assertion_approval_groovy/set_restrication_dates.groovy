import com.collibra.dgc.core.exceptions.DGCException
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.slf4j.LoggerFactory

def setRecertificationDates={
	def codeContext 		= "com.collibra.dgc.recertification.domain"
	log 					= LoggerFactory.getLogger(codeContext)
	result					= [:]
	int year = Calendar.getInstance().get(Calendar.YEAR)

	
	def getISO8601Date={Date date->
		TimeZone tz 		= TimeZone.getTimeZone("UTC")
		DateFormat df		= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
		df.setTimeZone		(tz)
		String nowAsISO 	= df.format(date)
		nowAsISO
	}
	
	Date currentDate 		=  new Date()
	
	
	def getLastCertificationDate={
		//if first time running, recertify all assets
		Date lastCertificationDate 		= execution.getVariable("currentCertificationDate")

		if(lastCertificationDate==null)
		{
			lastCertificationDate 		= new SimpleDateFormat('MM/dd/yyyy').parse("01/01/1900")
		}
		result.lastCertificationDate	=lastCertificationDate

		lastCertificationDate
	}



	def getCertificationDateList ={

		/*Obtain and sort the certification dates (MM/dd)entered on start form
		 ******************************************************/
		List<Date> certificationDateList			= []
		List<String> certificationDateStringList 	= utility.toList(certificationDates)
		certificationDateStringList.sort()

		certificationDateStringList.each {date ->
			try{
				validDate 	=  new SimpleDateFormat('MM/dd/yyyy').parse(date.concat('/').concat(year.toString()))
				certificationDateList.add(validDate)
			}catch(java.text.ParseException e){
				dgcError 	= new DGCException("${date} - Dates must be in MM/DD format")
				dgcError.setTitleCode ("Unparseable Date")
				throw dgcError
			}
		}
		certificationDateList
	}

	def getNextCertificationDate ={List<Date> certificationDateList->
		Date nextCertificationDate

		/*Loop through the certification dates, and find the next one
		 ******************************************************/
		for (certificationDate in certificationDateList){

			if(currentDate < certificationDate){

				nextCertificationDate = certificationDate

				break;
			}
		}
		/*If this year does not include any more recertification dates, look for the first occurrence next year
		 ******************************************************/
		if(nextCertificationDate ==null){
			Calendar c = Calendar.getInstance()
			c.setTime(certificationDateList.min() )
			c.add(Calendar.YEAR, 1)
			nextCertificationDate = c.getTime()
		}

 		result.nextCertificationDateReadable		= new SimpleDateFormat('MM/dd/yyyy').format(nextCertificationDate)
		result.taskDescriptionTwo					=taskDescriptionTwo.replace("CYCLE_DATE",result.nextCertificationDateReadable)
		
		nextCertificationDate
	}



	def exec = {
		Date lastCertificationDate 				= 	getLastCertificationDate()
 		List<Date> certificationDateList		=	getCertificationDateList()		
		String nextCertificationDate			= 	getISO8601Date(getNextCertificationDate(certificationDateList))
 		
		result.nextCertificationDate			= nextCertificationDate
		
		result.currentCertificationDate			= currentDate
		result.currentCertificationDateReadable	= new SimpleDateFormat('MM/dd/yyyy').format(currentDate)
		result
	}
	
	exec()

}

def exportBindings = {
	it.keySet().each{ var ->
		execution.setVariable(var, it[var])
	}
}

exportBindings(setRecertificationDates( ))
 
