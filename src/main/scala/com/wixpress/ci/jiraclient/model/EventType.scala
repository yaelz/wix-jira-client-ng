package com.wixpress.ci.jiraclient.model

/**
 * Created by Uri_Bechar on 11/20/14.
 */
object EventType {
  sealed trait EventTypeVal
  case object GA extends EventTypeVal
  case object RC extends EventTypeVal
  case object TESTBED extends EventTypeVal
  case object ROLLBACK extends EventTypeVal
  case object TESTBED_ROLLBACK extends EventTypeVal
  case object SNAPSHOT extends EventTypeVal
  val eventType = Seq(GA, RC, TESTBED, ROLLBACK, TESTBED_ROLLBACK,SNAPSHOT)
}
