loggerComponent.info("[[ LOGGING ]] - ********SET UNDER PRODUCER REVIEW ASSERTION SCRIPT BEGIN******");
if(debug){
	loggerComponent.info("[[ DEBUG ]] - ==========UUID_status_UnderProducerReview=========="+UUID_status_UnderProducerReview);
	loggerComponent.info("[[ DEBUG ]] - ==========assertionVocabularyID=========="+assertionVocabularyID);
}

def underProducerReviewAssertions = [];
def underProducerReviewAssertionsEmpty = false;
vocabularyComponent.getTerms(assertionVocabularyID,0,0).each{ assertion ->
  assertionId = assertion.getId();
  if(assertion.getStatus().getId() == UUID_status_UnderProducerReview){
    if(debug){
      loggerComponent.info("[[ DEBUG ]] - ========== assertion uuid ========== " + assertion.getId());
    }
   	underProducerReviewAssertions.add(assertionId);
  }
}
underProducerReviewAssertions.unique();
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========underProducerReviewAssertions=========="+underProducerReviewAssertions);
}
if(underProducerReviewAssertions.size()==0){
	underProducerReviewAssertionsEmpty = true;
}
execution.setVariable('underProducerReviewAssertions', underProducerReviewAssertions);
execution.setVariable('underProducerReviewAssertionsEmpty', underProducerReviewAssertionsEmpty);
if(debug){
	loggerComponent.info("[[ DEBUG ]] - ==========underProducerReviewAssertions=========="+underProducerReviewAssertions);
	loggerComponent.info("[[ DEBUG ]] - ==========underProducerReviewAssertionsEmpty=========="+underProducerReviewAssertionsEmpty);
}
loggerComponent.info("[[ LOGGING ]] - ********SET UNDER PRODUCER REVIEW ASSERTION SCRIPT END******");
