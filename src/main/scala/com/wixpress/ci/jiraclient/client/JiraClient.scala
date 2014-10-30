package com.wixpress.ci.jiraclient.client



import com.atlassian.jira.rest.client.{ProgressMonitor, JiraRestClient}
import com.atlassian.jira.rest.client.domain.Issue


/**
 * Created by Uri_Bechar on 10/30/14.
 */
class JiraClient private[client] (restClient: JiraRestClient,progressMonitor: ProgressMonitor){

  def getIssue(issueId: String) : Issue = {
    restClient.getIssueClient().getIssue(issueId,progressMonitor )
  }




}
