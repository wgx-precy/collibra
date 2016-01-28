loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS AE PRODUCER LIST SCRIPT BEGIN******");
if(debug){
  loggerComponent.info("[[ LOGGING ]] - ==========UUID_Role_AEProducer=========="+UUID_Role_AEProducer);
  loggerComponent.info("[[ LOGGING ]] - ==========inProgressAssertions=========="+inProgressAssertions);
}
def inProgressAEProducer = "";
def arrayAEProducer = [];
//get the AE-Producer from the DUA
inProgressAssertions.each { duaPicked ->
  rightsComponent.getMembersByResourceAndRole(duaPicked, UUID_Role_AEProducer).each { mem ->
    arrayAEProducer.add(mem);
  } 
}
arrayAEProducer.unique();
arrayAEProducer.each { men ->
  if (inProgressAEProducer != "") {
        inProgressAEProducer = inProgressAEProducer + ",user(" + mem.user.getUserName() + ")";
      } else {
        inProgressAEProducer = "user(" + mem.user.getUserName() + ")";
      }    
      if(debug){ 
        loggerComponent.info ("[[ LOGGING ]] USER NAME OF AEProducer IS " + inProgressAEProducer);
      }
}
execution.setVariable('inProgressAEProducer', inProgressAEProducer);
if(debug){
  loggerComponent.info("[[ LOGGING ]] - ==========inProgressAEProducer=========="+inProgressAEProducer);
}
loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS AE PRODUCER LIST SCRIPT END******");

