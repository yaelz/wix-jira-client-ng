package com.wixpress.ci.jiraclient.client.rest

import dispatch._,Defaults._
import org.apache.commons.net.util.Base64
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.Await

/**
 * Created by Uri_Bechar on 10/2/14.
 */
class RestClient (username: String, password: String){

  private val basicAuthString = createBasicAuthenticationString()

  private def createBasicAuthenticationString() : String={
    def bytes = Base64.encodeBase64((username + ":" + password).getBytes);
    val base64 = new String(bytes)
    "Basic "+base64
  }

  def executeGet(endPoint : String): String ={
    val req = url(endPoint) <:< Map("Accept" -> "application/json","Authorization" -> basicAuthString)
    val res = Http(req OK as.String)
    Await.result(res,5000 millis)
  }



  def executePost(endPoint : String, jsonBody : String): String ={
    var req = url(endPoint)<:< Map("Accept" -> "application/json","Content-Type" -> "application/json; charset=UTF-8","Authorization" -> basicAuthString)
    req = req.POST.setBody(jsonBody)
    val res = Http(req OK as.String)
    Await.result(res,5000 millis)
  }


  def executeDelete(endPoint : String): Unit ={
    var req = url(endPoint) <:< Map("Accept" -> "application/json","Authorization" -> basicAuthString)
    req = req.DELETE
    val res = Http(req OK as.String)
    Await.result(res,5000 millis)
  }



}

