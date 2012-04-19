package hr.element.etb
package name_compare

import org.scalatest.matchers.MustMatchers
import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen



class FuzzyStringTest extends FeatureSpec with GivenWhenThen with MustMatchers{
  feature("name comparing") {
    scenario("name compare 1: reverse") {
      info("We wan't to compare if string, compared to its reversed form, returns a small corellation factor")
      val in = "Ivana Ivanić"
      val rev = "ćinavI anavI"
      given(" a name %s, and its reversed form %s" format(in, rev))
      val res = FuzzyString.compareTwoNames(in, rev)
      then("Result of comparation must be below 0.5")
      res must be < (0.5f)
    }

    scenario("name compare 2: transliteralization"){
      info("We wan't to find out if transliteralization works")
      val in = "Ana Đurić"
      val trans = "Ana Duric"
      val res = FuzzyString.compareTwoNames(in, trans)
      given("the folowing two names, %s and %s" format (in, trans))
      then("correlation of these names must equal 1.0")
      res must equal (1.0f)
    }

    scenario("name compare 3"){
      val in = "Horvat Ivan"
      val out = "Ivan Horvat"
      val res = FuzzyString.compareTwoNames(in, out)
      given("the following two names, %s and %s" format(in, out))
      then("correlation of these names must equal 1.0")
      res must equal (1.0f)
    }

    scenario("name compare 4: Long names"){
      val in = "Ana Maria Žabić"
      val out = "Žabic Maria Ana"
      val res = FuzzyString.compareTwoNames(in, out)
      given("the following two names, %s and %s" format(in, out))
      then("correlation of these names must equal 1.0")
      res must equal (1.0f)
    }
  }
}