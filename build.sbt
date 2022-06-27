lazy val commonSettings = Seq(
  name := "http4s_example",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.13.8",
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-language:postfixOps",
    "-feature"
  )
)

val http4sVersion = "0.22.14"
val circeVersion = "0.14.2"
val monixVersion = "3.4.1"

lazy val http4sLibraries = Seq(
  "org.http4s" %% "http4s-core"         % http4sVersion,
  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.http4s" %% "http4s-circe"        % http4sVersion
)

lazy val circeLibraries = Seq(
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-literal" % circeVersion
)

lazy val monixLibraries = Seq(
  "io.monix" %% "monix" % monixVersion
)

lazy val testLibraries = Seq(
  "org.scalatest" %% "scalatest" % "3.2.11" % Test
)

lazy val libraries = (
    http4sLibraries ++
    circeLibraries ++
    monixLibraries ++
    testLibraries
  ).map(_.withSources())



lazy val root = (project in file("."))
  .settings(commonSettings,
    libraryDependencies ++= libraries
  )
