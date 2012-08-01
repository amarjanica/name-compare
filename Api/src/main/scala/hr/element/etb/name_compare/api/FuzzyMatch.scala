package hr.element.etb.name_compare.api

import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein

object FuzzyMatch {
  private val SplitPattern = """\s+"""r
}

case class FuzzyMatch(
    private val _src: String
  , private val _dst: String
  , decisionTreshold: Float
  , transliterize: Boolean
  , initialsOption: Boolean) {

  import FuzzyMatch._

  val src = if (transliterize) FuzzyString(_src) else _src
  val dst = if (transliterize) FuzzyString(_dst) else _dst

  lazy val percentage =
    new Levenshtein getSimilarity(src, dst)

  sealed abstract class Decision(method: String) {
    override val toString = "%s (%s: %.0f%%)" format(
        getClass.getSimpleName,
        method,
        percentage * 100
      )
  }
  case class Yes(method: String) extends Decision(method)
  case class Maybe(method: String) extends Decision(method)
  case class No(method: String) extends Decision(method)

  lazy val result = {
    if (percentage >= decisionTreshold) {
      Yes("Direct match found")
    }
    else if (initialsOption){
      checkInitials()
    }
    else
    {
      No("Direct match not found")
    }
  }

  private def checkInitials() = {
    if(dst contains '.') {
      val namesList = SplitPattern split src

      val initialsList = for {
        n <- namesList
        if n.nonEmpty
      } yield n.head + "."

      val srcNames = namesList ++ initialsList toSet
      val dstNames = SplitPattern split dst toSet

      if (dstNames.forall(srcNames)) {
        Maybe("Initials were matched")
      }
      else {
        No("Initials did not match")
      }
    } else {
      No("Direct match not found")
    }
  }

  override lazy val toString = result.toString
}
