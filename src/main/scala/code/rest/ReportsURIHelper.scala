package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http._
import java.io.{ByteArrayOutputStream, OutputStream, BufferedInputStream, FileInputStream}
import org.pentaho.reporting.engine.classic.core.MasterReport
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil
import org.pentaho.reporting.libraries.resourceloader.ResourceManager
import java.net.URL
import net.liftweb.util.BasicTypesHelpers.AsInt
import net.liftweb.common.Logger
import net.liftweb.http.InMemoryResponse
import scala._
import net.liftweb.http.InMemoryResponse
import scala.Predef._
import net.liftweb.http.InMemoryResponse
import org.pentaho.reporting.engine.classic.core.modules.output.table.html.HtmlReportUtil

object ReportsURIHelper extends RestHelper with Logger {

  def init() {
    LiftRules.dispatch.append(ReportsURIHelper)
  }

  def serveToBrowser(fileName: String, headers: List[(String, String)]) = {
    if (new java.io.File(fileName).exists()) {
      val bis = new BufferedInputStream(new FileInputStream(fileName))
      val bArray = Stream.continually(bis.read).takeWhile(-1 !=).map(_.toByte).toArray
      InMemoryResponse(bArray, headers, Nil, 200)
    } else {
      error("File " + fileName + " doesn't exist")
      S.redirectTo("/reports")
    }
  }


  serve {
    case Req("report" :: "pdf" :: file :: Nil, _, _) =>
      serveToBrowser("output/" + file + ".pdf", ("content", "application/pdf") :: Nil)
    case Req("report" :: "xls" :: file :: Nil, _, _) =>
      val header = List(("content", "application/vnd.ms-excel"), ("Content-Transfer-Encoding", "binary"), ("Content-Disposition", "attachement; filename=PentahoTestReport.xls"))
      serveToBrowser("output/" + file + ".xls", header)
  }

}
