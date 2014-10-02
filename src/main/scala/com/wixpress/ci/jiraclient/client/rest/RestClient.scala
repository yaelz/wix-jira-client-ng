package com.wixpress.ci.jiraclient.client.rest

import dispatch._,Defaults._
import scala.concurrent.duration._

import scala.concurrent.Await

/**
 * Created by Uri_Bechar on 10/2/14.
 */
class RestClient {

  def executeGet(endPoint : String): String ={
    val req = url(endPoint);
    val res = Http(req OK as.String);
    Await.result(res,5000 millis)
  }




}

object XX{
  def main(args: Array[String]): Unit ={
    val rc = new RestClient();
    val json = rc.executeGet("http://www.isracam.co.il");
    println(json)   ;
  }
}
