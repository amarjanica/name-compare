import sbt._
import Keys._

// ###########################################################################

object Projects extends Build {
  import Repositories._
  import Dependencies._

  //Dependency report plugin
  import com.micronautics.dependencyReport.DependencyReport._

  lazy val sharedSettings =
    Defaults.defaultSettings ++
    dependencyReportSettings ++ Seq(
      scalaVersion <<= crossScalaVersions(_.head)
    , scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")
    , unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)
    , externalResolvers <<= resolvers map ( rs =>
        Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
      )
    )

// ----------------------------------------------------------------------------

  lazy val core = Project(
    "Name-Compare"
  , file("Core")
  , settings =
      sharedSettings ++
      Publishing.settings ++ Seq(

        version := "0.1.0-SNAPSHOT"
      , organization := "hr.element.etb"

      //, crossScalaVersions := Seq("2.9.2", "2.9.1-1", "2.9.1", "2.9.0-1", "2.9.0")
      , unmanagedSourceDirectories in Test <<= (scalaSource in Test)(_ :: Nil)

      , resolvers := Seq(ElementNexus)

      , libraryDependencies := Seq(
          icu4j, simmetrics
        )
      )
  )

// ----------------------------------------------------------------------------

  // Web plugin
  import com.github.siasia.WebPlugin._
  import com.github.siasia.PluginKeys._

  // Coffeescript plugin
  import coffeescript.Plugin._
  import CoffeeKeys._

  // Less plugin
  import less.Plugin._
  import LessKeys._

  lazy val lift = Project(
    "Name-Compare-Web"
  , file("Lift")
  , settings =
      sharedSettings ++
      webSettings ++
      coffeeSettings ++
      lessSettings ++ Seq(

        version := "0.0.0"
      , organization := "com.instantor.ip.sandbox"

      , crossScalaVersions := Seq("2.9.1")
      , unmanagedSourceDirectories in Test := Nil

      , resolvers := Seq(InstantorNexus)

      , libraryDependencies := Seq(
          jetty, orbit, liftWebkit
        , logback
        , etbUtil, etbLift
        , jodaConvert, jodaTime, scalaTime
        )

      , scanDirectories in Compile := Nil
      , port in container.Configuration := 9633
      , artifactName in packageWar := ((_: String, _: ModuleID, _: Artifact) => "ip-sandbox-name-compare.war")

      , resourceManaged in (Compile, coffee) <<= (webappResources in Compile)(_.get.head / ".." / "static" / "coffee-js")

      , mini in (Compile, less) := true
      , resourceManaged in (Compile, less) <<= (webappResources in Compile)(_.get.head / ".." / "static" / "less-css")
      )
  ) dependsOn(core)
}

// ============================================================================

object Dependencies {
  // Date/Time libraries
  val jodaConvert = "org.joda" % "joda-convert" % "1.2"
  val jodaTime = "joda-time" % "joda-time" % "2.1"
  val scalaTime = "org.scala-tools.time" %% "time" % "0.5"

  // Transliteration & comparison
  val icu4j = "com.ibm.icu" % "icu4j" % "49.1"
  val simmetrics = "uk.ac.shef.wit" % "simmetrics" % "1.6.2"

  // Web
  val jetty = "org.eclipse.jetty" % "jetty-webapp" % "7.6.5.v20120716" % "container"
  val orbit = "org.eclipse.jetty.orbit" % "javax.servlet" % "2.5.0.v201103041518" % "container" artifacts Artifact("javax.servlet", "jar", "jar")
  val liftWebkit = "net.liftweb" %% "lift-webkit" % "2.4"

  // Element Toolbox
  val etbUtil = "hr.element.etb" %% "etb-util" % "0.2.16"
  val etbLift = "hr.element.etb" %% "etb-lift" % "0.0.21"

  // Logging
  val logback = "ch.qos.logback" % "logback-classic" % "1.0.6"

  // Testing
  val scalaTest = "org.scalatest" %% "scalatest" % "2.0.M2" % "test"
}

// ============================================================================

object Repositories {
  val ElementNexus      = "Element Nexus"      at "http://maven.element.hr/nexus/content/groups/public/"
  val ElementReleases   = "Element Releases"   at "http://maven.element.hr/nexus/content/repositories/releases/"
  val ElementSnapshots  = "Element Snapshots"  at "http://maven.element.hr/nexus/content/repositories/snapshots/"

  val InstantorNexus    = "Instantor Nexus"    at "http://www.instantor.com/nexus/content/groups/public/"
  val InstantorReleases = "Instantor Releases" at "http://www.instantor.com/nexus/content/repositories/releases/"
}

// ----------------------------------------------------------------------------

object Publishing {
  import Repositories._

  val settings = Seq(
    publishTo <<= (version) ( v => Some(
      if (v endsWith "SNAPSHOT") ElementSnapshots else ElementReleases
    )),
    credentials += Credentials(Path.userHome / ".publish" / "element.credentials"),
    publishArtifact in (Compile, packageDoc) := false
  )
}

// ############################################################################
