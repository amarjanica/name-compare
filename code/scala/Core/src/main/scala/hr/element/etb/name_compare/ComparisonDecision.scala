package hr.element.etb.name_compare

sealed abstract class ComparisonDecision(
    val decision: String
  , val isOk: Boolean
  , val description: String
  , val percentage: Float) {

  override val toString = "%s (%s: %.0f%%)" format(
    decision
  , description
  , percentage * 100
  )
}

case object IdenticalMatch
    extends ComparisonDecision("Yes", true, "Identical match", 1f)

case class DirectMatch(override val percentage: Float)
    extends ComparisonDecision("Yes", true, "There was a direct match", percentage)

case class InitialsMatch(override val percentage: Float)
    extends ComparisonDecision("Maybe", true, "There was a match via initials", percentage)

case class HyphenMatch(override val percentage: Float)
    extends ComparisonDecision("Maybe", true, "There was a match via hyphens", percentage)

case class NoMatch(override val percentage: Float)
    extends ComparisonDecision("No", false, "There was no match", percentage)

case object MatchError
    extends ComparisonDecision("No", false, "There was an error", 0f)
