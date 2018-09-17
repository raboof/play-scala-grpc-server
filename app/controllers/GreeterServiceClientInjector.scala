package controllers

import akka.actor.ActorSystem
import akka.grpc.GrpcClientSettings
import akka.stream.Materializer
import io.grpc.examples.helloworld.GreeterServiceClient
import javax.inject.{Inject, Provider}

import scala.concurrent.ExecutionContext

class GreeterServiceClientInjector @Inject()(implicit sys: ActorSystem, mat: Materializer) extends Provider[GreeterServiceClient] {

  override def get(): GreeterServiceClient = {
    implicit val ec: ExecutionContext = sys.dispatcher
    // TODO get settings from the actorsystem configuration
    // https://github.com/akka/akka-grpc/pull/254
    val settings: GrpcClientSettings = GrpcClientSettings("localhost", 8080)
    GreeterServiceClient(settings)
  }
}
