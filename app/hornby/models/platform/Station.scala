package hornby.models.platform

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Station(
                     crs: String = "",
                     services: Option[Seq[CallingPoint]] = None
                   )

object Station {
  implicit val writes: Writes[Station] = Json.writes[Station]
}
