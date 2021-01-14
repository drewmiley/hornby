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
    val pcp = huxleyService.previousCallingPoints.getOrElse(Seq())
    val scp = huxleyService.subsequentCallingPoints.getOrElse(Seq())
    val callingAt = pcp ++ scp
    val isOrigin = pcp.isEmpty
    val isDestination = scp.isEmpty
    val origin = if (isOrigin) { huxleyService.crs } else { pcp.head.crs }
    val destination = if (isDestination) { huxleyService.crs } else { scp.last.crs }
    // TODO: Need to add to calling points if isOrigin or isDestination
    Service(
      huxleyService.platform,
      origin,
      destination,
      callingAt.map(PlatformCallingPoint.convert)
    )
  }
}
