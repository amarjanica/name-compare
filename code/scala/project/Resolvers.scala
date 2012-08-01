import sbt._
import Keys._

object Repositories {
  val InstantorNexus           = "Instantor Nexus"            at "http://www.instantor.com/nexus/content/groups/public/"
  val InstantorReleases        = "Instantor Releases"         at "http://www.instantor.com/nexus/content/repositories/releases/"
  val InstantorPrivateReleases = "Instantor Private Releases" at "http://www.instantor.com/nexus/content/repositories/releases-private/"
}

//  ---------------------------------------------------------------------------

object Resolvers {
  import Repositories._

  val settings = Seq(
    resolvers := Seq(
      InstantorPrivateReleases
    , InstantorNexus
    )
  , externalResolvers <<= resolvers map { rs =>
      Resolver.withDefaultResolvers(rs, mavenCentral = false, scalaTools = false)
    }
  )
}

//  ---------------------------------------------------------------------------

object Publishing {
  import Repositories._

  val settings = Seq(
    publishTo := Some(InstantorPrivateReleases)
  , credentials += Credentials(Path.userHome / ".publish" / "instantor.credentials")
  , publishArtifact in (Compile, packageSrc) := false
  , publishArtifact in (Compile, packageDoc) := false
  )
}
