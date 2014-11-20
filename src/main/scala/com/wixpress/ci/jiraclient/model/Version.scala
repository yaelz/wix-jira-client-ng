package com.wixpress.ci.jiraclient.model

/**
 * Created by Uri_Bechar on 11/20/14.
 */
case class Version (self: String, id:String,description:String,name:String,archived:Boolean,released:Boolean,projectId:Long,releaseDate:String,overdue:Boolean,userReleaseDate:String){

}
