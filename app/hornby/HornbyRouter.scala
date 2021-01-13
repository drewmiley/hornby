//package hornby
//
//import play.api.routing.Router.Routes
//import play.api.routing.SimpleRouter
//import play.api.routing.sird._
//
//import javax.inject.Inject
//
///**
//  * Routes and URLs to the HornbyResource controller.
//  */
//class HornbyRouter @Inject()(controller: HornbyController) extends SimpleRouter {
//  val prefix = "/api"
//
//  override def routes: Routes = {
//    case GET(p"/") =>
//      controller.test
//  }
//
//}
