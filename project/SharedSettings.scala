import sbt._
import Keys._

import Helpers._
import Dependencies._

object InitialCommands {
  val settings = Seq(
  )
}


//  ---------------------------------------------------------------------------

object Default {
  val settings =
    Defaults.defaultSettings ++
    Resolvers.settings ++
    Publishing.settings ++ Seq(
      organization := "hr.element.etb",
      crossScalaVersions := Seq("2.9.1", "2.9.0-1", "2.9.0"),
      scalaVersion <<= (crossScalaVersions) { versions => versions.head },
      scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise", "-Yrepl-sync"),
      javacOptions  := Seq("-deprecation", "-encoding", "UTF-8", "-source", "1.6", "-target", "1.6"),
      unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)( _ :: Nil),
      unmanagedSourceDirectories in Test    <<= (scalaSource in Test   )( _ :: Nil)
    )

  // Web plugin
  import com.github.siasia.WebPlugin._
  import com.github.siasia.PluginKeys._

}
//  ---------------------------------------------------------------------------

object Repositories {
  val MavenNuxeo       = "Nuxeo Nexus" at "http://maven.nuxeo.org/nexus/content/groups/public/"
  val ElementNexus     = "Element Nexus"     at "http://maven.element.hr/nexus/content/groups/public/"
  val ElementReleases  = "Element Releases"  at "http://maven.element.hr/nexus/content/repositories/releases/"
  val ElementSnapshots = "Element Snapshots" at "http://maven.element.hr/nexus/content/repositories/snapshots/"
  val ElementPrivateReleases    = "Element Private Releases" at "http://maven.element.hr/nexus/content/repositories/releases-private/"
  val ElementPrivateSnapshots   = "Element Private Snapshots" at "http://maven.element.hr/nexus/content/repositories/snapshots-private/"

}

//  ---------------------------------------------------------------------------

object Resolvers {
  import Repositories._

  val settings = Seq(
    resolvers := Seq(
      MavenNuxeo
    , ElementNexus
    , ElementReleases
    , ElementSnapshots
    , ElementPrivateReleases
    , ElementPrivateSnapshots
    ),
    externalResolvers <<= resolvers map { rs =>
      Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
    }
  )
}

//  ---------------------------------------------------------------------------

object Publishing {
  import Repositories._

  val settings = Seq(
    publishTo <<= (version) { version => Some(
      if (version.endsWith("SNAPSHOT")) ElementSnapshots else ElementReleases
    )},
    credentials += Credentials(Path.userHome / ".publish" / "element.credentials"),
      publishArtifact in (Compile, packageSrc) := false,
      publishArtifact in (Compile, packageDoc) := false
  )
}

//  ---------------------------------------------------------------------------


//  ---------------------------------------------------------------------------

object Helpers {
  implicit def depToFunSeq(m: ModuleID) = Seq((_: String) => m)
  implicit def depFunToSeq(fm: String => ModuleID) = Seq(fm)
  implicit def depSeqToFun(mA: Seq[ModuleID]) = mA.map(m => ((_: String) => m))
  implicit def warName2SMA(name: String) = (_: String, _: ModuleID, _: Artifact) => name

//  ---------------------------------------------------------------------------

  def top(
      title: String,
      projectAggs: Seq[sbt.ProjectReference] = Seq()) =
    Project(
      title,
      file("."),
      settings = Default.settings ++ Seq(
        name  := title,
        version := "0.0.0"
      )
    ) aggregate(projectAggs: _*)

  def parent(
      title: String,
      projectAggs: Seq[sbt.ProjectReference] = Seq()) =
    Project(
      title,
      file(title.replace('-', '/')),
      settings = Default.settings ++ Seq(
        name    := "NC-" + title,
        version := "0.0.0"
      )
    ) aggregate(projectAggs: _*)

  def project(
      title: String,
      ver: String,
      deps: Seq[Seq[String => ModuleID]] = Seq(),
      projectDeps: Seq[ClasspathDep[ProjectReference]] = Seq()) =
    Project(
      title,
      file(title.replace('-', '/')),
      settings = Default.settings ++ Seq(
        name    := "NC-" + title,
        version := ver
      ) :+ (libraryDependencies <++= scalaVersion( sV =>
        for (depSeq <- deps; dep <- depSeq) yield dep(sV))
      )
    ) dependsOn(projectDeps: _*)
}
