package com.instantor.ip.sandbox
package name_compare

import hr.element.etb.name_compare._
import net.liftweb.http._
import rest._
import net.liftweb.common.Full
import net.liftweb.common.Box
import scala.xml._
import net.liftweb.util.BindPlus._
import net.liftweb.util.Helpers._
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apache.commons.codec.binary.Base64
import java.io.ByteArrayInputStream
import java.io.FileInputStream

object LiftListener extends RestHelper {
  private val NewLineRegex = "[\r\n]+"r

  val logger = LoggerFactory.getLogger(LiftListener.getClass())
  serve {
    case req @ Req("compare" :: Nil, _, GetRequest) =>
      val tests = //req.param("tests")
"""Hans Müller,--- ---
Hans Müller,Hans Müller
Hans Müller,Hans-Jörg Müller
Hans Müller,Klaus-Hans Müller
Hans Müller,Klaus Müller
Hans Müller,Paula Müller
Hans Müller,H. Müller
Hans Müller,K. Müller
Hans Müller,Hans Müller-Meier
Hans Müller,Hans Müller-Meier
Hans Müller,Hans-Jörg Müller-Meier
Hans Müller,Klaus-Hans Müller-Meier
Hans Müller,Klaus Müller-Meier
Hans Müller,Paula Müller-Meier
Hans Müller,H. Müller-Meier
Hans Müller,K. Müller-Meier
Hans Müller,Hans Müller
Hans Müller,Hans-Jörg Müller
Hans Müller,Klaus-Hans Müller
Hans Müller,Klaus Müller
Hans Müller,Paula Müller
Hans Müller,H. Müller
Hans Müller,K. Müller
Hans Müller,Hans Müller-Meier
Hans Müller,Hans Müller-Meier
Hans Müller,Hans-Jörg Müller-Meier
Hans Müller,Klaus-Hans Müller-Meier
Hans Müller,Klaus Müller-Meier
Hans Müller,Paula Müller-Meier
Hans Müller,H. Müller-Meier
Hans Müller,K. Müller-Meier
Hans Müller,Hans Müller
Hans Müller,Hans-Jörg Müller
Hans Müller,Klaus-Hans Müller
Hans Müller,Klaus Müller
Hans Müller,Paula Müller
Hans Müller,H. Müller
Hans Müller,K. Müller
Hans Müller,Hans Müller-Meier
Hans Müller,Hans Müller-Meier
Hans Müller,Hans-Jörg Müller-Meier
Hans Müller,Klaus-Hans Müller-Meier
Hans Müller,Klaus Müller-Meier
Hans Müller,Paula Müller-Meier
Hans Müller,H. Müller-Meier
Hans Müller,K. Müller-Meier
Hans Müller,Hans Müller
Hans Müller,Hans-Jörg Müller
Hans Müller,Klaus-Hans Müller
Hans Müller,Klaus Müller
Hans Müller,Paula Müller
Hans Müller,H. Müller
Hans Müller,K. Müller
Hans Müller,Hans Müller-Meier
Hans Müller,Hans Müller-Meier
Hans Müller,Hans-Jörg Müller-Meier
Hans Müller,Klaus-Hans Müller-Meier
Hans Müller,Klaus Müller-Meier
Hans Müller,Paula Müller-Meier
Hans Müller,H. Müller-Meier
Hans Müller,K. Müller-Meier
Hans Müller,Hans Müller
Hans Müller,Hans-Jörg Müller
Hans Müller,Klaus-Hans Müller
Hans Müller,Klaus Müller
Hans Müller,Paula Müller
Hans Müller,H. Müller
Hans Müller,K. Müller
Hans Müller,Hans Müller-Meier
Hans Müller,Hans Müller-Meier
Hans Müller,Hans-Jörg Müller-Meier
Hans Müller,Klaus-Hans Müller-Meier
Hans Müller,Klaus Müller-Meier
Hans Müller,Paula Müller-Meier
Hans Müller,H. Müller-Meier
Hans Müller,K. Müller-Meier
Hans Müller,Hans Meier
Hans Müller,Hans-Jörg Meier
Hans Müller,Klaus-Hans Meier
Hans Müller,Klaus Meier
Hans Müller,Paula Meier
Hans Müller,H. Meier
Hans Müller,K. Meier
Hans Müller,Hans Meier
Hans Müller,Hans-Jörg Meier
Hans Müller,Klaus-Hans Meier
Hans Müller,Klaus Meier
Hans Müller,Paula Meier
Hans Müller,H. Meier
Hans Müller,K. Meier
Hans Müller,Hans Meier
Hans Müller,Hans-Jörg Meier
Hans Müller,Klaus-Hans Meier
Hans Müller,Klaus Meier
Hans Müller,Paula Meier
Hans Müller,H. Meier
Hans Müller,K. Meier
Hans Müller,Hans Meier
Hans Müller,Hans-Jörg Meier
Hans Müller,Klaus-Hans Meier
Hans Müller,Klaus Meier
Hans Müller,Paula Meier
Hans Müller,H. Meier
Hans Müller,K. Meier
Hans Müller,K. M.
Hans Müller,H. M.
Hans Müller,Hans M.
Hans Muller Meier,H. M. Meier
Hans Muller Meier,H. Muller Meier
Hans Muller Meier,Muller Meier Hans
Hans Muller Meier,Muller Meier H.
Hans Muller Meier,Muller Meier K.
Hrvoje Horvat Hegedus,H. H."""

      val line = """(.*)[,/t+](.*)"""r

      val out = for{
        t: String <- (NewLineRegex split tests) toSet
        val line(src, dst) = t
        val res = (src + " : " + dst + " => " + FuzzyMatch(src, dst, 0.95f, true, false))
      } yield res
      PlainTextResponse(out.mkString("\n"))

  }
}
