package com.wixpress.ci.jiraclient.client


import java.net.URI

import com.atlassian.jira.rest.client.{ProgressMonitor, JiraRestClient}
import com.atlassian.jira.rest.client.domain.{Visibility, Comment, Issue}
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

  def deleteAllComments(issueId : String): Int = {
    val issue = this.getIssue(issueId)
    issue.comments.map(comment => this.deleteComment(issueId, comment.id))
    issue.comments.length
  }

//  def addComment(issueId : String, commentString : String): Unit ={
//    val path : String =uri+ "/rest/api/2/issue/"+issueId+"/comment"
//    val _uri = new URI(path)
//
//    val visability = new Visibility(Visibility.Type.ROLE,"Administrators")
//    val comment = new Comment(null,commentString,null,null,null,null,visability,null)
//    restClient.getIssueClient().addComment(progressMonitor,_uri,comment)
//  }


}
