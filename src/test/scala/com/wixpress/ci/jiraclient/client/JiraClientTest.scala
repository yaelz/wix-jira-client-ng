package com.wixpress.ci.jiraclient.client

import java.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.wixpress.ci.jiraclient.client.rest.RestClient
import com.wixpress.ci.jiraclient.client.tests.utils.ServerResponseData
import com.wixpress.ci.jiraclient.model.{JiraComment, Issue}
import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit

/**
 * Created by Uri_Bechar on 10/30/14.
 */
class JiraClientTest extends SpecificationWithJUnit with Mockito {
  val userName = "whiz"
  val password = "kid"
//  "getIssue" should {
//    "contains" in {
//       val comment : Comment = new Comment(null,"whiz kid",null,null,null,null,null,12L)
//       val comments : util.ArrayList[Comment] = new util.ArrayList[Comment]()
//       comments.add(comment)
//       val issue = new Issue("summary",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,comments,null,null,null,null,null,null,null,null,null)
//       val restClient = mock[JiraRestClient]
//       val issueClient = mock[IssueRestClient]
//       restClient.getIssueClient() returns issueClient
//       val progressMonitor = new NullProgressMonitor()
//       issueClient.getIssue("issue1", progressMonitor) returns issue
//       val internalRestClient = new RestClient(userName, password)
//       val jiraClient = new JiraClient(restClient, progressMonitor,internalRestClient,"")
//
//       val jiraComment: Comment = new Comment(12L, "whiz kid")
//       val commentsLst : List[Comment] = jiraComment :: Nil
//       val jiraIssue : Issue = new Issue(commentsLst)
//
//       val retIssue = jiraClient.getIssue("issue1")
//       retIssue must equalTo(jiraIssue)
//
//    }
//  }
    "addComment" should {
      "contains" in {
        val issueId = "JIR-1"
        val commentBody = "coment 1"
        val commentId = "12345"
        val uri = "http://whiz.kid"
        val path = "/rest/api/2/issue/"+issueId+"/comment"
        val commentToAdd  = new JiraComment(body=commentBody)

        val responseFromServer = ServerResponseData.addCommentResponseData(commentId,commentBody);
        val expected = new JiraComment(commentId,commentBody)
        val restClient = mock[RestClient]

        val mapper = new ObjectMapper()
        mapper.registerModule(DefaultScalaModule)
        val jsonCommentBodyString = mapper.writeValueAsString(commentToAdd)

        restClient.executePost(uri+path, jsonCommentBodyString) returns responseFromServer
        val jiraClient = new JiraClient(restClient, uri)
        val retVal = jiraClient.addComment(issueId, commentToAdd);

        retVal must equalTo(expected)
      }
    }


  "deleteComment" should {
    "contains" in {
      val issueId = "JIR-1"
      val commentId = "12345"
      val commentBody = "coment 1"
      val uri = "http://whiz.kid"
      val path = "/rest/api/2/issue/"+issueId+"/comment/"+commentId
      val commentToDelete  = new JiraComment(commentId, commentBody)

      val restClient = mock[RestClient]
      val jiraClient = new JiraClient(restClient, uri)
      jiraClient.deleteComment(issueId, commentToDelete)
      there was one(restClient).executeDelete(uri + path)

      success
    }
  }

//  "deleteAllComments" should {
//    "contains" in {
//      val progressMonitor = null
//      val internalRestClient = mock[RestClient]
//
//
//      val comment : Comment = new Comment(null,"whiz kid needs",null,null,null,null,null,1L)
//      val comment2 : Comment = new Comment(null,"whiz kid a",null,null,null,null,null,1L)
//      val comment3 : Comment = new Comment(null,"whiz kid wetsuit",null,null,null,null,null,1L)
//
//      val comments : util.ArrayList[Comment] = new util.ArrayList[Comment]()
//      comments.add(comment)
//      comments.add(comment2)
//      comments.add(comment3)
//      val issue = new Issue("summary",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,comments,null,null,null,null,null,null,null,null,null)
//      val restClient = mock[JiraRestClient]
//      val issueClient = mock[IssueRestClient]
//      restClient.getIssueClient() returns issueClient
//      issueClient.getIssue("issue1", progressMonitor) returns issue
//
//
//      val uri = "http://loaclhost:8080"
//      val jiraClient = new JiraClient(restClient, progressMonitor,internalRestClient, uri)
//      val numOfDeletedComments: Int = jiraClient.deleteAllComments("issue1")
//      there was three (internalRestClient).executeDelete(uri + "/issue1/comment/1")
//      numOfDeletedComments must equalTo(3)
//    }
//  }
}
