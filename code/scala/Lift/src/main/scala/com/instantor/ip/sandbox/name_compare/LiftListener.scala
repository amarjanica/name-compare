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
import net.liftweb.json.JsonAST
import net.liftweb.json.JsonDSL._
import net.liftweb.json.Printer._


object LiftListener extends RestHelper {
  private val NewLineRegex = "[\r\n]+"r

  private val tests =
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

    //http://platform.instantor-local.com/sandbox/name-compare/

  val logger = LoggerFactory.getLogger(LiftListener.getClass())
  serve {
  case req @ Req("sandbox/name-compare" :: Nil, _, PostRequest) =>


      val line = """(.*)[,/t+](.*)"""r

      val out = for{
        t: String <- (NewLineRegex split tests) toSet
        val line(src, dst) = t
        val res = (src + " : " + dst + " => " + NameCompare.apply(src, dst))
      } yield res
      PlainTextResponse(out.mkString("\n"))

  }

  def jsonCompare(
        transliteralization: Boolean
      , initialsOption: Boolean
      , directTreshodl: Float
      , initialsTreshod: Float){

    val line = """(.*)[,/t+](.*)"""r

    val out = for{
        t: String <- (NewLineRegex split tests) toSet
        val line(src, dst) = t
        val comparison = NameCompare.apply(src, dst)
        val res = ("srcname" -> src) ~
                  ("dstname" -> dst) ~
                  ("description" -> comparison.descr) ~
                  ("percentage" -> comparison.perc) ~
                  ("decision" -> comparison.dec)
      } yield res

      println(out)
      PlainTextResponse(out.mkString("\n"))
  }
}
