package controllers

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import io.grpc.examples.helloworld.{GreeterService, GreeterServiceHandler}
import javax.inject._
import play.api.mvc.akkahttp.AkkaHttpHandler
import play.api.routing.Router
import play.api.routing.Router.Routes

import scala.concurrent.Future

/**
  * Can this be a generated class? I hoped to inject 'GreeterService' here and leave it up to the user to provide
  * his implementation.
  *
  * Is there a nice place where we can ask the user to 'binder.bind(classOf[GreeterService]).to(classOf[GreeterServiceImpl])'?
  */
@Singleton
class GreeterRouter @Inject()(impl: GreeterServiceImpl)(implicit mat: Materializer)
  extends Router {

  protected val prefix: String = GreeterService.name

  val handler = new AkkaHttpHandler {
    val h = GreeterServiceHandler(impl)
    override def apply(request: HttpRequest): Future[HttpResponse] = h(request)
  }

  // could look at GreeterService.name, but isn't this already covered in the route file?
  override def routes: Routes = {
    case rh if rh.path.startsWith(prefix) ⇒ handler
  }

  override def documentation: Seq[(String, String, String)] = Seq.empty

  override def withPrefix(additionalPrefix: String): Router = {
      val newPrefix =
        if (prefix == GreeterService.name) additionalPrefix
        else Router.prefixPath(additionalPrefix + prefix)

      new GreeterRouter(impl) {
        override val prefix = newPrefix
      }
    }
}
