package hr.element.etb.name_compare.api

import org.scalatest.matchers.MustMatchers
import org.scalatest.FeatureSpec
import org.scalatest.GivenWhenThen



class FuzzyStringTest extends FeatureSpec with GivenWhenThen with MustMatchers{
  feature("name comparing") {
    scenario("name compare 1: reverse") {
      info("We wan't to compare if string, compared to its reversed form, returns a small corellation factor")
      val in1 = "Ivana Ivanić"
      val in2 = "ćinavI anavI"
      given(" a name %s, and its reversed form %s" format(in1, in2))
      val res = FuzzyMatch(in1, in2)
      then("Result of comparation must be below 0.5")
      res must be < (0.5f)
    }

    scenario("name compare 2: transliteralization"){
      info("We wan't to find out if transliteralization works")
      val in = "Ana Đurić"
      val trans = "Ana Duric"
      val res = FuzzyMatch(in, trans)
      given("the folowing two names, %s and %s" format (in, trans))
      then("correlation of these names must equal 1.0")
      res must equal (1.0f)
    }

    scenario("name compare 3"){
      val in1 = "Horvat Ivan"
      val in2 = "Ivan Horvat"
      val res = FuzzyMatch(in1, in2)
      given("the following two names, %s and %s" format(in1, in2))
      then("correlation of these names must equal 1.0")
      res must equal (1.0f)
    }

    scenario("name compare 4: Long names"){
      val in1 = "Ana Maria Žabić"
      val in2 = "Žabic Maria Ana"
      val res = FuzzyMatch(in1, in2)
      given("the following two names, %s and %s" format(in1, in2))
      then("correlation of these names must equal 1.0")
      res must equal (1.0f)
    }

    scenario("name compare 5: Different name lengths 1"){
      val in1  = "John"
      val in2 = "John John John"
      val res = FuzzyMatch(in1, in2)
      given("the following two names, %s and %s" format(in1, in2))
      then("correlation of these names must be at least below 0.3")
      res must be < (0.3f)
    }

    scenario("name compare 6: reverse check"){
      val in1  = "John"
      val in2 = "John John John"
      val res1 = FuzzyMatch(in1, in2)
      val res2 = FuzzyMatch(in2, in1)
      given("the following two names, %s and %s" format(in1, in2))
      when("when first name is compared to second name, and reverse")
      then("Its results must be exual")
      res1 must equal (res2)
    }
  }
}
