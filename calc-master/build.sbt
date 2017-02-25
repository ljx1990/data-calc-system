name := "calc-master"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"


libraryDependencies ++= {
  val akkaV       = "2.4.17"
  val akkaHttpV   = "10.0.1"
  val scalaTestV  = "3.0.1"
  Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % akkaV,
    "com.typesafe.akka" % "akka-remote_2.11" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "org.scalatest" %% "scalatest" % scalaTestV % "test"
  )
}
