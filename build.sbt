
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
    version := "0.6.3-dev.1",

    scalaVersion := scala3Version,
    scalacOptions ++= Seq("-encoding", "utf-8"),
    coverageMinimumStmtTotal := 100,
    coverageFailOnMinimum := false,
    coverageHighlighting := true,
    coverageExcludedPackages := ".*Main.*;.*Routes.*;.*Config.*;",
    coverageExcludedFiles := "*Main*",

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test,
)




