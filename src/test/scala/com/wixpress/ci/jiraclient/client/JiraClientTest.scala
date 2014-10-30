package com.wixpress.ci.jiraclient.client

import com.atlassian.jira.rest.client.domain.Issue
import com.atlassian.jira.rest.client.{IssueRestClient, JiraRestClient, NullProgressMonitor}
import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit

/**
 * Created by Uri_Bechar on 10/30/14.
 */
class JiraClientTest extends SpecificationWithJUnit with Mockito {
  "xxx" should{
    "contains" in {

       val issue = new Issue("summary",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null)
       val restClient = mock[JiraRestClient]
       val issueClient = mock[IssueRestClient]
       restClient.getIssueClient() returns issueClient
       val progressMonitor = new NullProgressMonitor();
       issueClient.getIssue("issue1", progressMonitor ) returns issue
       val jiraClient = new JiraClient(restClient,progressMonitor);
       val retIssue = jiraClient.getIssue("issue1");
       retIssue must equalTo( issue)

    }
  }
}
