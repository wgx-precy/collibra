loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS PRODUCER MDS LIST SCRIPT BEGIN******");
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========UUID_Role_MDS=========="+UUID_Role_MDS);
  loggerComponent.info("[[ DEBUG ]] - ==========inProgressAssertions=========="+inProgressAssertions);
}
/*Notify AE Producer Use*/
def inProgressProducerMDS = "";
def arrayMDS = [];
def inProgressProducerMDSEmpty = false;
/*get AE-Producer by assertion*/
inProgressAssertions.each { assertionPicked ->
  rightsComponent.getMembersByResourceAndRole(assertionPicked, UUID_Role_MDS).each { mem ->
    arrayMDS.add(mem)
  }
}
arrayMDS.unique();
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========arrayMDS=========="+arrayMDS);
}
if(arrayMDS.size()==0){
  inProgressProducerMDSEmpty = true;
}
arrayMDS.each { men ->
  if (inProgressProducerMDS != "") {
      inProgressProducerMDS = inProgressProducerMDS + ",user(" + mem.user.getUserName() + ")"  
    } else {
      inProgressProducerMDS = "user(" + mem.user.getUserName() + ")" 
  }
}
execution.setVariable('inProgressProducerMDS', inProgressProducerMDS);
execution.setVariable('inProgressProducerMDSEmpty', inProgressProducerMDSEmpty);
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========inProgressProducerMDS=========="+inProgressProducerMDS);
  loggerComponent.info("[[ DEBUG ]] - ==========inProgressProducerMDSEmpty=========="+inProgressProducerMDSEmpty);
}
loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS PRODUCER MDS LIST SCRIPT END******");

