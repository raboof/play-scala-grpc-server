package controllers

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import io.grpc.examples.helloworld.{GreeterService, GreeterServiceHandler}
import javax.inject._
import play.api.inject.{BindingKey, Injector}
import play.api.mvc._
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
class GreeterRouter(impl: GreeterService)(implicit mat: Materializer)
  extends Router {

  @Inject() def this(injector: Injector)(implicit mat: Materializer) = {
//    This works:
//    this(injector.instanceOf(BindingKey(classOf[GreeterServiceImpl])))

    // This doesn't, even if AFAICS `GreeterServiceImpl` is annotated with `GrpcServerImplementation`:
    // No implementation for controllers.GreeterServiceImpl annotated with class controllers.GrpcServerImplementation was bound.
    //  while locating controllers.GreeterServiceImpl annotated with class controllers.GrpcServerImplementation
//    this(injector.instanceOf(BindingKey(classOf[GreeterServiceImpl]).qualifiedWith[GrpcServerImpl]))

    // Similar with '@Named`:
    // No implementation for controllers.GreeterServiceImpl annotated with @com.google.inject.name.Named(value=grpc-impl) was bound.
//    while locating controllers.GreeterServiceImpl annotated with @com.google.inject.name.Named(value=grpc-impl)
    this(injector.instanceOf(BindingKey(classOf[GreeterServiceImpl]).qualifiedWith("grpc-impl")))

    // This is what we'd eventually want (but let's take one step at a time):
//    this(injector.instanceOf(BindingKey(classOf[GreeterServiceImpl]).qualifiedWith[GrpcServerImplementation]))

  }

  val handler = new AkkaHttpHandler {
    val h = GreeterServiceHandler(impl)
    override def apply(request: HttpRequest): Future[HttpResponse] = h(request)
  }

  // could look at GreeterService.name, but isn't this already covered in the route file?
  override def routes: Routes = { case _ ⇒ handler }

  override def documentation: Seq[(String, String, String)] = Seq.empty

  override def withPrefix(prefix: String): Router = this
}
