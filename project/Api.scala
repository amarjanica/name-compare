import sbt._
import Keys._

import Helpers._
import Dependencies._

object Api extends Build {
 
  lazy val root = project(
    "Api",
    "0.0.1",
    Seq(
        namedcomparedeps
     ,  scalaTest    
  ),
  Seq(
    )
  ) 
}
