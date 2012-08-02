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
import net.liftweb.json.TypeHints
import net.liftweb.json.Serialization
import net.liftweb.json.NoTypeHints
import net.liftweb.json.JsonParser


object LiftListener extends RestHelper {
  private val NewLineRegex = "[\r\n]+"r

    //http://platform.instantor-local.com/sandbox/name-compare/

  val logger = LoggerFactory.getLogger(LiftListener.getClass())
  serve {
    case req @ Req("api" :: Nil, _, PostRequest) =>

      ( req.param("param") ) match {
        case (
            Full(AsParams(param))
              /*Full(directTreshold)
            , Full(initialsTreshold)
            , Full(initialsOption)
            , Full(transliteralization)
            , Full(input_names)*/
            ) =>  println(param)
              jsonCompare(param.directTreshold toFloat
                , param.initialsTreshold toFloat
                , param.initialsOption
                , param.transliteralization
                , param.input_names)

        case _ => JsonResponse(("Success" -> "false") ~ ("Reason" -> "bad request"))

      }

  }

  def jsonCompare(
        directTreshold: Float
      , initialsTreshod: Float
      , initialsOption: Boolean
      , transliteralization: Boolean
      , input: String): LiftResponse ={

    val line = """(.*)[,/t+](.*)"""r

    val out = for{
        t: String <- (NewLineRegex split input) toList
        val line(src, dst) = t
        val comparison = NameCompare.apply(src, dst)
        val res = ("srcname" -> src) ~
                  ("dstname" -> dst) ~
                  ("description" -> comparison.descr) ~
                  ("percentage" -> comparison.perc * 100) ~
                  ("decision" -> comparison.dec)
      } yield res


      JsonResponse(out)
  }

  case class Params(
        directTreshold: String
      , initialsTreshold: String
      , initialsOption: Boolean
      , transliteralization: Boolean
      , input_names: String
      )

  object  AsParams{
    implicit def formats = Serialization.formats(NoTypeHints)

    def unapply(s: String): Option[Params] =
      try {
              Some(JsonParser.parse(s).extract[Params])
      } catch {
        case e  => e.printStackTrace()
          None
      }

  }
  /* (req.param("username"), req.param("password")) match {
        case (Full(username), Full(password)) =>
          userRep.editUser(username, password) match {
            case true =>
              JsonResponse(("success" -> true)~
                ("message" -> "Lozinka uspješno promijenjena."))
            case false =>
              JsonResponse(("success" -> false)~
                ("message" -> "Dogodila se pogreška prilikom promjene lozinke!"))
          }
        case _ => BadResponse()

   */
}
