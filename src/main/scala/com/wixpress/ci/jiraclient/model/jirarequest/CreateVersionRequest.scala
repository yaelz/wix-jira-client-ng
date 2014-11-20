package com.wixpress.ci.jiraclient.model.jirarequest

/**
 * Created by Uri_Bechar on 11/20/14.
 */
case class CreateVersionRequest(description:String,
                               name:String,
                               project: String,
                               archived: Boolean,
                               released: Boolean,
                               userReleaseDate: String) extends VersionRequestBase {

}
