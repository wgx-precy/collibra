loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS AE PRODUCER LIST SCRIPT BEGIN******");
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========UUID_Role_AEProducer=========="+UUID_Role_AEProducer);
  loggerComponent.info("[[ DEBUG ]] - ==========inProgressAssertions=========="+inProgressAssertions);
}
/*Notify AE Producer Use*/
def inProgressAEProducer = "";
def arrayAEProducer = [];
def inProgressAEProducerEmpty = false;
/*get AE-Producer by assertion*/
inProgressAssertions.each { assertionPicked ->
  rightsComponent.getMembersByResourceAndRole(assertionPicked, UUID_Role_AEProducer).each { mem ->
    arrayAEProducer.add(mem);
  } 
}
arrayAEProducer.unique();
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========arrayAEProducer=========="+arrayAEProducer);
}
if(arrayAEProducer.size()==0){
  inProgressAEProducerEmpty = true;
}
arrayAEProducer.each { mem ->
  if (inProgressAEProducer != "") {
        inProgressAEProducer = inProgressAEProducer + ",user(" + mem.user.getUserName() + ")";
      } else {
        inProgressAEProducer = "user(" + mem.user.getUserName() + ")";
      }    
      if(debug){ 
        loggerComponent.info ("[[ DEBUG ]] USER NAME OF AEProducer IS " + inProgressAEProducer);
      }
}
execution.setVariable('inProgressAEProducer', inProgressAEProducer);
execution.setVariable('inProgressAEProducerEmpty', inProgressAEProducerEmpty);
if(debug){
  loggerComponent.info("[[ DEBUG ]] - ==========inProgressAEProducer=========="+inProgressAEProducer);
  loggerComponent.info("[[ DEBUG ]] - ==========inProgressAEProducerEmpty=========="+inProgressAEProducerEmpty);
}
loggerComponent.info("[[ LOGGING ]] - ********SET IN PROGRESS AE PRODUCER LIST SCRIPT END******");

