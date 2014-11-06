package com.wixpress.ci.jiraclient.client



import com.atlassian.jira.rest.client.{ProgressMonitor, JiraRestClient}
import com.atlassian.jira.rest.client.domain.{Comment, Issue}
import com.wixpress.ci.jiraclient.client.rest.RestClient
import com.wixpress.ci.jiraclient.model.{JiraComment, JiraIssue}
import scala.collection.JavaConverters._;


/**
 * Created by Uri_Bechar on 10/30/14.
 */class JiraClient private[client] (restClient: JiraRestClient,progressMonitor: ProgressMonitor, internalRestClient: RestClient, uri: String){

  def getIssue(issueId: String) : JiraIssue = {
    val issue = restClient.getIssueClient().getIssue(issueId,progressMonitor)
    val comments : List[Comment] = issue.getComments.asScala.toList
    val jiraComments : List[JiraComment] = comments.map(comment => new JiraComment(comment.getId, comment.getBody))
    new JiraIssue(jiraComments)
  }

  def deleteComment(issueId : String, commentId: Long): Unit = {
    internalRestClient.executeDelete(uri + "/" + issueId + "/comment/" + commentId)
  }


}
