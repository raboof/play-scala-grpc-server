package controllers

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import io.grpc.examples.helloworld.{GreeterService, GreeterServiceClient, HelloReply, HelloRequest}
import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

/**
 * Would be written by the user, with support for dependency injection etc
 *
 * Injecting the client just to show we can ;).
 */
@Singleton
class GreeterServiceImpl @Inject()(client: GreeterServiceClient)(implicit mat: Materializer) extends GreeterService {
  println(s"Got injected $client")

  override def sayHello(in: HelloRequest): Future[HelloReply] = Future.successful(HelloReply(s"Hello, ${in.name}!"))

  override def itKeepsTalking(in: Source[HelloRequest, NotUsed]): Future[HelloReply] = ???

  override def itKeepsReplying(in: HelloRequest): Source[HelloReply, NotUsed] = ???

  override def streamHellos(in: Source[HelloRequest, NotUsed]): Source[HelloReply, NotUsed] = ???
}
