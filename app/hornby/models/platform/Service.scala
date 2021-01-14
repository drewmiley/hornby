package hornby.models.platform

import hornby.models.huxley.DetailedService
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
    val callingAt = huxleyService.previousCallingPoints ++ huxleyService.subsequentCallingPoints
    val isOrigin = huxleyService.previousCallingPoints.isEmpty
    val isDestination = huxleyService.subsequentCallingPoints.isEmpty
    val origin = if (isOrigin) { huxleyService.crs } else { huxleyService.previousCallingPoints.head.crs }
    val destination = if (isDestination) { huxleyService.crs } else { huxleyService.subsequentCallingPoints.last.crs }
    // TODO: Need to add to calling points if isOrigin or isDestination
    Service(
      huxleyService.platform,
      origin,
      destination,
      callingAt.map(PlatformCallingPoint.convert)
    )
  }
}
