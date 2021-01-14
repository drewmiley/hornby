package hornby

import play.api.libs.json.Json
import play.api.mvc._
import play.api.{Configuration, Logger}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

/**
  * Takes HTTP requests and produces JSON.
  */
class HornbyController @Inject()(hornbyService: HornbyService, cc: HornbyControllerComponents, configuration: Configuration)(
    implicit ec: ExecutionContext)
    extends HornbyBaseController(cc) {

  private val logger = Logger(getClass)

  def getDepartures(crs: String): Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.getDepartures(crs).map { data =>
      Ok(Json.toJson(data))
    }
  }

  def getArrivals(crs: String): Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.getArrivals(crs).map { data =>
      Ok(Json.toJson(data))
    }
  }

  def getCRSByQuery(query: String): Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.getCRSByQuery(query).map { data =>
      Ok(Json.toJson(data))
    }
  }

  def getDetailedServiceByID(id: String): Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.getDetailedServiceByID(id).map { data =>
      Ok(Json.toJson(data))
    }
  }

  def getNextTrainsOnPlatforms(stationName: String): Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.getNextTrainsOnPlatforms(stationName).map { data =>
      Ok(Json.toJson(data))
    }
  }
}
