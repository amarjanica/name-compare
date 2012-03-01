package hr.element.nc

import scala.collection.JavaConversions._
import scala.util.parsing.combinator._
import scala.util.parsing.combinator.syntactical._
import scala.io.Source
import java.io.File
import java.text.Normalizer
import java.util.regex.Pattern


/*
 * REDOSLIJED NIJE BITAN
 * CASE-OVE I LITERALSE ZANEMARIT
 * APOSTROFI I CRTICE NISU BITNI
 */

object FuzzyString {

  val similarityMethod = new Levenshtein

  def compareTwoNames(name1: String, name2: String, pattern: String = """[-\s+]"""): Float = {

    val splitName1 = splitString(name1, pattern)
    val splitName2 = splitString(name2, pattern)

    val compareList =
      splitName1.map{a =>
        splitName2.map(similarityMethod.getSimilarity(a, _)).max
      }

    compareList.sum/compareList.length
  }

  private def removeDiacritics(str: String) = {
    val nfd = Normalizer.normalize(str, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    pattern.matcher(nfd).replaceAll("")
  }

  private def splitString(str:String, pattern: String) = {
    str.split(pattern).toList
  }

}
