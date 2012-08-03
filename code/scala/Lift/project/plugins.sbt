resolvers := Seq(
  "Instantor Nexus" at "http://www.instantor.com/nexus/content/groups/public/"
, Resolver.url("Instantor Nexus (Ivy)",
    new URL("http://www.instantor.com/nexus/content/groups/public/"))(Resolver.ivyStylePatterns)
)

externalResolvers <<= resolvers map { rs =>
  Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
}

// =======================================================================================

// +-------------------------------------------------------------------------------------+
// | SBT Eclipse (https://github.com/typesafehub/sbteclipse)                             |
// | Creates .project and .classpath files for easy Eclipse project imports              |
// |                                                                                     |
// | See also: Eclipse downloads (http://www.eclipse.org/downloads/)                     |
// | See also: Scala IDE downloads (http://download.scala-ide.org/)                      |
// +-------------------------------------------------------------------------------------+

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.0")

 // +-------------------------------------------------------------------------------------+
// | XSBT Web plugin (https://github.com/siasia/xsbt-web-plugin)                         |
// | Implements SBT 0.7.x Web project actions: "jetty-run" -> "container:start", etc ... |
// +-------------------------------------------------------------------------------------+

libraryDependencies <+= sbtVersion(v => "com.github.siasia" %% "xsbt-web-plugin" % (v+"-0.2.11.1"))

// +-------------------------------------------------------------------------------------+
// | CoffeeScripted SBT (https://github.com/softprops/coffeescripted-sbt)                |
// | Automates "compilation" of coffeescript files (/src/main/*.coffee) into javascript  |
// |                                                                                     |
// | See also: Coffeescript reference (http://jashkenas.github.com/coffee-script/)       |
// +-------------------------------------------------------------------------------------+

addSbtPlugin("me.lessis" % "coffeescripted-sbt" % "0.2.3")

// +-------------------------------------------------------------------------------------+
// | Less SBT (https://github.com/softprops/less-sbt)                                    |
// | Automates "compilation" of less files (/src/main/*.less) into css                   |
// |                                                                                     |
// | See also: LESS reference (http://lesscss.org/)                                      |
// +-------------------------------------------------------------------------------------+

addSbtPlugin("me.lessis" % "less-sbt" % "0.1.10")

// +-------------------------------------------------------------------------------------+
// | Dependency report plugin (hhttps://github.com/mslinn/dependencyReport)              |
// | Lists all jars in a nicely formatted way for easy inspection.                       |
// +-------------------------------------------------------------------------------------+

addSbtPlugin("com.micronautics" % "dependencyreport" % "0.1.0")
