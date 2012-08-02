package com.instantor.ip.sandbox
package name_compare

import net.liftweb._
import http._
import sitemap._
import org.slf4j.LoggerFactory
import scala.sys.process._


import hr.element.etb.lift.boot.Bouncer
object NameCompareBouncer extends Bouncer("sandbox/name-compare")

class Boot extends Bootable {
  def boot {
    NameCompareBouncer.init()

    LiftRules.setSiteMap(SiteMap(
      Menu.i("Test") / "test"
    ))

    LiftRules.addToPackages("com.instantor.ip.sandbox.name_compare")

//    LiftRules.statelessDispatchTable.append(LiftListener)

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use this block to send HTML5
    LiftRules.htmlProperties.default.set((r: Req) =>
      XHtmlInHtml5OutProperties(r.userAgent)
    )
  }
}
