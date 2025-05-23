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

package edu.uci.ics.amber.engine.e2e

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import akka.util.Timeout
import com.twitter.util.{Await, Promise}
import com.typesafe.scalalogging.Logger
import edu.uci.ics.amber.clustering.SingleNodeListener
import edu.uci.ics.amber.core.workflow.{PortIdentity, WorkflowContext}
import edu.uci.ics.amber.engine.architecture.controller.{ControllerConfig, ExecutionStateUpdate}
import edu.uci.ics.amber.engine.architecture.rpc.controlcommands.EmptyRequest
import edu.uci.ics.amber.engine.architecture.rpc.controlreturns.WorkflowAggregatedState.COMPLETED
import edu.uci.ics.amber.engine.common.AmberRuntime
import edu.uci.ics.amber.engine.common.client.AmberClient
import edu.uci.ics.amber.operator.{LogicalOp, TestOperators}
import edu.uci.ics.texera.workflow.LogicalLink
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpecLike

import scala.concurrent.duration._

class PauseSpec
    extends TestKit(ActorSystem("PauseSpec", AmberRuntime.akkaConfig))
    with ImplicitSender
    with AnyFlatSpecLike
    with BeforeAndAfterAll {

  implicit val timeout: Timeout = Timeout(5.seconds)

  val logger = Logger("PauseSpecLogger")

  override def beforeAll(): Unit = {
    system.actorOf(Props[SingleNodeListener](), "cluster-info")
  }

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  def shouldPause(
      operators: List[LogicalOp],
      links: List[LogicalLink]
  ): Unit = {
    val workflow =
      TestUtils.buildWorkflow(operators, links, new WorkflowContext())
    val client =
      new AmberClient(
        system,
        workflow.context,
        workflow.physicalPlan,
        ControllerConfig.default,
        error => {}
      )
    val completion = Promise[Unit]()
    client
      .registerCallback[ExecutionStateUpdate](evt => {
        if (evt.state == COMPLETED) {
          completion.setDone()
        }
      })
    Await.result(client.controllerInterface.startWorkflow(EmptyRequest(), ()))
    Await.result(client.controllerInterface.pauseWorkflow(EmptyRequest(), ()))
    Thread.sleep(4000)
    Await.result(client.controllerInterface.resumeWorkflow(EmptyRequest(), ()))
    Thread.sleep(400)
    Await.result(client.controllerInterface.pauseWorkflow(EmptyRequest(), ()))
    Thread.sleep(4000)
    Await.result(client.controllerInterface.resumeWorkflow(EmptyRequest(), ()))
    Await.result(completion)
  }

  "Engine" should "be able to pause csv workflow" in {
    val csvOpDesc = TestOperators.mediumCsvScanOpDesc()
    logger.info(s"csv-id ${csvOpDesc.operatorIdentifier}")
    shouldPause(
      List(csvOpDesc),
      List()
    )
  }

  "Engine" should "be able to pause csv->keyword workflow" in {
    val csvOpDesc = TestOperators.mediumCsvScanOpDesc()
    val keywordOpDesc = TestOperators.keywordSearchOpDesc("Region", "Asia")
    logger.info(
      s"csv-id ${csvOpDesc.operatorIdentifier}, keyword-id ${keywordOpDesc.operatorIdentifier}"
    )
    shouldPause(
      List(csvOpDesc, keywordOpDesc),
      List(
        LogicalLink(
          csvOpDesc.operatorIdentifier,
          PortIdentity(),
          keywordOpDesc.operatorIdentifier,
          PortIdentity()
        )
      )
    )
  }

}
