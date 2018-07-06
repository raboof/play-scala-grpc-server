package controllers

import java.lang.annotation.Annotation

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import io.grpc.examples.helloworld.{GreeterService, HelloReply, HelloRequest}
import javax.inject.{Inject, Named, Singleton}

import scala.concurrent.Future

/** Would be written by the user, with support for dependency injection etc */
@Singleton
@Named("grpc-impl")
//@GrpcServerImpl
class GreeterServiceImpl @Inject()(implicit mat: Materializer) extends GrpcServerImpl with GreeterService {
  println(s"Got injected $mat")

  override def sayHello(in: HelloRequest): Future[HelloReply] = Future.successful(HelloReply(s"Hello, ${in.name}!"))

  override def itKeepsTalking(in: Source[HelloRequest, NotUsed]): Future[HelloReply] = ???

  override def itKeepsReplying(in: HelloRequest): Source[HelloReply, NotUsed] = ???

  override def streamHellos(in: Source[HelloRequest, NotUsed]): Source[HelloReply, NotUsed] = ???

  override def annotationType(): Class[_ <: Annotation] = classOf[GrpcServerImpl]
}
