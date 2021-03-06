import com.github.play2war.plugin._

name := """play-heroku-seed"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .settings(Play2WarPlugin.play2WarSettings: _*)
  .settings(Play2WarKeys.servletVersion := "3.1")

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc4",
  "org.jsoup" % "jsoup" % "1.8.2",
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "junit" % "junit" % "4.12" % "test",
  "com.twilio.sdk" % "twilio-java-sdk" % "3.4.1",
  "org.twitter4j" % "twitter4j-stream" % "4.0.4",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)


//fork in run := true