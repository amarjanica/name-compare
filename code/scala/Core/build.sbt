name := "Name-Compare"

version := "0.1.1-3"

organization := "hr.element.etb"

crossScalaVersions := Seq("2.9.2", "2.9.1-1", "2.9.1", "2.9.0-1", "2.9.0")

scalaVersion <<= crossScalaVersions(_.head)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")

unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)

unmanagedSourceDirectories in Test <<= (scalaSource in Test)(_ :: Nil)

resolvers := Seq("Element Nexus" at "http://maven.element.hr/nexus/content/groups/public/")

externalResolvers <<= resolvers map ( rs =>
  Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
)

libraryDependencies := Seq(
  "com.ibm.icu" % "icu4j" % "49.1"
, "uk.ac.shef.wit" % "simmetrics" % "1.6.2"
, "org.scalatest" %% "scalatest" % "2.0.M2" % "test"
)

publishTo <<= (version) ( v => Some(
  if (v endsWith "SNAPSHOT") {
    "Element Snapshots" at "http://maven.element.hr/nexus/content/repositories/snapshots/"
  }
  else {
    "Element Releases" at "http://maven.element.hr/nexus/content/repositories/releases/"
  }
))

credentials += Credentials(Path.userHome / ".publish" / "element.credentials")

publishArtifact in (Compile, packageDoc) := false
