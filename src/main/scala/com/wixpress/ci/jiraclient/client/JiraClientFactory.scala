package com.wixpress.ci.jiraclient.client

import java.net.URI

import com.atlassian.jira.rest.client.NullProgressMonitor
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory
import com.wixpress.ci.jiraclient.client.rest.RestClient

/**
 * Created by Uri_Bechar on 10/30/14.
 */
class JiraClientFactory(uri: String, username: String, password: String) {
  val jerseyJiraRestClientFactory = new JerseyJiraRestClientFactory();

  def create(): JiraClient ={
    val progressMonitor = new NullProgressMonitor()
    val restClient = jerseyJiraRestClientFactory.createWithBasicHttpAuthentication(new URI(uri), username, password);
    val internalRestClient = new RestClient(username,password)
    new JiraClient(restClient,progressMonitor,internalRestClient, uri)
  }
}
