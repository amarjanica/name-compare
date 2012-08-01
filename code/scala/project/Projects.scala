import sbt._
import Keys._

import Helpers._
import Dependencies._

object Projects extends Build {
  lazy val core = project(
    "Core"
  , "0.0.1"
  , Seq(
      icu4j
    , simmetrics
    , scalaTest
    )
  )

  lazy val lift = project(
    "Lift"
  , "0.0.1"
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
