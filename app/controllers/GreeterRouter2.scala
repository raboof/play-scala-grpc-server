package controllers

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import io.grpc.examples.helloworld2.{GreeterService, GreeterServiceHandler}
import javax.inject.Inject
import play.api.mvc.ControllerComponents
import play.api.mvc.akkahttp.AkkaHttpHandler
import play.api.routing.{Router, SimpleRouter}
import play.api.routing.Router.Routes

import scala.concurrent.Future

class GreeterRouter2  @Inject()(cc: ControllerComponents, impl: GreeterService2Impl)(implicit mat: Materializer)
  extends SimpleRouter {

  val handler = new AkkaHttpHandler {
    val h = GreeterServiceHandler(impl)
    override def apply(request: HttpRequest): Future[HttpResponse] = h(request)
  }

  // could look at GreeterService.name, but isn't this already covered in the route file?
  override def routes: Routes = {

    case reqHeader ⇒
      println(s"""
                 | in service 2
                 | host: ${reqHeader.host}
                 | path: ${reqHeader.path}
                 | version: ${reqHeader.version}
                 | -----------------------------------------
                """.stripMargin)
      handler

  }

}
