/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.uci.ics.texera.web.resource.dashboard.user.workflow

import edu.uci.ics.texera.dao.SqlServer
import edu.uci.ics.texera.auth.SessionUser
import edu.uci.ics.texera.web.model.common.AccessEntry
import edu.uci.ics.texera.dao.jooq.generated.Tables._
import edu.uci.ics.texera.dao.jooq.generated.enums.PrivilegeEnum
import edu.uci.ics.texera.dao.jooq.generated.tables.daos.{
  UserDao,
  WorkflowOfUserDao,
  WorkflowUserAccessDao
}
import edu.uci.ics.texera.dao.jooq.generated.tables.pojos.WorkflowUserAccess
import edu.uci.ics.texera.web.resource.dashboard.user.workflow.WorkflowAccessResource.{
  context,
  hasWriteAccess
}
import io.dropwizard.auth.Auth
import org.jooq.DSLContext

import java.util
import javax.annotation.security.RolesAllowed
import javax.ws.rs._
import javax.ws.rs.core.MediaType

object WorkflowAccessResource {
  final private val context: DSLContext = SqlServer
    .getInstance()
    .createDSLContext()

  /**
    * Identifies whether the given user has read-only access over the given workflow
    *
    * @param wid workflow id
    * @param uid user id, works with workflow id as primary keys in database
    * @return boolean value indicating yes/no
    */
  def hasReadAccess(wid: Integer, uid: Integer): Boolean = {
    isPublic(wid) || getPrivilege(wid, uid).eq(PrivilegeEnum.READ) || hasWriteAccess(
      wid,
      uid
    )
  }

  /**
    * Identifies whether the given user has write access over the given workflow
    *
    * @param wid workflow id
    * @param uid user id, works with workflow id as primary keys in database
    * @return boolean value indicating yes/no
    */
  def hasWriteAccess(wid: Integer, uid: Integer): Boolean = {
    getPrivilege(wid, uid).eq(PrivilegeEnum.WRITE)
  }

  /**
    * @param wid workflow id
    * @param uid user id, works with workflow id as primary keys in database
    * @return PrivilegeEnum value indicating NONE/READ/WRITE
    */
  def getPrivilege(wid: Integer, uid: Integer): PrivilegeEnum = {
    val access = context
      .select()
      .from(WORKFLOW_USER_ACCESS)
      .where(WORKFLOW_USER_ACCESS.WID.eq(wid).and(WORKFLOW_USER_ACCESS.UID.eq(uid)))
      .fetchOneInto(classOf[WorkflowUserAccess])
    if (access == null) {
      val projectAccess = context
        .select()
        .from(PROJECT_USER_ACCESS)
        .join(WORKFLOW_OF_PROJECT)
        .on(WORKFLOW_OF_PROJECT.PID.eq(PROJECT_USER_ACCESS.PID))
        .where(WORKFLOW_OF_PROJECT.WID.eq(wid).and(PROJECT_USER_ACCESS.UID.eq(uid)))
        .fetchOneInto(classOf[WorkflowUserAccess])
      if (projectAccess == null) {
        PrivilegeEnum.NONE
      } else {
        projectAccess.getPrivilege
      }
    } else {
      access.getPrivilege
    }
  }

  def isPublic(wid: Integer): Boolean = {
    context
      .select(WORKFLOW.IS_PUBLIC)
      .from(WORKFLOW)
      .where(WORKFLOW.WID.eq(wid))
      .fetchOneInto(classOf[Boolean])
  }
}

@Produces(Array(MediaType.APPLICATION_JSON))
@RolesAllowed(Array("REGULAR", "ADMIN"))
@Path("/access/workflow")
class WorkflowAccessResource() {
  final private val userDao = new UserDao(context.configuration())
  final private val workflowOfUserDao = new WorkflowOfUserDao(context.configuration)
  final private val workflowUserAccessDao = new WorkflowUserAccessDao(context.configuration)

  /**
    * This method returns the owner of a workflow
    *
    * @param wid ,  workflow id
    * @return ownerEmail,  the owner's email
    */
  @GET
  @Path("/owner/{wid}")
  def getOwner(@PathParam("wid") wid: Integer): String = {
    userDao.fetchOneByUid(workflowOfUserDao.fetchByWid(wid).get(0).getUid).getEmail
  }

  /**
    * Returns information about all current shared access of the given workflow
    *
    * @param wid workflow id
    * @return a List of email/name/permission
    */
  @GET
  @Path("/list/{wid}")
  def getAccessList(
      @PathParam("wid") wid: Integer
  ): util.List[AccessEntry] = {
    context
      .select(
        USER.EMAIL,
        USER.NAME,
        WORKFLOW_USER_ACCESS.PRIVILEGE
      )
      .from(WORKFLOW_USER_ACCESS)
      .join(USER)
      .on(USER.UID.eq(WORKFLOW_USER_ACCESS.UID))
      .where(
        WORKFLOW_USER_ACCESS.WID
          .eq(wid)
          .and(WORKFLOW_USER_ACCESS.UID.notEqual(workflowOfUserDao.fetchByWid(wid).get(0).getUid))
      )
      .fetchInto(classOf[AccessEntry])
  }

  /**
    * This method shares a workflow to a user with a specific access type
    *
    * @param wid       the given workflow
    * @param email     the email which the access is given to
    * @param privilege the type of Access given to the target user
    * @return rejection if user not permitted to share the workflow or Success Message
    */
  @PUT
  @Path("/grant/{wid}/{email}/{privilege}")
  def grantAccess(
      @PathParam("wid") wid: Integer,
      @PathParam("email") email: String,
      @PathParam("privilege") privilege: String,
      @Auth user: SessionUser
  ): Unit = {
    if (email.equals(user.getEmail)) {
      throw new BadRequestException("You cannot grant access to yourself!")
    }

    if (!hasWriteAccess(wid, user.getUid)) {
      throw new ForbiddenException(s"You do not have permission to modify workflow $wid")
    }

    val userUid = userDao.fetchOneByEmail(email).getUid
    val workflowOwnerUid = context
      .select(WORKFLOW_OF_USER.UID)
      .from(WORKFLOW_OF_USER)
      .where(WORKFLOW_OF_USER.WID.eq(wid))
      .fetchOneInto(classOf[Integer])
    if (userUid == workflowOwnerUid) {
      throw new ForbiddenException("You cannot modify the owner's permissions!")
    }

    try {
      workflowUserAccessDao.merge(
        new WorkflowUserAccess(
          userUid,
          wid,
          PrivilegeEnum.valueOf(privilege)
        )
      )
    } catch {
      case _: NullPointerException =>
        throw new BadRequestException(s"User $email Not Found!")
    }
  }

  /**
    * This method identifies the user access level of the given workflow
    *
    * @param wid   the given workflow
    * @param email the email of the use whose access is about to be removed
    * @return message indicating a success message
    */
  @DELETE
  @Path("/revoke/{wid}/{email}")
  def revokeAccess(
      @PathParam("wid") wid: Integer,
      @PathParam("email") email: String,
      @Auth user: SessionUser
  ): Unit = {
    if (!hasWriteAccess(wid, user.getUid)) {
      throw new ForbiddenException(s"You do not have permission to modify workflow $wid")
    }

    context
      .delete(WORKFLOW_USER_ACCESS)
      .where(
        WORKFLOW_USER_ACCESS.UID
          .eq(userDao.fetchOneByEmail(email).getUid)
          .and(WORKFLOW_USER_ACCESS.WID.eq(wid))
      )
      .execute()
  }
}
