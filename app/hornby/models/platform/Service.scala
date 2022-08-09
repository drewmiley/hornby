package hornby.models.platform

import hornby.models.huxley.{CallingPoint, DetailedService}
import play.api.libs.json._

case class Service(
                    platform: Option[String] = None,
                    origin: String = "",
                    destination: String = "",
                    callingAt: Seq[PlatformCallingPoint] = Seq()
                  )

object Service {
  implicit val writes: Writes[Service] = Json.writes[Service]

  // TODO: Can we make this implicit?
  def convert(huxleyService: DetailedService): Service = {
    val pcp = huxleyService.previousCallingPoints.getOrElse(Seq())
    val scp = huxleyService.subsequentCallingPoints.getOrElse(Seq())
    val isOrigin = pcp.isEmpty
    val isDestination = scp.isEmpty
//    TODO: Can I avoid this if/else?
//    Maybe with a case match
    val callingAt = if (isOrigin) {
      val originCallingPoint = CallingPoint(
        huxleyService.crs,
        huxleyService.scheduledDepartureTime.getOrElse(""),
        actualTime = huxleyService.expectedDepartureTime
      )
      Seq(originCallingPoint) ++ pcp ++ scp
    } else if (isDestination) {
      val destinationCallingPoint = CallingPoint(
        huxleyService.crs,
        huxleyService.scheduledArrivalTime.getOrElse(""),
        expectedTime = huxleyService.expectedArrivalTime
      )
      pcp ++ scp ++ Seq(destinationCallingPoint)
    } else {
      pcp ++ scp
    }
    Service(
      huxleyService.platform,
      if (isOrigin) { huxleyService.crs } else { pcp.head.crs },
      if (isDestination) { huxleyService.crs } else { scp.last.crs },
      callingAt.map(PlatformCallingPoint.convert)
    )
  }
}
