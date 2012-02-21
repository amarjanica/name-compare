// +-------------------------------------------------------------------------------------+
// | SBT Eclipse (https://github.com/typesafehub/sbteclipse)                             |
// | Creates .project and .classpath files for easy Eclipse project imports              |
// |                                                                                     |
// | See also: Eclipse downloads (http://www.eclipse.org/downloads/)                     |
// | See also: Scala IDE downloads (http://download.scala-ide.org/)                      |
// +-------------------------------------------------------------------------------------+

resolvers += Classpaths.typesafeResolver

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.0.0-RC1")

// +-------------------------------------------------------------------------------------+
// | SBT Scalariform (https://github.com/typesafehub/sbtscalariform)                     |
// | Performs source code formatting                                                     |
// |                                                                                     |
// | See also: Scalariform reference (http://mdr.github.com/scalariform/)                |
// +-------------------------------------------------------------------------------------+

resolvers += Classpaths.typesafeResolver

addSbtPlugin("com.typesafe.sbtscalariform" % "sbtscalariform" % "0.3.0")
