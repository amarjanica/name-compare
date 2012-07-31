package hr.element.etb.name_compare

import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein

/*
 *  The following method of comparing names uses Levenshtein algorithm.
 *  The Levenshtein algorithm (also called Edit-Distance) calculates the
 *  least number of edit operations that are necessary to modify one string to obtain another string.
 *
 *  Method FuzzyMatch.apply returns 0 to 1 correlation between 2 names.
 *
 *  Names, that are used for fuzzy matching previously are:
 *
 *  1. Transliterated
 *  2. Alphabetically sorted
 *  3. Converted to lowercase and trimmed for extra whitespaces
 *
 */
object FuzzyMatch {
  def apply(name1: String, name2: String): Float = {
    (new Levenshtein).getSimilarity(
        FuzzyString(name1),
        FuzzyString(name2))
  }
}
