package com.wixpress.ci.jiraclient.client


import java.net.URI

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.wixpress.ci.jiraclient.client.rest.RestClient
import com.wixpress.ci.jiraclient.model.{JiraComment, Issue}
import scala.collection.JavaConverters._;


/**
 * Created by Uri_Bechar on 10/30/14.
 */class JiraClient private[client] (restClient: RestClient, uri: String){

  def getIssue(issueId: String) : Issue = {
    val path : String =uri+ "/rest/api/2/issue/"+issueId
    val result = restClient.executeGet(path)
//    println(result)
    null
  }

  def deleteComment(issueId : String, comment: JiraComment): Unit = {
    restClient.executeDelete(uri + "/rest/api/2/issue/" + issueId + "/comment/" + comment.id)
  }

//  def deleteAllComments(issueId : String): Int = {
//    val issue = this.getIssue(issueId)
//    issue.fields.comment.comments.map(comment => this.deleteComment(issueId, comment.id))
//    issue.fields.comment.comments.length
//  }

  def addComment(issueId : String, jiraComment : JiraComment): JiraComment ={
    val path : String =uri+ "/rest/api/2/issue/"+issueId+"/comment"
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    val jsonString = mapper.writeValueAsString(jiraComment)
    val result = restClient.executePost(path,jsonString)
    val map  = mapper.readValue[Map[String, String]](result, classOf[Map[String, String]])
    val id  = map.get("id").get
    new JiraComment(id, jiraComment.body)
  }


}
