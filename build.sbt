import sbt.addSbtPlugin

val scala3Version = "3.7.3"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
ThisBuild / assemblyMergeStrategy := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.preferProject
}

enablePlugins(BuildInfoPlugin)

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion)
buildInfoPackage := "de.htwg.winesmeeper"

lazy val root = project
  .in(file("."))
  .settings(
    name := "minesweeper",
    version := "1.2.1",

    scalaVersion := scala3Version,
    scalacOptions ++= Seq("-encoding", "utf-8"),
    coverageMinimumStmtTotal := 80,
    coverageFailOnMinimum := false,
    coverageHighlighting := true,
    coverageExcludedPackages := ".*Main.*;.*Routes.*;.*Config.*;",
    coverageExcludedFiles := "*Main*",

    libraryDependencies += "org.scalafx" %% "scalafx" % "24.0.2-R36",

    libraryDependencies += "org.scalameta" %% "munit" % "1.2.1" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    libraryDependencies += "org.scoverage" % "sbt-coveralls_2.12_1.0" % "1.3.15",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",
    libraryDependencies += "com.google.inject" % "guice" % "7.0.0",

    sonarProperties ++= Map(
      "sonar.projectKey"       -> "winesmeeper",
      "sonar.projectName"      -> "Scala Winesmeeper",
      "sonar.host.url"         -> "http://localhost:9000",
      "sonar.language"         -> "scala",
      "sonar.scala.coverage.reportPaths" -> "target/scala-3.scoverage-report/scoverage.xml",
      "sonar.sources"          -> "src/main/scala",
      "sonar.tests"            -> "src/test/scala"
    ),

    libraryDependencies ++= {
    // Determine OS version of JavaFX binaries
    lazy val osName = System.getProperty("os.name") match {
      case n if n.startsWith("Linux") => "linux"
      case n if n.startsWith("Mac") => "mac"
      case n if n.startsWith("Windows") => "win"
      case _ => throw new Exception("Unknown platform!")
  }

  sonarProperties ++= Map(
  "sonar.projectKey" -> "winesmeeper",
  "sonar.projectName" -> "Winesmeeper",
  "sonar.host.url" -> "http://localhost:9000",
  "sonar.login" -> sys.env("SONAR_TOKEN"),

  "sonar.sources" -> "src/main/scala",
  "sonar.tests"   -> "src/test/scala",

  "sonar.exclusions" ->
    "**/.scala-build/**,**/*.tasty,**/target/**",

  "sonar.scala.coverage.reportPaths" ->
    ((Compile / target).value /
      s"scala-${scalaVersion.value}" /
      "scoverage-report" /
      "scoverage.xml"
    ).getAbsolutePath
)


  val fxVersion = "23"
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % fxVersion classifier osName)
  }
)



