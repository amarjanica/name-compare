package com.instantor.ip.sandbox
package name_compare
package snippet

import net.liftweb._
import http._
import util._
import common._
import Helpers._
import BindPlus._
import scala.xml._

object ShowLastTest {
  def render(n: NodeSeq) = n.bind("v",
    FuncAttrOptionBindParam("transliteration", (t: NodeSeq) => if (LastTest.transliteration) Some(t) else None, "checked")
  , FuncAttrOptionBindParam("lowercasing", (t: NodeSeq) => if (LastTest.lowercasing) Some(t) else None, "checked")
  , AttrBindParam("directThreshold", "%.0f" format(LastTest.directThreshold * 100), "value")
  , AttrBindParam("initialsThreshold", "%.0f" format(LastTest.initialsThreshold * 100), "value")
  , AttrBindParam("hyphenThreshold", "%.0f" format(LastTest.hyphenThreshold * 100), "value")
  , "names" -> LastTest.names
  )
}
