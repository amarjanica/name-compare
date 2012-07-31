package hr.element.etb
package name_compare
package lift

import net.liftweb._
import http._
import sitemap._
import org.slf4j.LoggerFactory
import scala.sys.process._


class Boot extends Bootable {
  //val logger = LoggerFactory.getLogger(classOf[Boot])
  def boot {
    LiftRules.addToPackages("hr.element.etc.namedcompare.lift")
    LiftRules.statelessDispatchTable.append(LiftListener) // stateless -- no session created
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

  }

}
