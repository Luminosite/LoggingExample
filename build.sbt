//name := "LExample"
//organization in ThisBuild := "priv.L"
val sv = "2.12.3"
scalaVersion in ThisBuild := sv
version := "0.1.0"

enablePlugins(PackPlugin)
// PROJECTS

lazy val global = Project(
  id = "root",
  base = file("."))
  .aggregate(elastic)
//  .aggregate(udAppender, multiAppender, multiLogger, filters, elastic, structured)

lazy val structured = project
  .in(file("zest/structured"))
  .settings(
    name := "structured",
    settings ++ exJarSettings,
    mainClass in assembly := Some("priv.l.logging.example.main.ScalaLogMain"),
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.logback,
      dependencies.shapless,
      dependencies.playJson,
      dependencies.scalaReflect,
      dependencies.scalaMeta
    )
  )

lazy val elastic = project
  .in(file("zest/elastic"))
  .settings(
    name := "elastic",
    settings,
    mainClass in assembly := Some("priv.l.logging.example.main.Main"),
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
//      dependencies.metrics
      dependencies.elasticAppender,
      dependencies.logback,
      dependencies.scalaLogging
    )
  )

lazy val filters = project
  .in(file("filters"))
  .settings(
    name := "filters",
    settings,
    mainClass in assembly := Some("priv.l.logging.example.main.Main"),
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.logback,
      dependencies.scalaLogging
    )
  )

lazy val multiLogger = project
  .in(file("multi_logger"))
  .settings(
    name := "multiLogger",
    settings,
    mainClass in assembly := Some("priv.l.logging.example.main.Main"),
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.logback,
      dependencies.scalaLogging
    )
  )

lazy val udAppender = project
  .in(file("UDAppender"))
  .settings(
    name := "uda",
    settings ++ exJarSettings,
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.logback,
      dependencies.scalaLogging
    )
  )

lazy val multiAppender = project
  .in(file("multi_appender"))
  .settings(
    name := "ma",
    settings,
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.logback,
      dependencies.slf4jAPI
    )
  )

// DEPENDENCIES

lazy val dependencies =
  new {
    val logbackV        = "1.2.3"
    val logstashV       = "4.11"
    val scalaLoggingV   = "3.9.0"
    val slf4jV          = "1.7.25"
    val typesafeConfigV = "1.3.1"
    val pureconfigV     = "0.8.0"
    val monocleV        = "1.4.0"
    val akkaV           = "2.5.6"
    val scalatestV      = "3.0.4"
    val scalacheckV     = "1.13.5"

    val logbackCore    = "ch.qos.logback"             % "logback-core"             % "1.2.3"
    val logback        = "ch.qos.logback"             % "logback-classic"          % "1.2.3"
    val logstash       = "net.logstash.logback"       % "logstash-logback-encoder" % logstashV
    val scalaLogging   = "com.typesafe.scala-logging" %% "scala-logging"           % scalaLoggingV
    val slf4j          = "org.slf4j"                  % "jcl-over-slf4j"           % slf4jV
    val slf4jAPI       = "org.slf4j"                  % "slf4j-api"                % "1.7.25"
    val typesafeConfig = "com.typesafe"               % "config"                   % typesafeConfigV
    val akka           = "com.typesafe.akka"          %% "akka-stream"             % akkaV
    val monocleCore    = "com.github.julien-truffaut" %% "monocle-core"            % monocleV
    val monocleMacro   = "com.github.julien-truffaut" %% "monocle-macro"           % monocleV
    val pureconfig     = "com.github.pureconfig"      %% "pureconfig"              % pureconfigV
    val scalatest      = "org.scalatest"              %% "scalatest"               % scalatestV
    val scalacheck     = "org.scalacheck"             %% "scalacheck"              % scalacheckV

    val shapless       = "com.chuusai" %% "shapeless" % "2.3.2"
    val playJson       = "com.typesafe.play" %% "play-json" % "2.6.0" % "test"
    val scalaReflect   = "org.scala-lang" % "scala-reflect" % sv
    val scalaMeta      = "org.scalameta" %% "scalameta" % "1.8.0"

    val elasticAppender= "com.internetitem" % "logback-elasticsearch-appender" % "1.6"
//    val metrics        = "io.dropwizard.metrics" % "metrics-core" % "4.0.0"
  }

lazy val loggingDependencies = Seq(
  dependencies.logback,
  dependencies.logstash,
  dependencies.scalaLogging,
  dependencies.slf4j
)

lazy val commonDependencies = Seq(
  dependencies.typesafeConfig,
  dependencies.akka,
  dependencies.scalatest  % "test",
  dependencies.scalacheck % "test"
)

// main classes for pack
packMain := Map(
  "lb" -> "priv.l.logging.example.main.LogbackMain",
  "sl" -> "priv.l.logging.example.main.ScalaLogMain"
)

//externalResolvers := Resolver..withDefaultResolvers(resolvers.value, mavenCentral = false)

// SETTINGS

lazy val settings =
commonSettings ++
wartremoverSettings ++
scalafmtSettings

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    Resolver.defaultLocal,
    Resolver.mavenLocal,
    // make sure default maven local repository is added... Resolver.mavenLocal has bugs.
    "Local Maven Repository" at Path.userHome.asFile.toURI.toURL + "/.m2/repository",
    // for pack plugin
    "m1" at "http://repo1.maven.org/maven2/",
    // for PayPal repo
    "Paypal public" at "http://nexus.paypal.com/nexus/content/groups/public-all",
    "PayPal Nexus releases" at "http://nexus.paypal.com/nexus/content/repositories/releases",
    "PayPal Nexus snapshots" at "http://nexus.paypal.com/nexus/content/repositories/snapshots"
  )
)

lazy val exJarSettings = Seq(
  unmanagedBase := file("lib")
)

lazy val wartremoverSettings = Seq(
  wartremoverWarnings in (Compile, compile) ++= Warts.allBut(Wart.Throw)
)

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true,
    scalafmtTestOnCompile := true,
    scalafmtVersion := "1.2.0"
  )

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", _ @_*) => MergeStrategy.discard
    case _                           => MergeStrategy.first
  }
)
