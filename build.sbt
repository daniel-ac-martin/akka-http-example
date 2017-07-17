lazy val akkaHttpVersion = "10.0.9"
lazy val akkaVersion    = "2.5.3"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.2"
    )),
    mainClass in assembly := Some("com.example.Main"),
    name := "example",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      "com.typesafe.akka"    %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka"    %% "akka-stream"              % akkaVersion,
      // XML support
      "com.typesafe.akka"    %% "akka-http-xml"            % akkaHttpVersion,
      // JSON support
      "de.heikoseeberger"    %% "akka-http-json4s"         % "1.17.0",
      "org.json4s"           %% "json4s-native"            % "3.5.2",
      // Testing
      "com.typesafe.akka"    %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "org.scalatest"        %% "scalatest"                % "3.0.1"         % Test,
      // Logging
      "com.typesafe.akka"    %% "akka-slf4j"               % akkaVersion,
      "ch.qos.logback"       %  "logback-classic"          % "1.2.3",
      "net.logstash.logback" %  "logstash-logback-encoder" % "4.11",
      // Database
      "com.github.mauricio"  %% "postgresql-async"         % "0.2.21",
      "org.scalikejdbc"      %% "scalikejdbc-async"        % "0.8.0"
    )
  )
