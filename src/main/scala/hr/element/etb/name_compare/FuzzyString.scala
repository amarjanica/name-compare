package hr.element.etb
package name_compare

import scala.collection.JavaConversions._
import scala.util.parsing.combinator._
import scala.util.parsing.combinator.syntactical._
import scala.io.Source
import java.io.File
import java.text.Normalizer
import java.util.regex.Pattern
import uk.ac.shef.wit.simmetrics._
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein
import com.ibm.icu.text.Transliterator
/*
 * REDOSLIJED NIJE BITAN
 * CASE-OVE I LITERALSE ZANEMARIT
 * APOSTROFI I CRTICE NISU BITNI
 */

object FuzzyString {

  private val similarityMethod = new Levenshtein

  private val Trans = Transliterator.getInstance("Latin-ASCII")

  private val splitPattern = """\s+"""

  def compareTwoNames(name1: String, name2: String): Float = {

    val splitName1 = transText(name1, splitPattern)
    val splitName2 = transText(name2, splitPattern)

    val compareList =
      splitName1.map{a =>
        splitName2.map(similarityMethod.getSimilarity(a, _)).max
      }

    compareList.sum/compareList.length

  }

  protected val trimWhiteSpace =  (splitPattern r) replaceAllIn (_: String, " ")

  protected val splitFullName = (_:String).split( _:String) toSeq

  protected val lowerCase = (_: String).toLowerCase

  protected val transliterate = Trans.transliterate(_: String)

  def transText(text: String, splitPattern: String) =
    splitFullName((lowerCase andThen transliterate andThen trimWhiteSpace) (text), splitPattern)

}
