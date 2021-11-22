name := "Proyecto final Scala twitter PFSD"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("eci.edu.co")
libraryDependencies +="com.google.code.gson" % "gson" % "2.2.4"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.8"
libraryDependencies += "com.dimafeng" %% "testcontainers-scala-scalatest" % "0.39.8" % "test"
libraryDependencies += "com.dimafeng" %% "testcontainers-scala-postgresql" % "0.39.8" % "test"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.23"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.0.0"
libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % "3.0.0"
val sparkVersion = "3.2.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)
libraryDependencies ++= Seq( "org.twitter4j" % "twitter4j-core" % "4.0.5", "org.twitter4j" % "twitter4j-stream" % "4.0.5" )
// (optional) If you need scalapb/scalapb.proto or anything from
// google/protobuf/*.proto
libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
)

Test / fork := true