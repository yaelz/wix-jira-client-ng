package com.wixpress.ci.jiraclient.model.jirarequest

/**
 * Created by Uri_Bechar on 11/20/14.
 */
case class ArchiveVersionRequest(description:String,
                                 name:String,
                                 project: String,
                                 archived: Boolean) extends VersionRequestBase {

}
