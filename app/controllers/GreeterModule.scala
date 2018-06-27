package controllers

import akka.actor.ActorSystem
import akka.stream.Materializer
import io.grpc.examples.helloworld.GreeterServiceClient
import javax.inject.Inject
import play.api.inject.Binding
import play.api.{Configuration, Environment}

class GreeterModule extends play.api.inject.Module {
  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[GreeterServiceClient].toProvider[GreeterServiceClientInjector],
    )
  }
}
