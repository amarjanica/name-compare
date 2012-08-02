package hr.element.etb.name_compare

import com.ibm.icu.text.Transliterator

object Fuzzy extends Fuzzy(true, true) {
  private val ASCII = Transliterator.getInstance("Latin-ASCII")

  private val SplitPattern = """\s{2,}"""r
  private val Spacer = " "

  private val trimWhiteSpace = SplitPattern replaceAllIn (_: String, Spacer)
  private val lowerCase = (_: String) toLowerCase
  private val transliterate = ASCII transliterate (_: String)

  private def sortAsc(e1: String, e2: String) = (e1 compare e2) > 0 // ASC
  private def sortWords(words: String) = words split Spacer sortWith sortAsc mkString Spacer

  private val passThru = (s: String) => s
}

case class Fuzzy private(
    transliteralization: Boolean
  , lowercasing: Boolean) {

  import Fuzzy._

  def setTransliteralization(transliteralization: Boolean) =
    copy(transliteralization = transliteralization)

  def setLowercasing(lowercasing: Boolean) =
    copy(lowercasing = lowercasing)

  def lowerCaseAction =
    if (lowercasing) lowerCase else passThru

  def transliteralizationAction =
    if (transliteralization) transliterate else passThru

  def apply(text: String) =
    FuzzyString(
      text
    , (trimWhiteSpace andThen lowerCase andThen transliterate andThen sortWords)(text)
    )
}

case class FuzzyString(original: String, processed: String) {
  override val toString = processed
}
