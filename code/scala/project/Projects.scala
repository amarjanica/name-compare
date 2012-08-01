import sbt._
import Keys._

import Helpers._
import Dependencies._

object Projects extends Build {
  lazy val core = project(
    "name-compare"
  , "0.1.0-SNAPSHOT"
  , "Core"
  , Seq(
      icu4j
    , simmetrics
    , scalaTest
    )
  )

  lazy val lift = project(
    "name-compare-web"
  , "0.0.0"
  , "Lift"
  , Seq(
      jetty
    , liftWebkit
    , logback
    , etbLift
    , scalaTime
    )
  , Seq(
      core
    )
  , 9633
  , "ip-name-compare.war"
  )
}
