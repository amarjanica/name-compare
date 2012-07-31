package hr.element.etb.name_compare.api

import com.ibm.icu.text.Transliterator

object FuzzyString {
  private val ASCII = Transliterator.getInstance("Latin-ASCII")

  def apply(s: String) = {
    val sanitizedName = (transliterate andThen lowerCase andThen trimWhiteSpace)(s)
    val sortedName    = sanitizedName split ' ' sortWith sortAsc
    sortedName mkString " "
  }

  private val splitPattern = """\s{2,}"""

  private def sortAsc(e1: String, e2: String) = (e1 compareToIgnoreCase e2) > 0 // ASC

  protected val trimWhiteSpace = (splitPattern r) replaceAllIn (_: String, " ")

  protected val lowerCase = (_: String).toLowerCase

  protected val transliterate = ASCII.transliterate(_: String)

  def transText(text: String, splitPattern: String) =
    (lowerCase andThen transliterate andThen trimWhiteSpace) (text)
}
