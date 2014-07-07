package hr.element.etb.name_compare

import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein

object NameCompare extends NameCompare(true, true, 0.95f, 1f, 1f)

case class NameCompare private(
    transliteration: Boolean
  , lowercasing: Boolean
  , directThreshold: Float
  , initialsThreshold: Float
  , hyphenThreshold: Float) {

  def setTransliteration(transliteration: Boolean) =
    copy(transliteration = transliteration)

  def setLowercasing(lowercasing: Boolean) =
    copy(lowercasing = lowercasing)

  def setDirectThreshold(directThreshold: Float) =
    copy(directThreshold = directThreshold)

  def setInitialsThreshold(initialsThreshold: Float) =
    copy(initialsThreshold = initialsThreshold)

  def setHyphenThreshold(hyphenThreshold: Float) =
    copy(hyphenThreshold = hyphenThreshold)

  lazy val fuzzy = Fuzzy
    .setTransliteration(transliteration)
    .setLowercasing(lowercasing)

  def apply(src: String, dst: String) = {
    val c = Comparison(fuzzy(src), fuzzy(dst))
    c.result -> (c.src, c.dst)
  }

// ----------------------------------------------------------------------------

  private case class Comparison(
      src: FuzzyString
    , dst: FuzzyString) {

    lazy val identical =
      src.original == dst.original

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

    lazy val hyphenPercentage = {
      val _src = src.processed
      val _dst = dst.processed

      if(!_dst.contains('-'))
      {
        0.0f
      }
      else
      {
        val names = _dst replace ('-', ' ')
        val namesList = names split ' ' toList

        val combinations =
          for{n <- 1 to namesList.length} yield (namesList.combinations(n)) toList

        val comb = (for{v <- combinations
            n <-v} yield n) toList

        val res = comb.map{ combination =>
          val perms = combination.permutations toList

          perms.map{ p =>
            val permName = p mkString " "
            new Levenshtein getSimilarity(_src, permName)
            }.max
          }.max

       val nWordsDst = namesList.length toFloat
       val nWordsSrc = (_src split " ").length toFloat

       if(nWordsDst > nWordsSrc)
       {
         res*(nWordsSrc/nWordsDst)
       } else
       {
         res*(nWordsDst/nWordsSrc)
       }
      }
    }

    lazy val result =
      if (identical) {
        IdenticalMatch
      }
      else if (directPercentage >= directThreshold) {
        DirectMatch(directPercentage)
      }
      else if (initialsPercentage >= initialsThreshold) {
        InitialsMatch(initialsPercentage)
      }
      else if (hyphenPercentage >= hyphenThreshold) {
        HyphenMatch(hyphenPercentage)
      }
      else {
        NoMatch(directPercentage)
      }
  }
}
