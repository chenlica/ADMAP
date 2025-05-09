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

package edu.uci.ics.texera.web.resource.dashboard.user.project

import edu.uci.ics.texera.auth.SessionUser
import edu.uci.ics.texera.dao.SqlServer
import edu.uci.ics.texera.web.model.common.AccessEntry
import edu.uci.ics.texera.dao.jooq.generated.Tables.{
  DATASET_USER_ACCESS,
  PROJECT_USER_ACCESS,
  USER,
  WORKFLOW_USER_ACCESS
}
import edu.uci.ics.texera.dao.jooq.generated.enums.PrivilegeEnum
import edu.uci.ics.texera.dao.jooq.generated.tables.daos.{ProjectDao, ProjectUserAccessDao, UserDao}
import edu.uci.ics.texera.dao.jooq.generated.tables.pojos.ProjectUserAccess
import edu.uci.ics.texera.web.resource.dashboard.user.project.ProjectAccessResource.userHasWriteAccess
import io.dropwizard.auth.Auth
import org.jooq.DSLContext

import java.util
import javax.annotation.security.RolesAllowed
import javax.ws.rs._
import javax.ws.rs.core.MediaType

object ProjectAccessResource {
  final private val context: DSLContext = SqlServer
    .getInstance()
    .createDSLContext()

  def userHasWriteAccess(pid: Integer, uid: Integer): Boolean = {
    getProjectAccessPrivilege(pid, uid) == PrivilegeEnum.WRITE
  }

  def getProjectAccessPrivilege(pid: Integer, uid: Integer): PrivilegeEnum = {
    Option(
      context
        .select(PROJECT_USER_ACCESS.PRIVILEGE)
        .from(WORKFLOW_USER_ACCESS)
        .where(
          PROJECT_USER_ACCESS.PID
            .eq(pid)
            .and(DATASET_USER_ACCESS.UID.eq(uid))
        )
        .fetchOneInto(classOf[PrivilegeEnum])
    ).getOrElse(PrivilegeEnum.NONE)
  }
}

@Produces(Array(MediaType.APPLICATION_JSON))
@RolesAllowed(Array("REGULAR", "ADMIN"))
@Path("/access/project")
class ProjectAccessResource() {
  final private val context: DSLContext = SqlServer
    .getInstance()
    .createDSLContext()
  final private val userDao = new UserDao(context.configuration())
  final private val projectDao = new ProjectDao(context.configuration)
  final private val projectUserAccessDao = new ProjectUserAccessDao(context.configuration)

  /**
    * This method returns the owner of a project
    *
    * @param pid ,  project id
    * @return ownerEmail,  the owner's email
    */
  @GET
  @Path("/owner/{pid}")
  def getOwner(@PathParam("pid") pid: Integer): String = {
    userDao.fetchOneByUid(projectDao.fetchOneByPid(pid).getOwnerId).getEmail
  }

  /**
    * Returns information about all current shared access of the given project
    *
    * @param pid project id
    * @return a List of email/permission pair
    */
  @GET
  @Path("/list/{pid}")
  def getAccessList(
      @PathParam("pid") pid: Integer
  ): util.List[AccessEntry] = {
    context
      .select(
        USER.EMAIL,
        USER.NAME,
        PROJECT_USER_ACCESS.PRIVILEGE
      )
      .from(PROJECT_USER_ACCESS)
      .join(USER)
      .on(USER.UID.eq(PROJECT_USER_ACCESS.UID))
      .where(
        PROJECT_USER_ACCESS.PID
          .eq(pid)
          .and(PROJECT_USER_ACCESS.UID.notEqual(projectDao.fetchOneByPid(pid).getOwnerId))
      )
      .fetchInto(classOf[AccessEntry])
  }

  /**
    * This method shares a project to a user with a specific access type
    *
    * @param pid       the given project
    * @param email     the email which the access is given to
    * @param privilege the type of Access given to the target user
    * @return rejection if user not permitted to share the project or Success Message
    */
  @PUT
  @Path("/grant/{pid}/{email}/{privilege}")
  def grantAccess(
      @PathParam("pid") pid: Integer,
      @PathParam("email") email: String,
      @PathParam("privilege") privilege: String,
      @Auth user: SessionUser
  ): Unit = {
    if (!userHasWriteAccess(pid, user.getUid)) {
      throw new ForbiddenException(s"You do not have permission to modify project $pid")
    }

    projectUserAccessDao.merge(
      new ProjectUserAccess(
        userDao.fetchOneByEmail(email).getUid,
        pid,
        PrivilegeEnum.valueOf(privilege)
      )
    )
  }

  /**
    * Revoke a user's access to a file
    *
    * @param pid   the id of the file
    * @param email the email of target user whose access is about to be revoked
    * @return A successful resp if granted, failed resp otherwise
    */
  @DELETE
  @Path("/revoke/{pid}/{email}")
  def revokeAccess(
      @PathParam("pid") pid: Integer,
      @PathParam("email") email: String,
      @Auth user: SessionUser
  ): Unit = {
    if (!userHasWriteAccess(pid, user.getUid)) {
      throw new ForbiddenException(s"You do not have permission to modify project $pid")
    }

    context
      .delete(PROJECT_USER_ACCESS)
      .where(
        PROJECT_USER_ACCESS.UID
          .eq(userDao.fetchOneByEmail(email).getUid)
          .and(PROJECT_USER_ACCESS.PID.eq(pid))
      )
      .execute()
  }
}
