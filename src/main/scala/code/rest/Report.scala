package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{OutputStreamResponse, Req, LiftRules}
import java.io.{BufferedInputStream, FileInputStream}

object Report extends RestHelper {

  def init() {
    LiftRules.dispatch.append(Report)
  }

  serve {
    case Req("report" :: file :: Nil, _, _) =>
      val fileName = "output/" + file + ".pdf"
      val bis = new BufferedInputStream(new FileInputStream(fileName)) //TODO: check if file exists
      val bArray = Stream.continually(bis.read).takeWhile(-1 !=).map(_.toByte).toArray
      val ac = Stream(bArray)
      OutputStreamResponse(out => ac.foreach(out.write))
  }
}
