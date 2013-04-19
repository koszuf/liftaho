package code.lib

import org.pentaho.reporting.libraries.resourceloader.ResourceManager
import net.liftweb.util.Helpers._
import java.net.URL
import org.pentaho.reporting.engine.classic.core.modules.output.pageable.pdf.PdfReportUtil
import java.io.FileOutputStream
import net.liftweb.http.S
import net.liftweb.common.Logger
import org.pentaho.reporting.engine.classic.core.modules.output.table.xls.ExcelReportUtil
import org.pentaho.reporting.engine.classic.core.MasterReport



/**
 * User: rakoczy
 * Date: 17.04.13
 * Time: 11:19
 */
object PentahoReport extends Enumeration with Logger {

  val PDF, XLS = Value

  /**
   *
   * @param reportName
   * Printing PDF report without parameters
   */
  def apply(reportName: String) {
    apply(reportName, List(), PDF)
  }


  /**
   *
   * @param reportName
   * @param parameters
   * @param output
   */
  def apply(reportName: String, parameters: List[Any], output: Value) {

    val fileName = randomString(40)

    val manager = new ResourceManager
    manager.registerDefaults
    val reportPath = "file:reports/" + reportName + ".prpt"
    val ext: String = output match {
      case PDF => ".pdf"
      case XLS => ".xls"
      case _ => {
        error("Unknow output type");
        ""
      }
    }
    val filePath = "output/" + fileName + ext
    info("Opening " + reportName)
    val res = manager.createDirectly(new URL(reportPath), classOf[MasterReport])
    val report = res.getResource.asInstanceOf[MasterReport]

    def setParameters(param: List[Any], n: Int): Unit = {
      param match {
        case head :: tail => {
          val paramName = "Param" + n.toString
          report.getParameterValues.put(paramName, head)
          info("Set " + paramName + ": " + head)
          setParameters(tail, n + 1)
        }
        case List() => ()
      }
    }
    info("Setting parameters.")
    setParameters(parameters, 1)


    def createPDF {
      info("Creating PDF file")
      val reportOk =
        PdfReportUtil.createPDF(report, new FileOutputStream(filePath))
      if (reportOk)
        S.redirectTo("/report/pdf/" + fileName)
      else {
        error("Problem with pdf report creation")
        S.redirectTo("/")
      }
    }

    def createExcel {
      info("Creating excel file")
      ExcelReportUtil.createXLS(report, new FileOutputStream(filePath))
      S.redirectTo("/report/xls/" + fileName)
    }

    output match {
      case PDF => createPDF
      case XLS => createExcel
    }

  }
}

