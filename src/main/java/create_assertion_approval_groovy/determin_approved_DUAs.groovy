if(debug){
  loggerComponent.info("[[ LOGGING ]] - ********inside dertermine approved DUA workflow******")
  loggerComponent.info("[[ LOGGING ]] - ********debug equal to ****** " + debug)

  loggerComponent.info("[[ LOGGING ]] - ********duaVocabularyID equal to ****** " + duaVocabularyID)
  loggerComponent.info("[[ LOGGING ]] - ********UUID_status_Approved equal to ****** " + UUID_status_Approved)
}


arrayOfApprovedDUAs = []
counter = 0

vocabularyComponent.getTerms(duaVocabularyID,0,0).each{ dua ->

  duaId = dua.getId()
  currentUser = userComponent.getUserByName(requester).getId()


  if(dua.getStatus().getId() == UUID_status_Approved){
    
    if(debug){
      loggerComponent.info("[[ LOGGING ]] - ********DUA FOUND ****** " + dua.getId())
    }

    loggerComponent.info("[[ LOGGING ]] - INSIDE MDS USERS FOR PRODUCER LOB currentUser: " + currentUser)
    loggerComponent.info("[[ LOGGING ]] - INSIDE MDS USERS FOR PRODUCER LOB duaId: " + duaId)
    relationComponent.findRelationsBySourceAndType(UUID_RelType_ProducerLOB, duaId, 0,0).each { lob ->
      lobId = lob.getTarget().getId()
      loggerComponent.info("[[ LOGGING ]] - INSIDE MDS USERS FOR PRODUCER LOB lobId: " + lobId)
      rightsComponent.getMembersByResourceAndRole(lobId, UUID_Role_MDS).each { mem ->
        user = mem.getUser().getId()
        loggerComponent.info("[[ LOGGING ]] - INSIDE MDS USERS FOR PRODUCER LOB user: " + user)
        if(user == currentUser){
          arrayOfApprovedDUAs.add(dua.getId())
          counter = counter + 1
          loggerComponent.info("[[ LOGGING ]] - ADDING DUA BASED ON MDS: " + user)
        }
      }
      rightsComponent.getMembersByResourceAndRole(lobId, UUID_Role_MDSDelegate).each { mem ->
        user = mem.getUser().getId()
        loggerComponent.info("[[ LOGGING ]] - INSIDE MDS DELEGATE USERS FOR PRODUCER LOB user: " + user)
        if(user == currentUser){
          arrayOfApprovedDUAs.add(dua.getId())
          counter = counter + 1
          loggerComponent.info("[[ LOGGING ]] - ADDING DUA BASED ON MDS DELEGATE: " + user)
        }
      }
    }

    
  }
 
}

arrayOfApprovedDUAs.unique()
stringOfApprovedDUAs = arrayOfApprovedDUAs.join(',')
loggerComponent.info("[[ LOGGING ]] - TOTAL COUNT OF DUAS counter: " + counter)

if(debug){
  loggerComponent.info("[[ LOGGING ]] - ********stringOfApprovedDUAs equal to ****** " + stringOfApprovedDUAs)
}

execution.setVariable('stringOfApprovedDUAs', stringOfApprovedDUAs)