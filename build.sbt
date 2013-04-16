name := "Lift pentaho reporting"

version := "0.0.1"

organization := "net.liftweb"

scalaVersion := "2.10.0"

resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "http://oss.sonatype.org/content/repositories/releases",
				"pentaho"         at "http://repository.pentaho.org/artifactory/repo"
                )

seq(com.github.siasia.WebPlugin.webSettings :_*)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.5-RC2"
  val preVersion = "3.9.2-GA"
  val plVersion = "1.2.4"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftmodules"   %% "lift-jquery-module" % (liftVersion + "-2.2"),
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.7.v20120910"  % "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.0.6",
    "org.specs2"        % "specs2_2.10.0-M7"    % "1.12.1.1"         % "test",
	"pentaho-reporting-engine"  % "pentaho-reporting-engine-classic-core" % preVersion % "compile",
	//"pentaho-reporting-engine"  % "pentaho-reporting-engine-classic-extensions" % preVersion % "compile",
	"pentaho-library"  % "libloader" % plVersion % "compile",
	"pentaho-library"  % "libbase" % plVersion % "compile",
	"pentaho-library"  % "libxml" % plVersion % "compile",
	"pentaho-library"  % "libserializer" % plVersion % "compile",
	"pentaho-library"  % "librepository" % plVersion % "compile",
	"pentaho-library"  % "libformula" % plVersion % "compile",
	"pentaho-library"  % "libfonts" % plVersion % "compile",
	"pentaho-library"  % "libformat" % plVersion % "compile",
	"pentaho-library"  % "libdocbundle" % plVersion % "compile",
	"com.lowagie"      % "itext"        %"2.1.7"    % "compile"
  )
}

