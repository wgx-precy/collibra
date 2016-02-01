loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS ASSERTION SCRIPT BEGIN******");
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========UUID_status_InProgress=========="+UUID_status_InProgress);
  loggerComponent.info("[[ DEBUG ]] - ==========assertionVocabularyID=========="+assertionVocabularyID);
}

def inProgressAssertions = [];
def inProgressAssertionsEmpty = false;
vocabularyComponent.getTerms(assertionVocabularyID,0,0).each{ assertion ->
  assertionId = assertion.getId();
  if(assertion.getStatus().getId() == UUID_status_InProgress){   
    if(debug){
      loggerComponent.info("[[ DEBUG ]] - ========== assertion uuid ========== " + assertion.getId());
    }
    inProgressAssertions.add(assertionId);
  }
}
inProgressAssertions.unique();
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========inProgressAssertions=========="+inProgressAssertions);
}
if(inProgressAssertions.size()==0){
  inProgressAssertionsEmpty = true;
}
execution.setVariable('inProgressAssertions', inProgressAssertions);
execution.setVariable('inProgressAssertionsEmpty', inProgressAssertionsEmpty);
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========inProgressAssertions=========="+inProgressAssertions);
  loggerComponent.info("[[ DEBUG ]] - ==========inProgressAssertionsEmpty=========="+inProgressAssertionsEmpty);
}
loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS ASSERTION SCRIPT END******");
