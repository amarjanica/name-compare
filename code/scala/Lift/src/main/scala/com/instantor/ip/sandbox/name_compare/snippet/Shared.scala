package com.instantor.ip.sandbox
package name_compare
package snippet

import hr.element.etb.lift.snippet._

object SharedJS extends StaticJS {
  override val root = "sandbox/name-compare/shared"

  override val defaultVersions = Map(
    "jquery"                             -> "1.7.2"
  , "jquery.bxSlider"                    -> "3.0"
  , "jquery.scrollTo"                    -> "1.4.2"
  , "jquery.mousewheel"                  -> "3.0.6"
  , "jquery.fancybox"                    -> "2.0.6"
  , "jquery.fancybox-buttons"            -> "2.0.5"
  , "jquery.easing"                      -> "1.3"
  , "jquery.validate"                    -> "1.9.0"
  , "jquery.validate-additional-methods" -> "1.9.0"
  , "jquery.fieldselection"              -> "0.1.1"

  , "knockout"         -> "2.1.0"
  , "knockout.mapping" -> "2.2.4"
  , "json2" -> "11.10.19"

  , "less" -> "1.3.0"
  , "coffeescript" -> "1.2.1"

  , "bootstrap" -> "2.0.4"

  , "modernizr.custom" -> "2.5.3"
  )
}

object SharedCSS extends StaticCSS {
  override val root = "sandbox/name-compare/shared"

  override val defaultVersions = Map(
    "jquery.fancybox"         -> "2.0.6"
  , "jquery.fancybox-buttons" -> "2.0.5"
  )
}
