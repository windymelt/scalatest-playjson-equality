lazy val scala212 = "2.12.18"
lazy val scala213 = "2.13.14"
lazy val scala3 = "3.3.1"

lazy val supportedScalaVersions = List(scala212, scala213, scala3)

ThisBuild / scalaVersion := scala213

lazy val root = project
  .in(file("."))
  .settings(
    name         := "ScalaTest Play JSON Equality",
    version      := "0.1.0-SNAPSHOT",
    crossScalaVersions := supportedScalaVersions,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.10.0",
    ),
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
  )
