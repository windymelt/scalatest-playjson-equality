import xerial.sbt.Sonatype.sonatypeCentralHost

lazy val scala212               = "2.12.18"
lazy val scala213               = "2.13.14"
lazy val scala3                 = "3.3.1"
lazy val supportedScalaVersions = List(scala212, scala213, scala3)

Global / useGpgPinentry := true // for yubikey

ThisBuild / sonatypeCredentialHost := sonatypeCentralHost
ThisBuild / publishTo              := sonatypePublishToBundle.value

lazy val root = project
  .in(file("."))
  .settings(
    scalaVersion         := scala213,
    organization         := "dev.capslock",
    organizationName     := "Windymelt",
    organizationHomepage := Some(url("https://www.3qe.us/")),
    versionScheme        := Some("early-semver"),
    name                 := "ScalaTest Play JSON Equality",
    version              := "0.0.1", // fails when SNAPSHOT?
    crossScalaVersions   := supportedScalaVersions,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.10.0",
    ),
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/windymelt/scalatest-playjson-equality"),
        "scm:git@github.com:windymelt/scalatest-playjson-equality.git",
      ),
    ),
    developers := List(
      Developer(
        id = "Windymelt",
        name = "Windymelt",
        email = "windymelt@capslock.dev",
        url = url("https://www.3qe.us/"),
      ),
    ),
    description := "You can compare `JsValue` with `JsNumber` tolerance in ScalaTest",
    licenses := List(
      "BSD 2-Clause" -> new URL("https://opensource.org/license/BSD-2-Clause"),
    ),
    homepage := Some(url("https://github.com/windymelt/scalatest-playjson-equality")),

// Remove all additional repository other than Maven Central from POM
    pomIncludeRepository := { _ => false },
  )
