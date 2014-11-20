package com.wixpress.ci.jiraclient

/**
 * Created by Uri_Bechar on 11/20/14.
 */
object VersionBuildUtil {
  val DELIMITER = "-"

  def createBuildVersionName(buildName : String, version : String): String = {
    buildName +  DELIMITER + version
  }

}
