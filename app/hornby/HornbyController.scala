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

  def getDepartures: Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.getDepartures("NEW").map { data =>
      Ok(Json.toJson(data))
    }
  }

  def getArrivals: Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.getArrivals("NEW").map { data =>
      Ok(Json.toJson(data))
    }
  }

  def getCRSByQuery: Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.getCRSByQuery("newc").map { data =>
      Ok(Json.toJson(data))
    }
  }

  def getDetailedServiceByID: Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.getDetailedServiceByID("service_id").map { data =>
      Ok(Json.toJson(data))
    }
  }

  def test: Action[AnyContent] = HornbyAction.async { implicit request =>
    hornbyService.test("NCL").map { data => Ok(Json.toJson(data)) }
  }
}
