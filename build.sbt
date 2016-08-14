name := "cl-library"

version := "0.1-SNAPSHOT"

resolvers += "repo.codahale.com" at "http://repo.codahale.com"

libraryDependencies ++= Seq(
    "log4j"              %   "log4j"                  % "1.2.16",
    "commons-codec"      %   "commons-codec"          % "1.5",
    "commons-httpclient" %   "commons-httpclient"     % "3.1",
    "com.codahale"       %   "jerkson_2.9.1"          % "0.5.0",
    "org.jsoup"          %   "jsoup"                  % "1.7.2",
    "joda-time"          %   "joda-time"              % "2.4",
    "org.joda"           %   "joda-convert"           % "1.2"
)


libraryDependencies ++= Seq(
    "junit"                 %   "junit"                 % "4.8.2"     % "test",
    "org.mockito"           %   "mockito-all"           % "1.9.5"     % "test",
    "org.specs2"            %%  "specs2"                % "2.2.3"     % "test",
    "org.scalatest"         %%  "scalatest"             % "2.0"       % "test"
)
