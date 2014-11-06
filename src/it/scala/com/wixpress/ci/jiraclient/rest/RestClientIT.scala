package com.wixpress.ci.jiraclient.rest

import org.specs2.mutable.SpecificationWithJUnit
import com.wixpress.framework.test.http.EmbeddedHttpProbe
import spray.http.{HttpHeader, HttpMethods, HttpResponse, HttpRequest}
import concurrent.duration._
import org.specs2.time.NoTimeConversions
import org.specs2.matcher.{Expectable, Matcher}
import com.wixpress.ci.jiraclient.client.rest.RestClient
import com.wixpress.framework.test.http.EmbeddedHttpProbe
import com.wixpress.framework.test.http.EmbeddedHttpProbe.Handler
import spray.http._
import spray.http.HttpHeaders.{Authorization, `WWW-Authenticate`}
import spray.http.StatusCodes.Unauthorized



/**
 * Created by Uri_Bechar on 10/23/14.
 */
class RestClientIT extends SpecificationWithJUnit with NoTimeConversions {

  val userName = "whiz"
  val password = "kid"
  val probe = new EmbeddedHttpProbe(8080) with BasicAuthentication {
    override val credentials = BasicHttpCredentials(userName, password)

    override def realm: String = "ASdasdasd"
  }

  def anHttpRequest(headers: Matcher[TraversableOnce[(String, String)]], body: Matcher[String]): Matcher[HttpRequest] = {

    headers ^^ { (_: HttpRequest).headers map { case h => (h.name, h.value)} aka "headers" } and
    body ^^ { (_: HttpRequest).entity.data.asString aka "entityAsString" }
//
//    val matcher : Matcher[HttpRequest] = headers ^^ ((r: HttpRequest) => {
//      r.headers map { h : HttpHeader => h.name -> h.value}
//    })
//
//    val matcher2 : Matcher[HttpRequest] = body ^^ ((r: HttpRequest) => {
//      r.entity.data.asString
//    })
//
//    return matcher and matcher2
  }





  step {
    probe.doStart()
  }

  "check get" >> {
    val client = new RestClient(userName, password)
    client.executeGet("http://localhost:8080")
    probe.requests must eventually(retries = 40, sleep = 100.milliseconds){ contain(anHttpRequest(headers = contain("Accept" -> "application/json"),body = contain("") )) }

    success
  }

  "check post" >> {
    val client = new RestClient(userName, password)
    client.executePost("http://localhost:8080", "{\"a\" : \"b\"")
    probe.requests must eventually(retries = 40, sleep = 100.milliseconds){ contain(anHttpRequest(headers = contain("Accept" -> "application/json") and contain("Content-Type" -> "application/json; charset=UTF-8") ,body = contain("{\"a\" : \"b\"") )) }

    success
  }

  "check delete" >> {
    val client = new RestClient(userName, password)
    client.executeDelete("http://localhost:8080")
    probe.requests must eventually(retries = 40, sleep = 100.milliseconds){ contain(anHttpRequest(headers = contain("Accept" -> "application/json"), body = beEqualTo("") )) }

    success
  }


  step {
    probe.doStop()
  }


}

trait BasicAuthentication { self: EmbeddedHttpProbe =>

  def credentials: BasicHttpCredentials
  def realm: String = "My Server"

  private def authorizationHandler: Handler = {
    case HttpRequest(_, _, headers, _, _) if !headers.contains(Authorization(credentials)) => HttpResponse(Unauthorized, headers = List(`WWW-Authenticate`(HttpChallenge("Basic", realm))))
  }

  override abstract def reset(): Unit = {
    self.reset()
    handlers += authorizationHandler
  }
}
