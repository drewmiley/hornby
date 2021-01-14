package hornby.models.platform

import play.api.libs.json._

case class CallingPoint(
                         stationName: String = "",
                         arrivalTime: String = "",
                         departureTime: String = "",
                         expectedTimes: Boolean
                       )

object CallingPoint {
  implicit val writes: Writes[CallingPoint] = Json.writes[CallingPoint]
}
