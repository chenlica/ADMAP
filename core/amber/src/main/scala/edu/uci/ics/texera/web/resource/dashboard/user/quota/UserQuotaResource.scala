package edu.uci.ics.texera.web.resource.dashboard.user.quota

import edu.uci.ics.amber.core.storage.util.mongo.MongoDatabaseManager
import edu.uci.ics.amber.core.storage.util.mongo.MongoDatabaseManager.database
import edu.uci.ics.texera.dao.SqlServer
import edu.uci.ics.texera.auth.SessionUser
import edu.uci.ics.texera.dao.jooq.generated.Tables._
import edu.uci.ics.texera.web.resource.dashboard.user.dataset.utils.DatasetStatisticsUtils.getUserCreatedDatasets
import edu.uci.ics.texera.web.resource.dashboard.user.quota.UserQuotaResource._
import io.dropwizard.auth.Auth
import org.bson.Document

import java.util
import javax.ws.rs._
import javax.ws.rs.core.MediaType
import scala.jdk.CollectionConverters.IterableHasAsScala

object UserQuotaResource {
  final private lazy val context = SqlServer
    .getInstance()
    .createDSLContext()

  case class Workflow(
      userId: Integer,
      workflowId: Integer,
      workflowName: String,
      creationTime: Long,
      lastModifiedTime: Long
  )

  case class DatasetQuota(
      did: Integer,
      name: String,
      creationTime: Long,
      size: Long
  )

  case class MongoStorage(
      workflowName: String,
      size: Double,
      pointer: String,
      eid: Integer
  )

  def getDatabaseSize(collectionNames: Array[MongoStorage]): Array[MongoStorage] = {
    var count = 0

    for (collection <- collectionNames) {
      val stats: Document = database.runCommand(new Document("collStats", collection.pointer))
      collectionNames(count) = MongoStorage(
        collection.workflowName,
        stats.getInteger("totalSize").toDouble,
        collection.pointer,
        collection.eid
      )
      count += 1
    }

    collectionNames
  }

  def getCollectionName(result: String): String = {

    /**
      * Get the Collection Name from
      * {"results":["1_TextInput-operator-6c3be22b-b2e2-4896-891c-cfa849638e5c"]}
      * to
      * 1_TextInput-operator-6c3be22b-b2e2-4896-891c-cfa849638e5c
      */

    var quoteCount = 0
    var name = ""
    for (chr <- result) {
      if (chr == '\"') {
        quoteCount += 1
      } else if (quoteCount == 3) { // collection name starts from the third quote and ends at the fourth quote.
        name += chr
      }
    }

    name
  }

  def getUserCreatedWorkflow(uid: Integer): List[Workflow] = {
    val userWorkflowEntries = context
      .select(
        WORKFLOW_OF_USER.UID,
        WORKFLOW_OF_USER.WID,
        WORKFLOW.NAME,
        WORKFLOW.CREATION_TIME,
        WORKFLOW.LAST_MODIFIED_TIME
      )
      .from(
        WORKFLOW_OF_USER
      )
      .leftJoin(
        WORKFLOW
      )
      .on(
        WORKFLOW.WID.eq(WORKFLOW_OF_USER.WID)
      )
      .where(
        WORKFLOW_OF_USER.UID.eq(uid)
      )
      .fetch()

    userWorkflowEntries
      .map(workflowRecord => {
        Workflow(
          workflowRecord.get(WORKFLOW_OF_USER.UID),
          workflowRecord.get(WORKFLOW_OF_USER.WID),
          workflowRecord.get(WORKFLOW.NAME),
          workflowRecord.get(WORKFLOW.CREATION_TIME).getTime,
          workflowRecord.get(WORKFLOW.LAST_MODIFIED_TIME).getTime
        )
      })
      .asScala
      .toList
  }

  def getUserAccessedWorkflow(uid: Integer): util.List[Integer] = {
    val availableWorkflowIds = context
      .select(
        WORKFLOW_USER_ACCESS.WID
      )
      .from(
        WORKFLOW_USER_ACCESS
      )
      .where(
        WORKFLOW_USER_ACCESS.UID.eq(uid)
      )
      .fetchInto(classOf[Integer])

    availableWorkflowIds
  }

  def getUserMongoDBSize(uid: Integer): Array[MongoStorage] = {
    val collectionNames = context
      .select(
        WORKFLOW_EXECUTIONS.RESULT,
        WORKFLOW.NAME,
        WORKFLOW_EXECUTIONS.EID
      )
      .from(
        WORKFLOW_EXECUTIONS
      )
      .leftJoin(
        WORKFLOW_VERSION
      )
      .on(WORKFLOW_EXECUTIONS.VID.eq(WORKFLOW_VERSION.VID))
      .leftJoin(
        WORKFLOW
      )
      .on(WORKFLOW_VERSION.WID.eq(WORKFLOW.WID))
      .where(
        WORKFLOW_EXECUTIONS.UID
          .eq(uid)
          .and(WORKFLOW_EXECUTIONS.RESULT.notEqual(""))
          .and(WORKFLOW_EXECUTIONS.RESULT.isNotNull)
      )
      .fetch()

    val collections = collectionNames
      .map(result => {
        MongoStorage(
          result.get(WORKFLOW.NAME),
          0.0,
          getCollectionName(result.get(WORKFLOW_EXECUTIONS.RESULT)),
          result.get(WORKFLOW_EXECUTIONS.EID)
        )
      })
      .asScala
      .toArray

    val collectionSizes = getDatabaseSize(collections)

    collectionSizes
  }

  def deleteMongoCollection(collectionName: String): Unit = {
    MongoDatabaseManager.dropCollection(collectionName)
    val resultName = "{\"results\":[\"" + collectionName + "\"]}"
    context
      .update(WORKFLOW_EXECUTIONS)
      .set(WORKFLOW_EXECUTIONS.RESULT, null.asInstanceOf[String])
      .where(WORKFLOW_EXECUTIONS.RESULT.eq(resultName))
      .execute()
  }
}

@Path("/quota")
class UserQuotaResource {

  @GET
  @Path("/created_datasets")
  @Produces(Array(MediaType.APPLICATION_JSON))
  def getCreatedDatasets(@Auth current_user: SessionUser): List[DatasetQuota] = {
    getUserCreatedDatasets(current_user.getUid)
  }

  @GET
  @Path("/created_workflows")
  @Produces(Array(MediaType.APPLICATION_JSON))
  def getCreatedWorkflow(@Auth current_user: SessionUser): List[Workflow] = {
    getUserCreatedWorkflow(current_user.getUid)
  }

  @GET
  @Path("/access_workflows")
  @Produces(Array(MediaType.APPLICATION_JSON))
  def getAccessedWorkflow(@Auth current_user: SessionUser): util.List[Integer] = {
    getUserAccessedWorkflow(current_user.getUid)
  }

  @GET
  @Path("/mongodb_size")
  @Produces(Array(MediaType.APPLICATION_JSON))
  def mongoDBSize(@Auth current_user: SessionUser): Array[MongoStorage] = {
    getUserMongoDBSize(current_user.getUid)
  }

  @DELETE
  @Path("/deleteCollection/{collectionName}")
  def deleteCollection(@PathParam("collectionName") collectionName: String): Unit = {
    deleteMongoCollection(collectionName)
  }
}
