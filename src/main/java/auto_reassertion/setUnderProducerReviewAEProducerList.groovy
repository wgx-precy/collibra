loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS AE PRODUCER LIST SCRIPT BEGIN******");
loggerComponent.info("[[ LOGGING ]] - ==========UUID_Role_AEProducer=========="+UUID_Role_AEProducer);
loggerComponent.info("[[ LOGGING ]] - ==========underProducerReviewAssertions=========="+underProducerReviewAssertions);

def underProducerReviewAEProducer = "";
def arrayAEProducer = [];

//get the AE-Producer from the DUA
underProducerReviewAssertions.each { duaPicked ->
  rightsComponent.getMembersByResourceAndRole(duaPicked, UUID_Role_AEProducer).each { mem ->
    arrayAEProducer.add(mem);
  } 
}
arrayAEProducer.unique();
arrayAEProducer.each { men ->
  if (underProducerReviewAEProducer != "") {
        underProducerReviewAEProducer = underProducerReviewAEProducer + ",user(" + mem.user.getUserName() + ")";
      } else {
        underProducerReviewAEProducer = "user(" + mem.user.getUserName() + ")";
      }     
      loggerComponent.info ("[[ LOGGING ]] USER NAME OF AEProducer IS " + underProducerReviewAEProducer);
}
execution.setVariable('underProducerReviewAEProducer', underProducerReviewAEProducer);
loggerComponent.info("[[ LOGGING ]] - ==========underProducerReviewAEProducer=========="+underProducerReviewAEProducer);
loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS AE PRODUCER LIST SCRIPT END******");

