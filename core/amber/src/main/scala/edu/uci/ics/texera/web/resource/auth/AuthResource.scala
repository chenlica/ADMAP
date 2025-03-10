package edu.uci.ics.texera.web.resource.auth

import edu.uci.ics.amber.engine.common.AmberConfig
import edu.uci.ics.texera.dao.SqlServer
import edu.uci.ics.texera.web.auth.JwtAuth._
import edu.uci.ics.texera.web.model.http.request.auth.{
  RefreshTokenRequest,
  UserLoginRequest,
  UserRegistrationRequest,
  LdapUserRegistrationRequest
}
import edu.uci.ics.texera.web.model.http.response.TokenIssueResponse
import edu.uci.ics.texera.dao.jooq.generated.Tables.USER
import edu.uci.ics.texera.dao.jooq.generated.enums.UserRoleEnum
import edu.uci.ics.texera.dao.jooq.generated.tables.daos.UserDao
import edu.uci.ics.texera.dao.jooq.generated.tables.pojos.User
import edu.uci.ics.texera.web.resource.auth.AuthResource._
import org.jasypt.util.password.StrongPasswordEncryptor
import javax.ws.rs._
import javax.ws.rs.core.MediaType
import com.unboundid.ldap.sdk.{LDAPConnection, Entry, AddRequest}
import com.unboundid.ldap.sdk._
import com.jcraft.jsch._

object AuthResource {

  final private val HOST_IP: String = "3.145.57.82"

  final private lazy val userDao = new UserDao(
    SqlServer
      .getInstance()
      .createDSLContext()
      .configuration
  )

  /**
    * Retrieve exactly one User from databases with the given username and password.
    * The password is used to validate against the hashed password stored in the db.
    *
    * @param name     String

    * @param password String, plain text password
    * @return
    */
  def retrieveUserByUsernameAndPassword(name: String, password: String): Option[User] = {
    Option(
      SqlServer
        .getInstance()
        .createDSLContext()
        .select()
        .from(USER)
        .where(USER.NAME.eq(name))
        .fetchOneInto(classOf[User])
    ).filter(user => new StrongPasswordEncryptor().checkPassword(password, user.getPassword))
  }


  def addUserToLdap(ldapUser: User): LDAPResult = {
    val ldapHost = HOST_IP
    val ldapPort = 389
    val ldapBindDN = "cn=admin,dc=admap,dc=com"
    val ldapBindPassword = "23627"
    val baseDN = "dc=admap,dc=com"

    val emailPrefix = ldapUser.getEmail.split("@")(0)
    val uid = ldapUser.getUid
    val name = ldapUser.getName

    val username = s"${emailPrefix}_${uid}"
    val password = ldapUser.getPassword

    var connection: LDAPConnection = null
    try {
      connection = new LDAPConnection(ldapHost, ldapPort, ldapBindDN, ldapBindPassword)
      val entry = new Entry(s"uid=$username,ou=users,$baseDN")


      entry.addAttribute("objectClass", "inetOrgPerson", "posixAccount", "shadowAccount")
      entry.addAttribute("uid", username)
      entry.addAttribute("cn", name)
      entry.addAttribute("sn", name)
      entry.addAttribute("userPassword", password)
      entry.addAttribute("loginShell", "/bin/bash")
      entry.addAttribute("uidNumber", uid.toString)
      entry.addAttribute("gidNumber", "5000")
      entry.addAttribute("homeDirectory", s"/home/users/$username")

      // Use AddRequest explicitly
      val addRequest = new AddRequest(entry)
      val result = connection.add(addRequest)

      createHomeDirectory(ldapUser)

      result

    } catch {
      case e: LDAPException =>
        println(s"LDAP error: ${e.getMessage}, Code: ${e.getResultCode}")
        throw e

    } finally {
      if (connection != null && connection.isConnected) {
        connection.close()
      }
    }
  }

  def createHomeDirectory(ldapUser: User): Boolean = {
    val emailPrefix = ldapUser.getEmail.split("@")(0)
    val uid = ldapUser.getUid
    val username = s"${emailPrefix}_${uid}"

    val path = s"/home/users/$username/"

    val sshHost = HOST_IP
    val sshUser = "ubuntu"
    val privateKeyPath = "/Users/lanaramadan/Desktop/012624-2.pem"

    // make directory and change ownership to the user
    val command = s"sudo mkdir -p $path && sudo chown -R $username:5000 $path"


    var session: Session = null

    try {
      val jsch = new JSch()
      jsch.setKnownHosts("/Users/lanaramadan/.ssh/known_hosts")
      jsch.addIdentity(privateKeyPath, "12345")

      session = jsch.getSession(sshUser, sshHost, 22)
      session.setConfig("StrictHostKeyChecking", "no")

      session.connect()

      val channel = session.openChannel("exec").asInstanceOf[ChannelExec]
      channel.setCommand(command)
      channel.setErrStream(System.err)
      channel.connect()

      true


    } catch {
      case e: Exception =>
        println(s"Error: ${e.getMessage}")
        e.printStackTrace()
        false
    } finally {
      // Ensure the session is disconnected
      if (session != null && session.isConnected) {
        session.disconnect()
      }
    }
  }
}

@Path("/auth/")
@Consumes(Array(MediaType.APPLICATION_JSON))
@Produces(Array(MediaType.APPLICATION_JSON))
class AuthResource {

  @POST
  @Path("/login")
  def login(request: UserLoginRequest): TokenIssueResponse = {
    if (!AmberConfig.isUserSystemEnabled)
      throw new NotAcceptableException("User System is disabled on the backend!")
    retrieveUserByUsernameAndPassword(request.username, request.password) match {
      case Some(user) =>
        TokenIssueResponse(jwtToken(jwtClaims(user, dayToMin(TOKEN_EXPIRE_TIME_IN_DAYS))))
      case None => throw new NotAuthorizedException("Login credentials are incorrect.")
    }
  }

  @POST
  @Path("/refresh")
  def refresh(request: RefreshTokenRequest): TokenIssueResponse = {
    val claims = jwtConsumer.process(request.accessToken).getJwtClaims
    claims.setExpirationTimeMinutesInTheFuture(dayToMin(TOKEN_EXPIRE_TIME_IN_DAYS).toFloat)
    TokenIssueResponse(jwtToken(claims))
  }

  @POST
  @Path("/register")
  def register(request: UserRegistrationRequest): TokenIssueResponse = {
    println("TESTING")
    if (!AmberConfig.isUserSystemEnabled)
      throw new NotAcceptableException("User System is disabled on the backend!")
    val username = request.username
    if (username == null) throw new NotAcceptableException("Username cannot be null.")
    if (username.trim.isEmpty) throw new NotAcceptableException("Username cannot be empty.")
    userDao.fetchByName(username).size() match {
      case 0 =>
        val user = new User
        user.setName(username)
        user.setEmail(username)
        user.setRole(UserRoleEnum.ADMIN)
        // hash the plain text password
        user.setPassword(new StrongPasswordEncryptor().encryptPassword(request.password))
        userDao.insert(user)


        addUserToLdap(user)



        TokenIssueResponse(jwtToken(jwtClaims(user, dayToMin(TOKEN_EXPIRE_TIME_IN_DAYS))))
      case _ =>
        // the username exists already
        throw new NotAcceptableException("Username exists already.")
    }
  }


//  @POST
//  @Path("/add-ldap-user")
//  def addLdapUser(request: LdapUserRegistrationRequest): TokenIssueResponse = {
//    val scpUsername = request.scpUsername
//    val scpPassword = request.scpPassword
//
//    val ldapUser = new User
////    ldapUser.setScpUsername(scpUsername)
////    ldapUser.setScpPassword(scpPassword)
//    ldapUser.setName(scpUsername)
//    ldapUser.setPassword(scpPassword)
//
//    addUserToLdap(ldapUser)
//    createHomeDirectory(ldapUser)
//
//    TokenIssueResponse(jwtToken(jwtClaims(ldapUser, dayToMin(TOKEN_EXPIRE_TIME_IN_DAYS))))
//  }


}
