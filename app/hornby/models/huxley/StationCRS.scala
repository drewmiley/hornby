package hornby.models.huxley

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class StationCRS(
                     stationName: String = "",
                     crsCode: String = ""
                     )

object StationCRS {
  implicit val reads: Reads[StationCRS] = (
    (__ \ "stationName").read[String] and
      (__ \ "crsCode").read[String]
    )(StationCRS.apply _)

  implicit val writes: Writes[StationCRS] = Json.writes[StationCRS]
}
