package controllers

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import io.grpc.examples.helloworld.{AbstractGreeterServiceRouter, HelloReply, HelloRequest}
import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

/** Would be written by the user, with support for dependency injection etc */
@Singleton
class GreeterServiceImpl @Inject()(implicit mat: Materializer) extends AbstractGreeterServiceRouter(mat) {

  override def sayHello(in: HelloRequest): Future[HelloReply] = Future.successful(HelloReply(s"Hello, ${in.name}!"))

  override def itKeepsTalking(in: Source[HelloRequest, NotUsed]): Future[HelloReply] = ???

  override def itKeepsReplying(in: HelloRequest): Source[HelloReply, NotUsed] = ???

  override def streamHellos(in: Source[HelloRequest, NotUsed]): Source[HelloReply, NotUsed] = ???
}
