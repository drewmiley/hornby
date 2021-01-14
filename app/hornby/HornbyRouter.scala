package hornby

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

import javax.inject.Inject

/**
  * Routes and URLs to the HornbyResource controller.
  */
class HornbyRouter @Inject()(controller: HornbyController) extends SimpleRouter {
  val prefix = "/api"

  // Remove router, do directly using routes and controller
  override def routes: Routes = {
    case GET(p"/") =>
      controller.test
    case GET(p"/departures") =>
      controller.getDepartures
    case GET(p"/arrivals") =>
      controller.getArrivals
    case GET(p"/crs") =>
      controller.getCRSByQuery
    case GET(p"/service") =>
      controller.getDetailedServiceByID
  }

}
