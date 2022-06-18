lazy val commonSettings = Seq(
  name := "http4s_example",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.13.8"
)

val http4sVersion = "0.23.12"

lazy val http4sLibraries = Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion
)

lazy val testLibraries = Seq(
  "org.scalatest" %% "scalatest" % "3.2.11" % Test
)

lazy val libraries = http4sLibraries ++ testLibraries

lazy val root = (project in file("."))
  .settings(commonSettings,
    libraryDependencies ++= libraries
  )
