package hornby.models.platform

import play.api.libs.json._

case class Station(
                     stationName: String = "",
                     services: Seq[Service] = Seq()
                   )

object Station {
  implicit val writes: Writes[Station] = Json.writes[Station]
}
