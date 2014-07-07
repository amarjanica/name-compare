name := "Name-Compare"

version := "0.1.1-3"

organization := "hr.element.etb"

crossScalaVersions := Seq("2.10.4", "2.9.2", "2.9.1-1", "2.9.1", "2.9.0-1", "2.9.0")

scalaVersion := crossScalaVersions.value.head

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")

unmanagedSourceDirectories in Compile := Seq((scalaSource in Compile).value)

unmanagedSourceDirectories in Test := Seq((scalaSource in Test).value)

resolvers := Seq("Element Nexus" at "http://repo.element.hr/nexus/content/groups/public/")

externalResolvers <<= resolvers map ( rs =>
  Resolver.withDefaultResolvers(rs, mavenCentral = false)
)

libraryDependencies := Seq(
  "com.ibm.icu" % "icu4j" % "49.1"
, "uk.ac.shef.wit" % "simmetrics" % "1.6.2"
, "org.scalatest" %% "scalatest" % "2.0" % "test"
)

publishTo <<= (version) ( v => Some(
  if (v endsWith "SNAPSHOT") {
    "Element Snapshots" at "http://repo.element.hr/nexus/content/repositories/snapshots/"
  }
  else {
    "Element Releases" at "http://repo.element.hr/nexus/content/repositories/releases/"
  }
))

credentials += Credentials(Path.userHome / ".publish" / "element.credentials")

publishArtifact in (Compile, packageDoc) := false
