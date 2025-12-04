import sbt.addSbtPlugin

val scala3Version = "3.7.3"
val javaVersion = sys.props("java.version").split("\\.")(0)

val scalafxVersion =
  if (javaVersion.toInt >= 25) "25.0.2-R36" else "21.0.0-R32"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
ThisBuild / assemblyMergeStrategy := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.preferProject
}

lazy val root = project
  .in(file("."))
  .settings(
    name := "minesweeper",
    version := "0.5.0",
    
    scalaVersion := scala3Version,
    scalacOptions ++= Seq("-encoding", "utf-8"),
    coverageMinimumStmtTotal := 100,
    coverageFailOnMinimum := false,
    coverageHighlighting := true,
    coverageExcludedPackages := ".*Main.*;.*Routes.*;.*Config.*;",
    coverageExcludedFiles := "*Main*",

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test,
    libraryDependencies += "org.scalafx" %% "scalafx" % "24.0.2-R36",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    libraryDependencies += "org.scoverage" % "sbt-coveralls_2.12_1.0" % "1.3.15",

    libraryDependencies ++= {
    // Determine OS version of JavaFX binaries
    lazy val osName = System.getProperty("os.name") match {
      case n if n.startsWith("Linux") => "linux"
      case n if n.startsWith("Mac") => "mac"
      case n if n.startsWith("Windows") => "win"
      case _ => throw new Exception("Unknown platform!")
  }
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % javaVersion classifier osName)
  }
)




