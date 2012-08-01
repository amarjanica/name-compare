package hr.element.etb.name_compare

sealed abstract class ComparisonDecision(
    decision: String
  , isOk: Boolean
  , description: String
  , percentage: Float) {

  override val toString = "%s (%s: %.0f%%)" format(
    decision
  , description
  , percentage * 100
  )
}

case class DirectMatch(percentage: Float)
    extends ComparisonDecision("Yes", true, "There was a direct match", percentage)

case class InitialsMatch(percentage: Float)
    extends ComparisonDecision("Maybe", true, "There was a match via initials", percentage)

case class NoMatch(percentage: Float)
    extends ComparisonDecision("No", false, "There was no match", percentage)
