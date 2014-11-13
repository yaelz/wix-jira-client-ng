package com.wixpress.ci.jiraclient.client

import com.wixpress.ci.jiraclient.client.rest.RestClient

/**
 * Created by Uri_Bechar on 10/30/14.
 */
class JiraClientFactory(uri: String, username: String, password: String) {
  def create(): JiraClient ={
    val restClient = new RestClient(username,password)
    new JiraClient(restClient, uri)
  }
}
