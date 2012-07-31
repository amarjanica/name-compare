import sbt._

object Dependencies {
  //scalaTime
  val jodaTime = Seq(
    "org.joda" % "joda-convert" % "1.2",
    "joda-time" % "joda-time" % "2.1"
  )
    
  val scalaTime = jodaTime ++ Seq(
    "org.scala-tools.time" %% "time" % "0.5"
  )

   val namedcomparedeps = Seq(
      "com.ibm.icu" % "icu4j" % "49.1"
    , "uk.ac.shef.wit" % "simmetrics" % "1.6.2"
    , "org.scalatest" %% "scalatest" % "2.0.M2" % "test"
  )

  // Web
  val jetty   = Seq(
    "org.eclipse.jetty" % "jetty-webapp" % "7.6.5.v20120716" % "container"
  , "org.eclipse.jetty.orbit" % "javax.servlet" % "2.5.0.v201103041518" % "container" artifacts Artifact("javax.servlet", "jar", "jar")   )

  //etb
  val etbUtil   = "hr.element.etb" %% "etb-util" % "0.2.16"
  val etbLift   = "hr.element.etb" %% "etb-lift" % "0.0.21"
  val etbImg    = "hr.element.etb" %% "etb-img"  % "0.1.0"

  //lift
  val liftVersion = "2.4"
  val liftJson   = "net.liftweb" %% "lift-json"   % liftVersion
  val liftCommon = "net.liftweb" %% "lift-common" % liftVersion
  val liftUtil   = "net.liftweb" %% "lift-util"   % liftVersion
  val liftWebkit = "net.liftweb" %% "lift-webkit" % liftVersion

  
  //logging
  val logback = "ch.qos.logback" % "logback-classic" % "1.0.0" 


  //dispatch
  val dispatch = "net.databinder" %% "dispatch-http" % "0.8.7"

  //test
  val scalaTest = "org.scalatest" %% "scalatest" % "1.8" % "test"
}
