package controllers

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import io.grpc.examples.helloworld2.{HelloReply, HelloRequest}
import io.grpc.examples.helloworld2.{GreeterService, HelloReply, HelloRequest}
import javax.inject.{Inject, Singleton}

import scala.concurrent.Future

@Singleton
class GreeterService2Impl @Inject()(implicit mat: Materializer)  extends GreeterService {
  println(s"Got injected $mat")

  override def sayHello(in: HelloRequest): Future[HelloReply] = Future.successful(HelloReply(s"Hello, ${in.name} (service2)!"))

  override def itKeepsTalking(in: Source[HelloRequest, NotUsed]): Future[HelloReply] = ???

  override def itKeepsReplying(in: HelloRequest): Source[HelloReply, NotUsed] = ???

  override def streamHellos(in: Source[HelloRequest, NotUsed]): Source[HelloReply, NotUsed] = ???
}
