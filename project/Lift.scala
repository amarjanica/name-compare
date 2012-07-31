import sbt._
import Keys._

import Helpers._
import Dependencies._

object Lift extends Build {
  // Web plugin
  import com.github.siasia.WebPlugin._
  import com.github.siasia.PluginKeys._

  lazy val root = project(
    "Lift",
    "0.0.1",
    Seq(
       jetty
    , liftWebkit
    , logback
    , etbLift
    , scalaTime
    , dispatch
  ),
  Seq(
    Api.root
    )
  ) settings (
    webSettings ++ Seq (
      port in container.Configuration := 9740,
      scanDirectories in Compile := Nil,
      (artifactName in packageWar := "namedcompare.war")
    ): _*
  )
}
