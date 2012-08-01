package com.instantor.ip.sandbox
package name_compare

import net.liftweb._
import http._
import sitemap._
import org.slf4j.LoggerFactory
import scala.sys.process._

class Boot extends Bootable {
  def boot {
    LiftRules.setSiteMap(SiteMap(
      Menu.i("test") / "test"
    ))

    LiftRules.addToPackages("com.instantor.ip.sandbox.name_compare")
    LiftRules.statelessDispatchTable.append(LiftListener)

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))
  }
}
