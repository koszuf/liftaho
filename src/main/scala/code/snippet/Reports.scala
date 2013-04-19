package code.snippet

import net.liftweb._
import util.Helpers._
import net.liftweb.http._
import net.liftweb.common.Logger


import code.lib.PentahoReport

class Reports extends Logger {

  var name = "Someone"

  def render = {
    "#name" #> SHtml.text(name, name = _) &
      "type=submit #pdf" #> SHtml.submit("Open", () => PentahoReport("report")) &
      "type=submit #pdfparam" #> SHtml.submit("Open", () => PentahoReport("report2", List(name, now), PentahoReport.PDF)) &
      "type=submit #xls" #> SHtml.submit("Open", () => PentahoReport("report", List(), PentahoReport.XLS)) &
      "type=submit #xlsparam" #> SHtml.submit("Open", () => PentahoReport("report2", List(name, now), PentahoReport.XLS))
  }
}
