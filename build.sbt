val scala3Version = "2.13.14"

lazy val root = project
  .in(file("."))
  .settings(
    name         := "ScalaTest Play JSON Equality",
    version      := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.8.2",
    ),
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
  )
