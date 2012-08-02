package hr.element.etb.name_compare

import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein

object NameCompare extends NameCompare(true, true, 0.95f, 1f)

case class NameCompare private(
    transliteralization: Boolean
  , lowercasing: Boolean
  , directThreshold: Float
  , initialsThreshold: Float) {

  def setTransliteralization(transliteralization: Boolean) =
    copy(transliteralization = transliteralization)

  def setLowercasing(lowercasing: Boolean) =
    copy(lowercasing = lowercasing)

  def setDirectThreshold(directThreshold: Float) =
    copy(directThreshold = directThreshold)

  lazy val fuzzy = Fuzzy
    .setTransliteralization(transliteralization)
    .setLowercasing(lowercasing)

  def apply(src: String, dst: String) =
    Comparison(fuzzy(src), fuzzy(dst)).result

// ----------------------------------------------------------------------------

  private case class Comparison(
      src: FuzzyString
    , dst: FuzzyString) {

    lazy val directPercentage =
      new Levenshtein getSimilarity(src.processed, dst.processed)

    lazy val initialsPercentage = {

      val _src = src.processed
      val _dst = dst.processed

      val names = _src split " " toList

     names.map{ name =>
        val perms = (name.head + "." :: names.diff(List(name))).permutations toList

        perms.map{ p =>
          val permName = p mkString " "
          new Levenshtein getSimilarity(permName, _dst)
        }.max
      }.max
    }

    lazy val result =
      if (directPercentage >= directThreshold) {
        DirectMatch(directPercentage)
      }
      else if (initialsPercentage >= initialsThreshold) {
        InitialsMatch(initialsPercentage)
      }
      else {
        NoMatch(directPercentage)
      }
  }
}

/*
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
*/
