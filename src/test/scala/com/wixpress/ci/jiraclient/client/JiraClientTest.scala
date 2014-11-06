package com.wixpress.ci.jiraclient.client

import java.util

import com.atlassian.jira.rest.client.domain.{Comment, Issue}
import com.atlassian.jira.rest.client.{IssueRestClient, JiraRestClient, NullProgressMonitor}
import com.wixpress.ci.jiraclient.client.rest.RestClient
import com.wixpress.ci.jiraclient.model.{JiraComment, JiraIssue}
import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit

/**
 * Created by Uri_Bechar on 10/30/14.
 */
class JiraClientTest extends SpecificationWithJUnit with Mockito {
  val userName = "whiz"
  val password = "kid"
  "getIssue" should{
    "contains" in {
       val comment : Comment = new Comment(null,"whiz kid",null,null,null,null,null,12L)
       val comments : util.ArrayList[Comment] = new util.ArrayList[Comment]()
       comments.add(comment)
       val issue = new Issue("summary",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,comments,null,null,null,null,null,null,null,null,null)
       val restClient = mock[JiraRestClient]
       val issueClient = mock[IssueRestClient]
       restClient.getIssueClient() returns issueClient
       val progressMonitor = new NullProgressMonitor()
       issueClient.getIssue("issue1", progressMonitor) returns issue
       val internalRestClient = new RestClient(userName, password)
       val jiraClient = new JiraClient(restClient, progressMonitor,internalRestClient,"")

       val jiraComment: JiraComment = new JiraComment(12L, "whiz kid")
       val commentsLst : List[JiraComment] = jiraComment :: Nil
       val jiraIssue : JiraIssue = new JiraIssue(commentsLst)
      
       val retIssue = jiraClient.getIssue("issue1")
       retIssue must equalTo(jiraIssue)

    }
  }

  "deleteComment" should{
    "contains" in {
      val restClient = null
      val progressMonitor = null
      val internalRestClient = mock[RestClient]
      val uri = "http://loaclhost:8080"
      val jiraClient = new JiraClient(restClient, progressMonitor,internalRestClient, uri)
      jiraClient.deleteComment("issue1", 1L)
      //executeDelete
      there was one (internalRestClient).executeDelete(uri + "/issue1/comment/1")
      ///rest/api/2/comment/{commentId}/properties/{propertyKey}
    }
  }
}
