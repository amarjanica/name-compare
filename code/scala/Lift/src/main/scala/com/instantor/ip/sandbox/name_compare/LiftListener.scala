package com.instantor.ip.sandbox
package name_compare

import hr.element.etb.name_compare._
import net.liftweb.http._
import rest._
import net.liftweb.common._
import scala.xml._
import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import net.liftweb.json.Printer._
import hr.element.etb.Pimps._
import net.liftweb.http.SessionVar
import scalax.io._

object LastTest extends SessionVar[Params](
  Params(
    true
  , true
  , 0.95f
  , 1f
  , 1f
  , Resource.fromClasspath("names.txt").slurpString("UTF-8")
  )
)

case class Params(
    transliteration: Boolean
  , lowercasing: Boolean
  , directThreshold: Float
  , initialsThreshold: Float
  , hyphenThreshold: Float
  , names: String)

object LiftListener extends RestHelper {

  serve {
    case "api" :: Nil JsonPost json =>
      implicit def formats = Serialization.formats(NoTypeHints)

      val response =
        try {
          val params = json._1.asInstanceOf[JObject].extract[Params]
          LastTest.set(params)

          val response = process(params)
          response
        }
        catch {
          case x: Throwable =>
            x.printStackTrace()

            ("Success" -> false) ~ ("Reason" -> x.toString)
        }

      JsonResponse(response)
  }

  private val LinePattern = "[\r\n]+"r
  private val NamePattern = "(.*)[,;\t]+(.*)"r

  def process(params: Params) = {
    val comparer = NameCompare
      .setTransliteration(params.transliteration)
      .setLowercasing(params.lowercasing)
      .setDirectThreshold(params.directThreshold)
      .setInitialsThreshold(params.initialsThreshold)
      .setHyphenThreshold(params.hyphenThreshold)

    val lines = LinePattern split params.names

    lines.map { l =>
      val (cD, (fS, fD)) =
      try {
        val NamePattern(src, dst) = l
        comparer(src.ksp, dst.ksp)
      }
      catch {
        case _: Throwable =>
          val eS = FuzzyString(l, "Could not separate names")
          MatchError -> (eS, eS)
      }

      implicit def formats = Serialization.formats(NoTypeHints)

      ("comparisonDecision" ->
        ("decision" -> cD.decision) ~
        ("percentage" -> cD.percentage) ~
        ("description" -> cD.description)
      ) ~
      ("src" ->
        ("original" -> fS.original) ~
        ("processed" -> fS.processed)
      ) ~
      ("dst" ->
        ("original" -> fD.original) ~
        ("processed" -> fD.processed)
      )
    }.toList: JArray
  }
}
