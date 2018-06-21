name := """play-scala-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(Seq(
    PlayKeys.playOmnidoc := false,
  ))
  .enablePlugins(PlayScala, PlayAkkaHttp2Support)

resolvers += Resolver.sonatypeRepo("snapshots")

resolvers += Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.196"

enablePlugins(AkkaGrpcPlugin)
