val scala3Version = "3.7.3"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

lazy val root = project
  .in(file("."))
  .settings(
    name := "minesweeper",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    coverageEnabled := true,
    coverageMinimumStmtTotal := 80,
    coverageFailOnMinimum := true,
    coverageHighlighting := true,
    coverageExcludedPackages := ".*Main.*;.*Routes.*;.*Config.*;",
    coverageExcludedFiles := "*Main*",

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    libraryDependencies += "org.scoverage" % "sbt-scoverage" % "1.8.2",
    libraryDependencies += "org.scoverage" % "sbt-coveralls" % "1.3.1",
  )
