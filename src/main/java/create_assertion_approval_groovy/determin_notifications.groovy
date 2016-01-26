def consumerLOBMDS = ""
def arrayMDS = []
def consumerLOBDDRO = ""
def arrayDDRO = []
def producerLOBDDRO = ""
def producerLOBMDS = ""
def duaAEProducer = ""
def arrayAEProducer = []
def duaAEConsumer = ""
def arrayAEConsumer = []
def consumerProducerDDRO = ""
def consumerProducerMDS = ""

//get the "Consumer DDRO Notified" LOB from the DUA
relationComponent.findRelationsBySourceAndType(UUID_RelType_ConsumerLOB, duaPicked, 0,0).each { lob ->
  //add "Consumer DDRO Notified" LOB as a relation to Assertion
  relationComponent.addRelation(lob.target.id,UUID_RelType_AsToConLOB,item.id,null,null)
  //get the DDRO users from the LOB
   rightsComponent.getMembersByResourceAndRole(lob.target.id, UUID_Role_DDRO).each { mem ->
      //add consumerLOB DDRO to array to set as responsibility on assertion
      arrayDDRO.add(mem.ownerId)
      if (consumerLOBDDRO != "") {
        consumerLOBDDRO = consumerLOBDDRO + ",user(" + mem.user.getUserName() + ")"  
      } else {
        consumerLOBDDRO = "user(" + mem.user.getUserName() + ")" 
      }
      loggerComponent.info ("[[ LOGGING ]] USER NAME OF DDRO IS " + consumerLOBDDRO)

      if (consumerProducerDDRO != "") {
        consumerProducerDDRO = consumerProducerDDRO + ",user(" + mem.user.getUserName() + ")"  
      } else {
        consumerProducerDDRO = "user(" + mem.user.getUserName() + ")" 
      }
      loggerComponent.info ("[[ LOGGING ]] USER NAME OF DDRO IS " + consumerProducerDDRO)
   }

   //get the MDS users from the LOB
  rightsComponent.getMembersByResourceAndRole(lob.target.id, UUID_Role_MDS).each { mem ->
      //add consumerLOB MDS to array to set as responsibility on assertion
      arrayMDS.add(mem.ownerId)
      if (consumerLOBMDS != "") {
          consumerLOBMDS = consumerLOBMDS + ",user(" + mem.user.getUserName() + ")"  
        } else {
          consumerLOBMDS = "user(" + mem.user.getUserName() + ")" 
        }

      if (consumerProducerMDS != "") {
          consumerProducerMDS = consumerProducerMDS + ",user(" + mem.user.getUserName() + ")"  
        } else {
          consumerProducerMDS = "user(" + mem.user.getUserName() + ")" 
        }
      
        loggerComponent.info ("[[ LOGGING ]] USER NAME OF MDS IS " + consumerProducerMDS)
   }
}

//get the "Producer DDRO Notified" LOB from the DUA to set DDRO and MDS responsibilities
relationComponent.findRelationsBySourceAndType(UUID_RelType_ProducerLOB, duaPicked, 0,0).each { lob ->
  relationComponent.addRelation(lob.target.id,UUID_RelType_AsToProdLOB,item.id,null,null)
  rightsComponent.getMembersByResourceAndRole(lob.target.id, UUID_Role_DDRO).each { mem ->
    arrayDDRO.add(mem.ownerId)
    if (producerLOBDDRO != "") {
        producerLOBDDRO = producerLOBDDRO + ",user(" + mem.user.getUserName() + ")"  
      } else {
        producerLOBDDRO = "user(" + mem.user.getUserName() + ")" 
      }
    loggerComponent.info ("[[ LOGGING ]] USER NAME OF DDRO IS " + producerLOBDDRO)

    if (consumerProducerDDRO != "") {
        consumerProducerDDRO = consumerProducerDDRO + ",user(" + mem.user.getUserName() + ")"  
      } else {
        consumerProducerDDRO = "user(" + mem.user.getUserName() + ")" 
      }
      loggerComponent.info ("[[ LOGGING ]] USER NAME OF DDRO IS " + consumerProducerDDRO)
    }
  rightsComponent.getMembersByResourceAndRole(lob.target.id, UUID_Role_MDS).each { mem ->
    arrayMDS.add(mem.ownerId)
    if (consumerProducerMDS != "") {
          consumerProducerMDS = consumerProducerMDS + ",user(" + mem.user.getUserName() + ")"  
        } else {
          consumerProducerMDS = "user(" + mem.user.getUserName() + ")" 
        }
    if (producerLOBMDS != "") {
        producerLOBMDS = producerLOBMDS + ",user(" + mem.user.getUserName() + ")"  
      } else {
        producerLOBMDS = "user(" + mem.user.getUserName() + ")" 
    }
    loggerComponent.info ("[[ LOGGING ]] USER NAME OF MDS IS " + consumerProducerMDS)
  }
}

//set MDS responsibility from Consumer and Producer LOB
arrayMDS.unique().each { mds ->
  rightsComponent.addMember(mds, UUID_Role_MDS, item.id)
}

//set DDRO responsibility from Consumer and Producer LOB
arrayDDRO.unique().each { ddro ->
  rightsComponent.addMember(ddro, UUID_Role_DDRO, item.id)
}


//get the AE-Producer from the DUA
rightsComponent.getMembersByResourceAndRole(duaPicked, UUID_Role_AEProducer).each { mem ->
  arrayAEProducer.add(mem.ownerId)
    if (duaAEProducer != "") {
      duaAEProducer = duaAEProducer + ",user(" + mem.user.getUserName() + ")"  
    } else {
    duaAEProducer = "user(" + mem.user.getUserName() + ")" 
    }
          
    loggerComponent.info ("[[ LOGGING ]] USER NAME OF AEProducer IS " + duaAEProducer)
} 

//get the AE-Consumer from the DUA
rightsComponent.getMembersByResourceAndRole(duaPicked, UUID_Role_AEConsumer).each { mem ->
    arrayAEConsumer.add(mem.ownerId)
    if (duaAEConsumer != "") {
      duaAEConsumer = duaAEConsumer + ",user(" + mem.user.getUserName() + ")"  
    } else {
    duaAEConsumer = "user(" + mem.user.getUserName() + ")" 
    }
          
    loggerComponent.info ("[[ LOGGING ]] USER NAME OF AEConsumer IS " + duaAEConsumer)
} 

//set AE Producer responsibility from DUA
arrayAEProducer.unique().each { producer ->
  rightsComponent.addMember(producer, UUID_Role_AEProducer, item.id)
}

//set AE Consumer responsibility from DUA
arrayAEConsumer.unique().each { consumer ->
  rightsComponent.addMember(consumer, UUID_Role_AEConsumer, item.id)
}

execution.setVariable('duaAEConsumer', duaAEConsumer)
execution.setVariable('duaAEProducer', duaAEProducer)
execution.setVariable('consumerLOBMDS', consumerLOBMDS)
execution.setVariable('consumerLOBDDRO', consumerLOBDDRO)
execution.setVariable('producerLOBDDRO', producerLOBDDRO)
execution.setVariable('producerLOBMDS', producerLOBMDS)
execution.setVariable('consumerProducerDDRO', consumerProducerDDRO)
execution.setVariable('consumerProducerMDS', consumerProducerMDS)

