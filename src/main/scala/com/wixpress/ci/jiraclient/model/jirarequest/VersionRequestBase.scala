package com.wixpress.ci.jiraclient.model.jirarequest

/**
 * Created by Uri_Bechar on 11/20/14.
 */
abstract class VersionRequestBase {
  def description:String
  def name:String
  def project: String
}
