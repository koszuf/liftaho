package code.snippet

import net.liftweb._
import util.Helpers._
import org.pentaho.reporting.engine.classic.core.{MasterReport, ClassicEngineBoot}
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil
import org.pentaho.reporting.libraries.resourceloader.ResourceManager
import java.net.URL
import java.io.{FileOutputStream}
import net.liftweb.http._
import net.liftweb.common.Logger


class Reports extends Logger {

  def saveAndOpen {
    val manager = new ResourceManager
    manager.registerDefaults
    val reportPath = "file:reports/report.prpt"
    val fileName = randomString(25)
    val filePath = "output/" + fileName + ".pdf"
    val res = manager.createDirectly(new URL(reportPath), classOf[MasterReport])
    val report = res.getResource.asInstanceOf[MasterReport]
    PdfReportUtil.createPDF(report, new FileOutputStream(filePath))
    S.redirectTo("report/" + fileName)
  }

  def saveToDisk {
    val manager = new ResourceManager
    manager.registerDefaults
    val reportPath = "file:reports/report.prpt"
    val filePath = "output/report_test1.pdf"
    val res = manager.createDirectly(new URL(reportPath), classOf[MasterReport])
    val report = res.getResource.asInstanceOf[MasterReport]
    PdfReportUtil.createPDF(report, new FileOutputStream(filePath))
  }

  def render = {
    "type=submit #ver1" #> SHtml.submit("Generate PDF ver 1", () => saveToDisk) &
      "type=submit #ver2" #> SHtml.submit("Generate PDF ver 2", () => saveAndOpen)
  }


}
