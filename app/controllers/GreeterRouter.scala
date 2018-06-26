package controllers

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import io.grpc.examples.helloworld.{GreeterService, GreeterServiceHandler}
import javax.inject._
import play.api.mvc._
import play.api.mvc.akkahttp.AkkaHttpHandler
import play.api.routing.{Router, SimpleRouter}
import play.api.routing.Router.Routes

import scala.concurrent.Future

/**
  * Can this be a generated class? I hoped to inject 'GreeterService' here and leave it up to the user to provide
  * his implementation.
  *
  * Is there a nice place where we can ask the user to 'binder.bind(classOf[GreeterService]).to(classOf[GreeterServiceImpl])'?
  */
@Singleton
class GreeterRouter @Inject()(cc: ControllerComponents, impl: GreeterServiceImpl)(implicit mat: Materializer)
  extends SimpleRouter {

  val handler = new AkkaHttpHandler {
    val h = GreeterServiceHandler(impl)
    override def apply(request: HttpRequest): Future[HttpResponse] = h(request)
  }

  // could look at GreeterService.name, but isn't this already covered in the route file?
  override def routes: Routes = {

    case reqHeader ⇒

      println(s"""
                  | in service 1
                  | host: ${reqHeader.host}
                  | path: ${reqHeader.path}
                  | version: ${reqHeader.version}
                  | -----------------------------------------
                """.stripMargin)
      handler

  }

}
