package controllers

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import io.grpc.examples.helloworld.{GreeterService, GreeterServiceHandler}
import play.api.inject.Injector
import play.api.mvc.akkahttp.AkkaHttpHandler
import play.api.routing.Router
import play.api.routing.Router.Routes

import scala.concurrent.Future

/**
 * FIXME: this needs to be generated
 * - note that it is the play injector we accept - not hardcoding the router to use Guice
 */
abstract class AbstractGreeterServiceController(injector: Injector) extends Router with GreeterService {

  private val handler = new AkkaHttpHandler {
    implicit val materializer = injector.instanceOf[Materializer]
    // this line is the only reason we have to generate it, so maybe we can make the rest a utility
    val h = GreeterServiceHandler(AbstractGreeterServiceController.this)
    override def apply(request: HttpRequest): Future[HttpResponse] = h(request)
  }

  // could look at GreeterService.name, but isn't this already covered in the route file?
  final override def routes: Routes = { case _ ⇒ handler }
  final override def documentation: Seq[(String, String, String)] = Seq.empty
  final override def withPrefix(prefix: String): Router = this
}
