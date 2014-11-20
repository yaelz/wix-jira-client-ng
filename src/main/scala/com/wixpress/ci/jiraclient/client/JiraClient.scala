package com.wixpress.ci.jiraclient.client


import java.net.URI

import com.fasterxml.jackson.databind.{DeserializationFeature, DeserializationConfig, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.wixpress.ci.jiraclient.VersionBuildUtil
import com.wixpress.ci.jiraclient.client.rest.RestClient
import com.wixpress.ci.jiraclient.model.EventType.EventTypeVal
import com.wixpress.ci.jiraclient.model.jirarequest.CreateVersionRequest
import com.wixpress.ci.jiraclient.model.{EventType, Version, JiraComment, Issue}
import org.joda.time.DateTime
import scala.collection.JavaConverters._;


/**
 * Created by Uri_Bechar on 10/30/14.
 */class JiraClient private[client] (restClient: RestClient, uri: String){

  def getIssue(issueId: String) : Issue = {
    val path : String =uri+ "/rest/api/2/issue/"+issueId
    val result = restClient.executeGet(path)
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.readValue(result,classOf[Issue])
  }

  def deleteComment(issueId : String, comment: JiraComment): Unit = {
    restClient.executeDelete(uri + "/rest/api/2/issue/" + issueId + "/comment/" + comment.id)
  }

  def deleteAllComments(issueId : String): Int = {
    val issue = this.getIssue(issueId)
    issue.fields.comment.comments.map(comment => this.deleteComment(issueId, comment))
    issue.fields.comment.comments.length
  }

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


  def createVersion(projectKey:String, buildName:String, version:String, eventType:EventTypeVal): Version = {
    val path : String =uri+ "/rest/api/latest/version/"
    val versionBuildName = VersionBuildUtil.createBuildVersionName(buildName,version)

    def handleCreateVersion(eventType:EventTypeVal) : Version = {
      eventType match {
        case EventType.SNAPSHOT => handleCreateSnapShotVersion(projectKey,versionBuildName,path)
        case _ => handleCreateSnapShotVersion(projectKey,versionBuildName,path)
      }
    }
    handleCreateVersion(eventType)
  }

  private def handleCreateSnapShotVersion(projectKey:String,versionBuildName:String,path:String) : Version = {
    val desc = "auto created version by lifecycle  "
    val date = DateTime.now.toString("dd/MMM/yy")
    val requestBody = new CreateVersionRequest(desc,versionBuildName,projectKey,false,false, date)
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    val jsonString = mapper.writeValueAsString(requestBody)
    val result = restClient.executePost(path,jsonString)
    mapper.readValue[Version](result, classOf[Version])
  }

}
