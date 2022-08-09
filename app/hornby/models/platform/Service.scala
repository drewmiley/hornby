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
    val isOrigin = huxleyService.previousCallingPoints match {
      case Some(a) if a.nonEmpty => false
      case _ => true
    }
    val isDestination = huxleyService.subsequentCallingPoints match {
      case Some(a) if a.nonEmpty => false
      case _ => true
    }
    val originCallingPoint = Option(isOrigin) collect { case true =>
      CallingPoint(huxleyService.crs, huxleyService.scheduledDepartureTime.getOrElse(""), actualTime = huxleyService.expectedDepartureTime)}
    val destinationCallingPoint = Option(isDestination) collect { case true =>
      CallingPoint(huxleyService.crs, huxleyService.scheduledArrivalTime.getOrElse(""), expectedTime = huxleyService.expectedArrivalTime)
    }
    val callingAt = Seq(
      originCallingPoint.map(Seq(_)),
      huxleyService.previousCallingPoints,
      huxleyService.subsequentCallingPoints,
      destinationCallingPoint.map(Seq(_))
    ).flatMap(a => a.getOrElse(Seq()))
    Service(
      huxleyService.platform,
      if (isOrigin) { huxleyService.crs } else { huxleyService.previousCallingPoints.getOrElse(Seq()).head.crs },
      if (isDestination) { huxleyService.crs } else { huxleyService.subsequentCallingPoints.getOrElse(Seq()).last.crs },
      callingAt.map(PlatformCallingPoint.convert)
    )
  }
}
