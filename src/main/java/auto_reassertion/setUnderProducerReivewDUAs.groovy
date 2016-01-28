loggerComponent.info("[[ LOGGING ]] - ********SET UNDER PRODUCER REVIEW ASSERTION SCRIPT BEGIN******");
loggerComponent.info("[[ LOGGING ]] - ==========UUID_status_UnderProducerReview=========="+UUID_status_UnderProducerReview);
loggerComponent.info("[[ LOGGING ]] - ==========duaVocabularyID=========="+duaVocabularyID);

def underProducerReviewAssertions = [];
def count = 0;
vocabularyComponent.getTerms(duaVocabularyID,0,0).each{ dua ->
  duaId = dua.getId();
  if(dua.getStatus().getId() == UUID_status_UnderProducerReview){
    
    //if(debug){
      loggerComponent.info("[[ LOGGING ]] - ========== DUAuuid ========== " + dua.getId())
    //}
   	underProducerReviewAssertions.add(dua.getId());
    counter = counter + 1   
  }
}
underProducerReviewAssertions.unique();
execution.setVariable('underProducerReviewAssertions', underProducerReviewAssertions);
loggerComponent.info("[[ LOGGING ]] - ==========underProducerReviewAssertions=========="+underProducerReviewAssertions);
loggerComponent.info("[[ LOGGING ]] - ********SET UNDER PRODUCER REVIEW ASSERTION SCRIPT BEGIN******");
