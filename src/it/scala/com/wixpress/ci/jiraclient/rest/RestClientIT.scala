package com.wixpress.ci.jiraclient.rest

import org.specs2.mutable.SpecificationWithJUnit
import com.wixpress.framework.test.http.EmbeddedHttpProbe
import spray.http.{HttpHeader, HttpMethods, HttpResponse, HttpRequest}
import concurrent.duration._
import org.specs2.time.NoTimeConversions
import org.specs2.matcher.{Expectable, Matcher}
import com.wixpress.ci.jiraclient.client.rest.RestClient


/**
 * Created by Uri_Bechar on 10/23/14.
 */
class RestClientIT extends SpecificationWithJUnit with NoTimeConversions {

  val probe = new EmbeddedHttpProbe(8080)

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
    val client = new RestClient()
    client.executeGet("http://localhost:8080")
    probe.requests must eventually(retries = 40, sleep = 100.milliseconds){ contain(anHttpRequest(headers = contain("Accept" -> "application/json"),body = contain("") )) }

    success
  }

  "check post" >> {
    val client = new RestClient()
    client.executePost("http://localhost:8080","{\"a\" : \"b\"")
    probe.requests must eventually(retries = 40, sleep = 100.milliseconds){ contain(anHttpRequest(headers = contain("Accept" -> "application/json") and contain("Content-Type" -> "application/json; charset=UTF-8") ,body = contain("{\"a\" : \"b222\"") )) }

    success
  }


  step {
    probe.doStop()
  }


}
