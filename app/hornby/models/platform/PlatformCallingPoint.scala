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
    val arrivalTime = huxleyCallingPoint.expectedTime.map(d => {
      if (d == "On time") { huxleyCallingPoint.scheduledTime } else { d }
    })
    val departureTime = huxleyCallingPoint.actualTime.map(d => {
      if (d == "On time") { huxleyCallingPoint.scheduledTime } else { d }
    })
    val timesAreExpected = huxleyCallingPoint.expectedTime.isDefined
    PlatformCallingPoint(huxleyCallingPoint.crs, arrivalTime, departureTime, timesAreExpected)
  }
}
