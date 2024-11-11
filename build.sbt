import xerial.sbt.Sonatype.sonatypeCentralHost

lazy val scala212 = "2.12.18"
lazy val scala213 = "2.13.14"
lazy val scala3   = "3.3.1"

lazy val supportedScalaVersions = List(scala212, scala213, scala3)

ThisBuild / scalaVersion         := scala213
ThisBuild / organization         := "dev.capslock"
ThisBuild / organizationName     := "Windymelt"
ThisBuild / organizationHomepage := Some(url("https://www.3qe.us/"))
ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/windymelt/scalatest-playjson-equality"),
    "scm:git@github.com:windymelt/scalatest-playjson-equality.git",
  ),
)
ThisBuild / developers := List(
  Developer(
    id = "Windymelt",
    name = "Windymelt",
    email = "windymelt@capslock.dev",
    url = url("https://www.3qe.us/"),
  ),
)

ThisBuild / description := "You can compare `JsValue` with `JsNumber` tolerance in ScalaTest"
ThisBuild / licenses := List(
  "BSD 2-Clause" -> new URL("https://opensource.org/license/BSD-2-Clause"),
)
ThisBuild / homepage := Some(url("https://github.com/windymelt/scalatest-playjson-equality"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }

ThisBuild / sonatypeCredentialHost := sonatypeCentralHost

lazy val root = project
  .in(file("."))
  .settings(
    name               := "ScalaTest Play JSON Equality",
    version            := "0.1.0-SNAPSHOT",
    publishTo          := sonatypePublishToBundle.value,
    crossScalaVersions := supportedScalaVersions,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.10.0",
    ),
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
  )
