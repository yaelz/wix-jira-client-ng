package com.wixpress.ci.jiraclient.client.rest

import dispatch._,Defaults._
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.Await

/**
 * Created by Uri_Bechar on 10/2/14.
 */
class RestClient {

  def executeGet(endPoint : String): String ={
    val req = url(endPoint) <:< Map("Accept" -> "application/json");
    val res = Http(req OK as.String);
    Await.result(res,5000 millis)
  }



  def executePost(endPoint : String, jsonBody : String): String ={
    var req = url(endPoint)<:< Map("Accept" -> "application/json","Content-Type" -> "application/json; charset=UTF-8");
    req = req.POST.setBody(jsonBody);
    val res = Http(req OK as.String);
    Await.result(res,5000 millis);
  }



}




object XX{
  def main(args: Array[String]): Unit ={
    val rc = new RestClient();
    //val json = rc.executeGet("http://www.isracam.co.il");
    val json = rc.executePost("http://postcatcher.in/catchers/5448ac75767a9f0200000104","{\"aa\" : \"bb\"}");
    println(json)   ;
  }
}
