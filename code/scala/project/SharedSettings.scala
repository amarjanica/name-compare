import sbt._
import Keys._

import Dependencies._

object Default {
  //Dependency report plugin
  import com.micronautics.dependencyReport.DependencyReport._

  val settings =
    Defaults.defaultSettings ++
    Resolvers.settings ++
    Publishing.settings ++
    dependencyReportSettings ++ Seq(
      organization := "com.instantor.ip"
    , crossScalaVersions := Seq("2.9.2", "2.9.1-1", "2.9.1", "2.9.0-1", "2.9.0")
    , scalaVersion <<= (crossScalaVersions) { versions => versions.head }
    , scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")
    , unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)( _ :: Nil)
    , unmanagedSourceDirectories in Test <<= (scalaSource in Test)( _ :: Nil)
    )

  // Web plugin
  import com.github.siasia.WebPlugin._
  import com.github.siasia.PluginKeys._

  // Coffeescript plugin
  import coffeescript.Plugin._
  import CoffeeKeys._

  // Less plugin
  import less.Plugin._
  import LessKeys._

  def liftSettings(jettyPort: Int, warName: String) =
    webSettings ++ Seq(
      scanDirectories in Compile := Nil
    , port in container.Configuration := jettyPort
    , artifactName in packageWar := ((_: String, _: ModuleID, _: Artifact) => warName)
    ) ++ coffeeSettings ++ Seq(
      resourceManaged in (Compile, coffee) <<= (webappResources in Compile)(_.get.head / ".." / "static" / "coffee-js")
    ) ++ lessSettings ++ Seq(
      mini in (Compile, less) := true
    , resourceManaged in (Compile, less) <<= (webappResources in Compile)(_.get.head / ".." / "static" / "less-css")
    )
}

//  ---------------------------------------------------------------------------

object Helpers {
  implicit def depToFunSeq(m: ModuleID) = Seq((_: String) => m)
  implicit def depFunToSeq(fm: String => ModuleID) = Seq(fm)
  implicit def depSeqToFun(mA: Seq[ModuleID]) = mA.map(m => ((_: String) => m))

//  ---------------------------------------------------------------------------

  def project(
      title: String
    , ver: String
    , path: String
    , deps: Seq[Seq[String => ModuleID]] = Seq()
    , projectDeps: Seq[ClasspathDep[ProjectReference]] = Seq()) =
    Project(
      title
    , file(path)
    , settings = Default.settings ++ Seq(
        name := title
      , version := ver
      , libraryDependencies <++= scalaVersion( sV =>
          for (depSeq <- deps; dep <- depSeq) yield dep(sV)
        )
      )
    ) dependsOn(projectDeps: _*)

  def project(
      title: String
    , ver: String
    , path: String
    , deps: Seq[Seq[String => ModuleID]]
    , projectDeps: Seq[ClasspathDep[ProjectReference]]
    , jettyPort: Int
    , warName: String): Project = project(
        title
      , ver
      , path
      , deps
      , projectDeps
      ) settings(
          Default.liftSettings(jettyPort: Int, warName: String): _*
        )
}
