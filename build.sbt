organization := "hr.element.etb"

name := "etb-name_compare"

version := "0.0.4"

crossScalaVersions := Seq("2.9.2", "2.9.1-1", "2.9.1", "2.9.0-1", "2.9.0")

scalaVersion <<= (crossScalaVersions){_.head}

resolvers := Seq("Element Nexus" at "http://maven.element.hr/nexus/content/groups/public/")

libraryDependencies ++= Seq(
  "com.ibm.icu" % "icu4j" % "49.1"
, "uk.ac.shef.wit" % "simmetrics" % "1.6.2"
, "org.scalatest" %% "scalatest" % "2.0.M2" % "test"
)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")

unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)( _ :: Nil)

unmanagedSourceDirectories in Test <<= (scalaSource in Test   )( _ :: Nil)

publishTo := Some("Element Releases" at "http://maven.element.hr/nexus/content/repositories/releases/")

credentials += Credentials(Path.userHome / ".publish" / "element.credentials")
