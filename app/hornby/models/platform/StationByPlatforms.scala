package hornby.models.platform

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class StationByPlatforms(
                     crs: String = "",
                     services: Option[Seq[CallingPoint]] = None
                   )

object Arrivals {
  implicit val writes: Writes[StationByPlatforms] = Json.writes[StationByPlatforms]
}
