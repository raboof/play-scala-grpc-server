# play-scala-grpc-server

Example project based on the starter to experiment with what
integrating a GRPC server into a Play app based on akka-grpc
could look like.

Changes:

* added a 'proto' folder for the protobuf definitions
* added akka-grpc-plugin to the plugins and enabled it
* added a proto file to app/protobuf
* added some 'glue' (that hopefully can disappear or be generated?)
* added a stub service implementation

To do:
* HTTP2, testing etc

Starting:
* `sbt stage`
* Until [this pr](https://github.com/playframework/playframework/pull/8485) is released you may need to replace `target/universal/stage/jetty-alpn-agent/jetty-alpn-agent-2.0.6.jar` with [2.7.0](https://repo1.maven.org/maven2/org/mortbay/jetty/alpn/jetty-alpn-agent/2.0.7/jetty-alpn-agent-2.0.7.jar)
*`./ssl-play run`

Testing with [grpcc](https://github.com/njpatel/grpcc):
* `keytool -export -alias playgeneratedtrusted -keystore generated.keystore -storepass "" -file playgeneratedtrusted.crt`
* `openssl x509 -in playgeneratedtrusted.crt -out playgeneratedtrusted.pem -inform DER -outform PEM`
* `grpcc -a localhost:9443 -p app/protobuf/helloworld.proto --eval 'client.sayHello({ name: "John" }, printReply)' --root_cert ./playgeneratedtrusted.pem`

## play-scala-starter-example

[<img src="https://img.shields.io/travis/playframework/play-scala-starter-example.svg"/>](https://travis-ci.org/playframework/play-scala-starter-example)

This is a starter application that shows how Play works.  Please see the documentation at <https://www.playframework.com/documentation/latest/Home> for more details.

## Running

Run this using [sbt](http://www.scala-sbt.org/).  If you downloaded this project from <http://www.playframework.com/download> then you'll find a prepackaged version of sbt in the project directory:

```bash
sbt run
```

And then go to <http://localhost:9000> to see the running web application.

There are several demonstration files available in this template.

## Controllers

- HomeController.scala:

  Shows how to handle simple HTTP requests.

- AsyncController.scala:

  Shows how to do asynchronous programming when handling a request.

- CountController.scala:

  Shows how to inject a component into a controller and use the component when
  handling requests.

## Components

- Module.scala:

  Shows how to use Guice to bind all the components needed by your application.

- Counter.scala:

  An example of a component that contains state, in this case a simple counter.

- ApplicationTimer.scala:

  An example of a component that starts when the application starts and stops
  when the application stops.

## Filters

- Filters.scala:

  Creates the list of HTTP filters used by your application.

- ExampleFilter.scala

  A simple filter that adds a header to every response.
