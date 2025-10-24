val scala3Version = "3.7.3"
val javaVersion = sys.props("java.version").split("\\.")(0)

val scalafxVersion =
  if (javaVersion.toInt >= 25) "25.0.2-R36" else "21.0.0-R32"

lazy val root = project
  .in(file("."))
  .settings(
    name := "minesweeper",
    version := "0.2.0-SNAPSHOT",

    
    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test,
    libraryDependencies += "org.scalafx" %% "scalafx" % "24.0.2-R36",

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
