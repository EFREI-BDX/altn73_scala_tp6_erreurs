ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .settings(
    name := "altn73_scala_tp6_erreurs"
  )

libraryDependencies += "dev.zio" %% "zio-prelude" % "1.0.0-RC42"
