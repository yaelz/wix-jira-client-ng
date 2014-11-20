package com.wixpress.ci.jiraclient.model.jirarequest

/**
 * Created by Uri_Bechar on 11/20/14.
 */
case class UpdateVersionRequest(description:String,
                                name:String,
                                project: String,
                                released: Boolean,
                                releaseDate: String) extends VersionRequestBase {

}
