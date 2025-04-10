package edu.uci.ics.texera.auth

import edu.uci.ics.texera.dao.jooq.generated.enums.UserRoleEnum
import edu.uci.ics.texera.dao.jooq.generated.tables.pojos.User

import java.security.Principal

class SessionUser(val user: User) extends Principal {
  def getUser: User = user

  override def getName: String = user.getName

  def getUid: Integer = user.getUid

  def getEmail: String = user.getEmail

  def getPassword: String = user.getPassword

  def getGoogleId: String = user.getGoogleId

  def isRoleOf(role: UserRoleEnum): Boolean = user.getRole == role
}
