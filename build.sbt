name := "compressor-service"

version := "1.0"

scalaVersion := "2.12.2"

mainClass in Compile := Some("com.compressor.app.StartService")


libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "4.0",
  "net.lingala.zip4j" % "zip4j" % "1.3.2",
  "org.mockito" % "mockito-core" % "1.10.19" % Test,
  "org.scalatest" %% "scalatest" % "3.0.1" % Test)

resolvers ++= Seq(
  Resolver.mavenLocal,
  "Maven Repository 2" at "http://repo2.maven.org/",
  "Maven Repository" at "http://repo1.maven.org/"
)

sourceDirectories           in Test <+= baseDirectory(_ / "src" / "test" / "scala" / "unit")
resourceDirectory           in Test <<= baseDirectory(_ / "src" / "test" / "resources")
resourceDirectories         in Test <+= baseDirectory(_ / "src" / "test" / "resources")

Defaults.itSettings
sourceDirectories          in IntegrationTest <+= baseDirectory(_ / "src" / "test" / "scala" / "integration")
resourceDirectory          in IntegrationTest <<= baseDirectory(_ / "src" / "test" / "resources")
resourceDirectories        in IntegrationTest <+= baseDirectory(_ / "src" / "test" / "resources")

javaOptions += "-Xmx1G"