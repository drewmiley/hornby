package hornby.models.platform

import hornby.models.huxley.CallingPoint
import play.api.libs.json._

case class PlatformCallingPoint(
                         stationName: String = "",
                         arrivalTime: Option[String] = None,
                         departureTime: Option[String] = None,
                         timesAreExpected: Boolean
                       )

object PlatformCallingPoint {
  implicit val writes: Writes[PlatformCallingPoint] = Json.writes[PlatformCallingPoint]

  // TODO: Can we make this implicit?
  def convert(huxleyCallingPoint: CallingPoint): PlatformCallingPoint = {
    // TODO: Implement this function
    val arrivalTime = None
    val departureTime = None
    val timesAreExpected = false
    PlatformCallingPoint(huxleyCallingPoint.crs, arrivalTime, departureTime, timesAreExpected)
  }
}
