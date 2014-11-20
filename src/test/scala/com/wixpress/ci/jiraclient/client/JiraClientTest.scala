package com.wixpress.ci.jiraclient.client

import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import scala.collection.mutable.ListBuffer
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.wixpress.ci.jiraclient.client.rest.RestClient
import com.wixpress.ci.jiraclient.client.tests.utils.ServerResponseData
import com.wixpress.ci.jiraclient.model._
import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit

/**
 * Created by Uri_Bechar on 10/30/14.
 */
class JiraClientTest extends SpecificationWithJUnit with Mockito {
  val userName = "whiz"
  val password = "kid"
  "getIssue" should {
    "contains" in {
      val issueKey = "JIR-1"
      val issueId = "74538"
      val uri = "http://whiz.kid"
      val path = "/rest/api/2/issue/"+issueKey

      val responseFromServer = ServerResponseData.getIssueResponseData(issueKey)
      val restClient = mock[RestClient]
      restClient.executeGet(uri + path) returns responseFromServer
      val jiraClient = new JiraClient(restClient, uri)
      val retVal = jiraClient.getIssue(issueKey)

      val firstComment = new JiraComment("255405","gdfg")
      val secondComment = new JiraComment("255410","vbcbcv")
      val commentList : List[JiraComment] = List(firstComment, secondComment)

      val comment = new Comment(commentList)
      val fields = new Fields(comment)
      val expected = new Issue(issueId, issueKey, fields)

      retVal must equalTo(expected)
    }
  }
    "addComment" should {
      "contains" in {
        val issueKey = "JIR-1"
        val commentBody = "coment 1"
        val commentId = "12345"
        val uri = "http://whiz.kid"
        val path = "/rest/api/2/issue/"+issueKey+"/comment"
        val commentToAdd  = new JiraComment(body=commentBody)

        val responseFromServer = ServerResponseData.addCommentResponseData(commentId, commentBody)
        val expected = new JiraComment(commentId, commentBody)
        val restClient = mock[RestClient]

        val mapper = new ObjectMapper()
        mapper.registerModule(DefaultScalaModule)
        val jsonCommentBodyString = mapper.writeValueAsString(commentToAdd)

        restClient.executePost(uri+path, jsonCommentBodyString) returns responseFromServer
        val jiraClient = new JiraClient(restClient, uri)
        val retVal = jiraClient.addComment(issueKey, commentToAdd)

        retVal must equalTo(expected)
      }
    }


  "deleteComment" should {
    "contains" in {
      val issueKey = "JIR-1"
      val commentId = "12345"
      val commentBody = "coment 1"
      val uri = "http://whiz.kid"
      val path = "/rest/api/2/issue/"+issueKey+"/comment/"+commentId
      val commentToDelete  = new JiraComment(commentId, commentBody)

      val restClient = mock[RestClient]
      val responseFromServer = ServerResponseData.addCommentResponseData(commentId, commentBody)
      val jiraClient = new JiraClient(restClient, uri)
      jiraClient.deleteComment(issueKey, commentToDelete)
      there was one(restClient).executeDelete(uri + path)

      success
    }
  }

  "deleteAllComments" should {
    "contains" in {
      val issueKey = "JIR-1"
      val issueId = "74538"
      val uri = "http://whiz.kid"
      val path = "/rest/api/2/issue/"+issueKey
      val responseFromServer = ServerResponseData.getIssueResponseData(issueKey)
      val restClient = mock[RestClient]
      restClient.executeGet(uri + path) returns responseFromServer
      val jiraClient = new JiraClient(restClient, uri)
      val numberOfDeleted = jiraClient.deleteAllComments(issueKey)
      numberOfDeleted must equalTo(2)
    }
  }


  "createVersion" should {
    "contains" in {
      val uri = "http://whiz.kid"
      val path = "/rest/api/latest/version/"
      val restClient = mock[RestClient]
      val expected = new Version("https://jira.wixpress.com/rest/api/latest/version/28868","28868","auto created version by lifecycle","buildName11-111111.1",false,false,14403,"2014-11-20",false,"20/Nov/14")
      val responseFromServer = ServerResponseData.getCreateVersionResponse()
      val jiraClient = new JiraClient(restClient, uri)
      val projectKey = "JIR"
      val buildName = "buildName11"
      val versionString = "111111.1"
      val eventType = EventType.SNAPSHOT
      restClient.executePost(any[String],any[String]) returns responseFromServer
      val version = jiraClient.createVersion(projectKey, buildName, versionString, eventType)
      version must equalTo(expected)
      //
      //Shmool adds it to the DB?
    }
  }
}
