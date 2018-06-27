package controllers

import example.myapp.echo.grpc.{EchoMessage, EchoService}

import scala.concurrent.Future

class EchoServiceImpl extends EchoService {
  override def echo(in: EchoMessage): Future[EchoMessage] =
    Future.successful(EchoMessage(in.payload))
}
