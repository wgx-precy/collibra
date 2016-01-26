loggerComponent.info("[[ LOGGING ]] - Inside create assertion approval after DUA is picked " + duaPicked)

relationComponent.addRelation(duaPicked,UUID_RelType_DUA,item.id,null,null);

duaName = termComponent.getTerm(duaPicked).getSignifier()

loggerComponent.info("[[ LOGGING ]] - The name of the DUA is : duaName : " + duaName)

currentUser = userComponent.getUserByName(requester).getId()

userComponent.getGroups().each { group ->
    groupName = group.getGroupName()
    groupId = group.getId()
    if( groupId == UUID_Group_MDSDel || groupId ==  UUID_Group_PDS){
      group.getUsers().each { user ->
        userId = user.getId()
        loggerComponent.info("[[ LOGGING ]] - getting user name. inside users for MDS Delegate and PDS groups | user: " + user.getId())
        if (currentUser == userId){
          if(groupId == UUID_Group_MDSDel){
            loggerComponent.info("[[ LOGGING ]] - adding user as MDS Delegate.  userId : " + userId)
            rightsComponent.addMember(currentUser, UUID_Role_MDSDelegate, item.id)
          }
          if(groupId == UUID_Group_PDS){
            loggerComponent.info("[[ LOGGING ]] - adding user as MDS Delegate.  userId : " + userId)
            rightsComponent.addMember(currentUser, UUID_Role_PDS, item.id)
          }
        }
      }
    }
    loggerComponent.info("[[ LOGGING ]] - getting group name.  groupName: " + groupName)
}

execution.setVariable('duaName', duaName)