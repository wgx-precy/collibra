loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS ASSERTION SCRIPT BEGIN******");
loggerComponent.info("[[ LOGGING ]] - ==========UUID_status_InProgress=========="+UUID_status_InProgress);
loggerComponent.info("[[ LOGGING ]] - ==========duaVocabularyID=========="+duaVocabularyID);

def inProgressAssertions = [];
def count = 0;
vocabularyComponent.getTerms(duaVocabularyID,0,0).each{ dua ->
  duaId = dua.getId();
  if(dua.getStatus().getId() == UUID_status_InProgress){   
    //if(debug){
      loggerComponent.info("[[ LOGGING ]] - ========== DUAuuid ========== " + dua.getId())
    //}
   	inProgressAssertions.add(dua.getId());
    counter = counter + 1
  }
}
inProgressAssertions.unique();
execution.setVariable('inProgressAssertions', inProgressAssertions);
loggerComponent.info("[[ LOGGING ]] - ==========inProgressAssertions=========="+inProgressAssertions);
loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS ASSERTION SCRIPT BEGIN******");
