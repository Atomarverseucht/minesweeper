val scala3Version = "3.7.3"

enablePlugins(DockerPlugin)

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
ThisBuild / assemblyMergeStrategy := {
    case PathList("META-INF", "services", xs @ _*) => MergeStrategy.concat
    case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
    case PathList("META-INF", xs @ _*) if xs.lastOption.exists(_.endsWith(".SF")) => MergeStrategy.discard
    case PathList("META-INF", xs @ _*) if xs.lastOption.exists(_.endsWith(".DSA")) => MergeStrategy.discard
    case PathList("META-INF", xs @ _*) if xs.lastOption.exists(_.endsWith(".RSA")) => MergeStrategy.discard
    case PathList("META-INF", "versions", xs @ _*) => MergeStrategy.first
    case PathList("module-info.class") => MergeStrategy.discard
    case x => MergeStrategy.first
}

enablePlugins(BuildInfoPlugin)

docker / dockerfile := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("eclipse-temurin:21-jdk")
    add(artifact, artifactTargetPath)
    runRaw("""apt-get update && apt-get install -y \
    libx11-6 \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgtk-3-0 \
    libglib2.0-0 \
    libfreetype6 \
    fontconfig \
 && rm -rf /var/lib/apt/lists/*""")
    entryPoint("java")
    cmd("-Dprism.order=sw", "-Djavafx.platform=gtk", "-jar", artifactTargetPath)
  }
}

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion)
buildInfoPackage := "de.htwg.winesmeeper"

lazy val root = project
  .in(file("."))
  .settings(
    name := "minesweeper",
    version := "2.2.1",

    scalaVersion := scala3Version,
    scalacOptions ++= Seq("-encoding", "utf-8"),
    coverageMinimumStmtTotal := 80,
    coverageFailOnMinimum := false,
    coverageHighlighting := true,
    coverageExcludedPackages := "*\\\\/de\\\\/htwg\\\\/winesmeeper\\\\/(?:Main|aView\\\\/(?:TUI|GUI))",
    coverageExcludedFiles := "*Main*",

    libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32", // aus Kompatibilität nicht aktueller
    libraryDependencies += "org.scalameta" %% "munit" % "1.2.1" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    libraryDependencies += "org.scoverage" % "sbt-coveralls_2.12_1.0" % "1.3.15",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.4.0",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.8",

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

  val fxVersion = "21"
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % fxVersion classifier osName)
  }
)