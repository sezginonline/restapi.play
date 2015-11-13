name := """REST-API"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.mongodb" % "mongo-java-driver" % "3.0.4",
  "org.mongodb.morphia" % "morphia" % "1.0.1",
  "io.jsonwebtoken" % "jjwt" % "0.6.0",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

javacOptions ++= Seq("-Xlint:deprecation", "-Xlint:unchecked")

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
