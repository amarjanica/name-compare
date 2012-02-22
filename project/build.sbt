organization := "hr.element.nc"

name         := "name-compare"

version      := "0.0.1"


// ### Build settings ###

libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.1" % "test"

crossScalaVersions := Seq("2.9.1", "2.9.0-1", "2.9.0")

scalaVersion <<= (crossScalaVersions)(_.head)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")

unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)( _ :: Nil)

unmanagedSourceDirectories in Test    <<= (scalaSource in Test   )( _ :: Nil)


// ### Misc ###

initialCommands := "import hr.element.doit.csv._"
