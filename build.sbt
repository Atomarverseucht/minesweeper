import sbt.addSbtPlugin

val scala3Version = "3.7.3"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
ThisBuild / assemblyMergeStrategy := {
    case PathList("META-INF", xs*) => MergeStrategy.discard
    case x => MergeStrategy.preferProject
}


lazy val root = project
  .in(file("."))
  .settings(
    name := "minesweeper",
    version := "0.4.0-dev.1",

    scalaVersion := scala3Version,
    scalacOptions ++= Seq("-encoding", "utf-8"),
    coverageMinimumStmtTotal := 100,
    coverageFailOnMinimum := false,
    coverageHighlighting := true,
    coverageExcludedPackages := ".*Main.*;.*Routes.*;.*Config.*;",
    coverageExcludedFiles := "*Main*",

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    libraryDependencies += "org.scoverage" % "sbt-coveralls_2.12_1.0" % "1.3.15",
  )




