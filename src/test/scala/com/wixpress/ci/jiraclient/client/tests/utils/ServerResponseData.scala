package com.wixpress.ci.jiraclient.client.tests.utils

/**
 * Created by Uri_Bechar on 11/13/14.
 */
object ServerResponseData {
  def addCommentResponseData(commentId: String, commentBody: String) : String = {
     "{\"self\":\"https://jira.wixpress.com/rest/api/2/issue/74538/comment/255580\",\"id\":\""+commentId+"\",\"author\":{\"self\":\"https://jira.wixpress.com/rest/api/2/user?username=yaelz\",\"name\":\"yaelz\",\"emailAddress\":\"yaelz@wix.com\",\"avatarUrls\":{\"16x16\":\"https://secure.gravatar.com/avatar/414cbe5d58b02effaf0fdb3cb702e005?d=mm&s=16\",\"24x24\":\"https://secure.gravatar.com/avatar/414cbe5d58b02effaf0fdb3cb702e005?d=mm&s=24\",\"32x32\":\"https://secure.gravatar.com/avatar/414cbe5d58b02effaf0fdb3cb702e005?d=mm&s=32\",\"48x48\":\"https://secure.gravatar.com/avatar/414cbe5d58b02effaf0fdb3cb702e005?d=mm&s=48\"},\"displayName\":\"Yael Zaritsky\",\"active\":true},\"body\":\""+commentBody+"\",\"updateAuthor\":{\"self\":\"https://jira.wixpress.com/rest/api/2/user?username=yaelz\",\"name\":\"yaelz\",\"emailAddress\":\"yaelz@wix.com\",\"avatarUrls\":{\"16x16\":\"https://secure.gravatar.com/avatar/414cbe5d58b02effaf0fdb3cb702e005?d=mm&s=16\",\"24x24\":\"https://secure.gravatar.com/avatar/414cbe5d58b02effaf0fdb3cb702e005?d=mm&s=24\",\"32x32\":\"https://secure.gravatar.com/avatar/414cbe5d58b02effaf0fdb3cb702e005?d=mm&s=32\",\"48x48\":\"https://secure.gravatar.com/avatar/414cbe5d58b02effaf0fdb3cb702e005?d=mm&s=48\"},\"displayName\":\"Yael Zaritsky\",\"active\":true},\"created\":\"2014-11-13T13:44:27.728+0200\",\"updated\":\"2014-11-13T13:44:27.728+0200\"}"
  }
}
