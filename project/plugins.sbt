addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.14.4")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.8")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.3.15")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.3.1")
addSbtPlugin("com.sonar-scala" % "sbt-sonar" % "2.3.0")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.11.0")

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
