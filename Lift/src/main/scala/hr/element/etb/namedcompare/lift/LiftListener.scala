package hr.element.etb
package name_compare
package lift

//import elements.ApiParamsData

import net.liftweb.http._
import rest._
import net.liftweb.common.Full
import net.liftweb.common.Box
import scala.xml._
import net.liftweb.util.BindPlus._
import net.liftweb.util.Helpers._
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apache.commons.codec.binary.Base64
import java.io.ByteArrayInputStream
import java.io.FileInputStream

object LiftListener extends RestHelper {
  //val logger = LoggerFactory.getLogger(LiftListener.getClass())
  serve {
    case req @ Req("compare" :: src :: dst :: Nil, _, GetRequest) =>
      //Reqparams include file extension and base64 String
      println("Header: %s" format req.headers)
      println("Body: %s" format req.body)
      val resp = "DINAMO"
      PlainTextResponse(src + dst)
  }


}



