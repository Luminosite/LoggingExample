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
  .aggregate(elastic, structured)
//  .aggregate(udAppender, multiAppender, multiLogger, filters, elastic, structured)

lazy val structured = project
  .in(file("zest/structured"))
  .settings(
    name := "structured",
    settings ++ exJarSettings,
    mainClass in assembly := Some("priv.l.logging.example.main.ScalaLogMain"),
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.metrics,
      dependencies.logback,
      dependencies.shapless,
      dependencies.playJson,
      dependencies.scalaReflect,
      dependencies.scalaMeta
    )
  )
  .aggregate(slime)
  .dependsOn(slime)

lazy val elastic = project
  .in(file("zest/elastic"))
  .settings(
    name := "elastic",
    settings ++ exJarSettings,
    mainClass in assembly := Some("priv.l.logging.example.main.Main"),
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.shapless,
      dependencies.playJson,
      dependencies.scalaReflect,
      dependencies.metrics,
      dependencies.amzon,
      dependencies.jackson,
//      dependencies.elasticAppender,
      dependencies.logback,
      dependencies.scalaLogging
    )
  )
  .aggregate(slime)
  .dependsOn(structured, slime)


lazy val macros = project
  .in(file("zest/macros"))
  .settings(slimeSettings: _*)
  .settings(
    name := "slime-macros",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scalameta" %% "scalameta" % "1.8.0"
    )
  )

lazy val slime = project
  .in(file("zest/slime"))
  .settings(slimeSettings: _*)
  .aggregate(macros)
  .dependsOn(macros)
  .settings(name := "slime")

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
    val metrics        = "io.dropwizard.metrics" % "metrics-core" % "4.0.0"
    val jackson = "com.fasterxml.jackson.core" % "jackson-core" % "2.8.0"
    val amzon          = "com.amazonaws" % "aws-java-sdk-core" % "1.11.31" % "provided"

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

lazy val slimeSettings = Seq(
  version := "0.2.1",
  scalaVersion := "2.12.3",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  crossScalaVersions := Seq("2.12.3"),
  organization := "com.slime",
  homepage := Some(url("https://github.com/petterarvidsson/slime")),
  organizationHomepage := Some(url("https://github.com/petterarvidsson/slime")),
  licenses := Seq("MIT License" -> url("https://opensource.org/licenses/MIT")),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "com.chuusai" %% "shapeless" % "2.3.2",
    "ch.qos.logback" % "logback-classic" % "1.2.3" % "provided,test",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "com.typesafe.play" %% "play-json" % "2.6.0" % "test"
  ),
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  publishArtifact in Test := false,
  pomIncludeRepository := (_ => false),
  pomExtra :=
    <scm>
      <url>git@github.com:petterarvidsson/slime.git</url>
      <connection>scm:git:git@github.com:petterarvidsson/slime.git</connection>
    </scm>
      <developers>
        <developer>
          <id>petterarvidsson</id>
          <name>Petter Arvidsson</name>
          <url>https://github.com/petterarvidsson</url>
        </developer>
        <developer>
          <id>hansjoergschurr</id>
          <name>Hans-Jörg Schurr</name>
          <url>https://github.com/hansjoergschurr</url>
        </developer>
        <developer>
          <id>lucastorri</id>
          <name>Lucas Torri</name>
          <url>http://unstablebuild.com</url>
        </developer>
        <developer>
          <id>hcwilhelm</id>
          <name>Hans Christian Wilhelm</name>
          <url>https://github.com/hcwilhelm</url>
        </developer>
      </developers>
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
