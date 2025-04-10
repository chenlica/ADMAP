package edu.uci.ics.texera.web.auth

import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter
import edu.uci.ics.amber.engine.common.AmberConfig
import edu.uci.ics.texera.auth.{AuthConfig, SessionUser}
import edu.uci.ics.texera.dao.jooq.generated.tables.pojos.User
import io.dropwizard.auth.AuthDynamicFeature
import io.dropwizard.setup.Environment
import org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256
import org.jose4j.jws.JsonWebSignature
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.{JwtConsumer, JwtConsumerBuilder}
import org.jose4j.keys.HmacKey

import java.util.Random

// TODO: move this logic to Auth
object JwtAuth {

  final val TOKEN_EXPIRE_TIME_IN_DAYS = AuthConfig.jwtExpirationDays
  final val TOKEN_SECRET: String = AuthConfig.jwtSecretKey.toLowerCase() match {
    case "random" => getRandomHexString
    case _        => AuthConfig.jwtSecretKey
  }

  val jwtConsumer: JwtConsumer = new JwtConsumerBuilder()
    .setAllowedClockSkewInSeconds(30)
    .setRequireExpirationTime()
    .setRequireSubject()
    .setVerificationKey(new HmacKey(TOKEN_SECRET.getBytes))
    .setRelaxVerificationKeyValidation()
    .build

  def setupJwtAuth(environment: Environment): Unit = {
    if (AmberConfig.isUserSystemEnabled) {
      // register JWT Auth layer
      environment.jersey.register(
        new AuthDynamicFeature(
          new JwtAuthFilter.Builder[SessionUser]()
            .setJwtConsumer(jwtConsumer)
            .setRealm("realm")
            .setPrefix("Bearer")
            .setAuthenticator(UserAuthenticator)
            .setAuthorizer(UserRoleAuthorizer)
            .buildAuthFilter()
        )
      )
    } else {
      // register Guest Auth layer
      environment.jersey.register(
        new AuthDynamicFeature(
          new GuestAuthFilter.Builder().setAuthorizer(UserRoleAuthorizer).buildAuthFilter()
        )
      )
    }

  }

  def jwtToken(claims: JwtClaims): String = {
    val jws = new JsonWebSignature()
    jws.setPayload(claims.toJson)
    jws.setAlgorithmHeaderValue(HMAC_SHA256)
    jws.setKey(new HmacKey(TOKEN_SECRET.getBytes))
    jws.getCompactSerialization
  }

  def jwtClaims(user: User, expireInDays: Int): JwtClaims = {
    val claims = new JwtClaims
    claims.setSubject(user.getName)
    claims.setClaim("userId", user.getUid)
    claims.setClaim("googleId", user.getGoogleId)
    claims.setClaim("email", user.getEmail)
    claims.setClaim("role", user.getRole)
    claims.setClaim("googleAvatar", user.getGoogleAvatar)
    claims.setExpirationTimeMinutesInTheFuture(dayToMin(expireInDays).toFloat)
    claims
  }

  def dayToMin(days: Int): Int = {
    days * 24 * 60
  }

  private def getRandomHexString: String = {
    val bytes = 32
    val r = new Random()
    val sb = new StringBuffer
    while (sb.length < bytes)
      sb.append(Integer.toHexString(r.nextInt()))
    sb.toString.substring(0, bytes)
  }
}
