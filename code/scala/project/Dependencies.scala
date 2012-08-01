import sbt._

object Dependencies {
  // Date/Time libraries
  val jodaTime = Seq(
    "org.joda" % "joda-convert" % "1.2",
    "joda-time" % "joda-time" % "2.1"
  )

  val scalaTime = jodaTime ++ Seq(
    "org.scala-tools.time" % "time_2.9.1" % "0.5"
  )

  // Transliteration  
  val icu4j = "com.ibm.icu" % "icu4j" % "49.1"
  
  // String comparisons
  val simmetrics = "uk.ac.shef.wit" % "simmetrics" % "1.6.2"

  // Web
  val jetty = Seq(
    "org.eclipse.jetty" % "jetty-webapp" % "7.6.5.v20120716" % "container"
  , "org.eclipse.jetty.orbit" % "javax.servlet" % "2.5.0.v201103041518" % "container" artifacts Artifact("javax.servlet", "jar", "jar")
  )

  val liftWebkit = "net.liftweb" % "lift-webkit_2.9.1" % "2.4"

  // Element Toolbox
  val etbUtil   = "hr.element.etb" %% "etb-util" % "0.2.16"
  val etbLift   = "hr.element.etb" %% "etb-lift" % "0.0.21"

  // Logging
  val logback = "ch.qos.logback" % "logback-classic" % "1.0.6"

  // Testing
  val scalaTest = "org.scalatest" %% "scalatest" % "2.0.M2" % "test"
}
