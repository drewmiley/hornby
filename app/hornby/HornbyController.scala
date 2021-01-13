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

  def test: Action[AnyContent] = HornbyAction.async { implicit request =>
    logger.trace("test: ")
    hornbyService.getStationData("NEW").map { data =>
      Ok(Json.toJson(data))
    }
  }
}
