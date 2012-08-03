name := "Name-Compare-Web"

organization := "com.instantor.ip.sandbox"

scalaVersion := "2.9.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise")

unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)

unmanagedSourceDirectories in Test := Nil

resolvers := Seq("Instantor Nexus" at "http://www.instantor.com/nexus/content/groups/public/")

externalResolvers <<= resolvers map ( rs =>
  Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
)

libraryDependencies := Seq(
  "hr.element.etb" %% "name-compare" % "0.1.0"
, "org.joda" % "joda-convert" % "1.2"
, "joda-time" % "joda-time" % "2.1"
, "org.scala-tools.time" %% "time" % "0.5"
, "org.eclipse.jetty" % "jetty-webapp" % "7.6.5.v20120716" % "container"
, "org.eclipse.jetty.orbit" % "javax.servlet" % "2.5.0.v201103041518" % "container" artifacts Artifact("javax.servlet", "jar", "jar")
, "net.liftweb" %% "lift-webkit" % "2.4"
, "hr.element.etb" %% "etb-util" % "0.2.16"
, "hr.element.etb" %% "etb-lift" % "0.0.21"
, "ch.qos.logback" % "logback-classic" % "1.0.6"
, "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.0"
, "org.scalatest" %% "scalatest" % "2.0.M2" % "test"
)

seq(webSettings: _*)

scanDirectories in Compile := Nil

port in container.Configuration := 9633

artifactName in packageWar := ((_: String, _: ModuleID, _: Artifact) => "ip-sandbox-name-compare.war")

seq(coffeeSettings: _*)

resourceManaged in (Compile, CoffeeKeys.coffee) <<= (com.github.siasia.PluginKeys.webappResources in Compile)(_.get.head / ".." / "static" / "coffee-js")

seq(lessSettings: _*)

LessKeys.mini in (Compile, LessKeys.less) := true

resourceManaged in (Compile, LessKeys.less) <<= (webappResources in Compile)(_.get.head / ".." / "static" / "less-css")

seq(dependencyReportSettings: _*)
