loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS AE PRODUCER LIST SCRIPT BEGIN******");
if(debug){
	loggerComponent.info("[[ DEBUG ]] - ==========UUID_Role_AEProducer=========="+UUID_Role_AEProducer);
	loggerComponent.info("[[ DEBUG ]] - ==========underProducerReviewAssertions=========="+underProducerReviewAssertions);
}
/*Notify AE Producer Use*/
def underProducerReviewAEProducer = "";
def arrayAEProducer = [];
def underProducerReviewAEProducerEmpty = false;
/*get AE-Producer by assertion*/
underProducerReviewAssertions.each { assertionPicked ->
  rightsComponent.getMembersByResourceAndRole(assertionPicked, UUID_Role_AEProducer).each { mem ->
    arrayAEProducer.add(mem);
  } 
}
arrayAEProducer.unique();
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========arrayAEProducer=========="+arrayAEProducer);
}
if(arrayAEProducer.size()==0){
	underProducerReviewAEProducerEmpty = true;
}
arrayAEProducer.each { mem ->
  if (underProducerReviewAEProducer != "") {
        underProducerReviewAEProducer = underProducerReviewAEProducer + ",user(" + mem.user.getUserName() + ")";
      } else {
        underProducerReviewAEProducer = "user(" + mem.user.getUserName() + ")";
      } 
      if(debug){    
	      loggerComponent.info ("[[ DEBUG ]] USER NAME OF AEProducer IS " + underProducerReviewAEProducer);
	  }
}
execution.setVariable('underProducerReviewAEProducer', underProducerReviewAEProducer);
execution.setVariable('underProducerReviewAEProducerEmpty', underProducerReviewAEProducerEmpty);
if(debug){
	loggerComponent.info("[[ DEBUG ]] - ==========underProducerReviewAEProducer=========="+underProducerReviewAEProducer);
	loggerComponent.info("[[ DEBUG ]] - ==========underProducerReviewAEProducerEmpty=========="+underProducerReviewAEProducerEmpty);
}
loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS AE PRODUCER LIST SCRIPT END******");

