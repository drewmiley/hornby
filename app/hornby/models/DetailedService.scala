package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class DetailedService(
                       _previousCallingPointsWrapper: Seq[CallingPointWrapper] = Seq(),
                       _subsequentCallingPointsWrapper: Seq[CallingPointWrapper] = Seq(),
                       crs: String = "",
                       platform: Option[String] = None,
                       scheduledArrivalTime: Option[String] = None,
                       expectedArrivalTime: Option[String] = None,
                       scheduledDepartureTime: Option[String] = None,
                       expectedDepartureTime: Option[String] = None
                     ) {
  val previousCallingPoints: Seq[CallingPoint] = _previousCallingPointsWrapper.head.callingPoints
  val subsequentCallingPoints: Seq[CallingPoint] = _subsequentCallingPointsWrapper.head.callingPoints
}

object DetailedService {
  implicit val reads: Reads[DetailedService] = (
    (__ \ "previousCallingPoints").read[Seq[CallingPointWrapper]] and
      (__ \ "subsequentCallingPoints").read[Seq[CallingPointWrapper]] and
      (__ \ "crs").read[String] and
      (__ \ "platform").readNullable[String] and
      (__ \ "sta").readNullable[String] and
      (__ \ "eta").readNullable[String] and
      (__ \ "std").readNullable[String] and
      (__ \ "etd").readNullable[String]
    )(DetailedService.apply _)

  implicit val writes: Writes[DetailedService] = (detailedService: DetailedService) => {
    Json.obj(
      "previousCallingPoints" -> detailedService.previousCallingPoints,
      "subsequentCallingPoints" -> detailedService.subsequentCallingPoints,
      "crs" -> detailedService.crs,
      "platform" -> detailedService.platform,
      "scheduledArrivalTime" -> detailedService.scheduledArrivalTime,
      "expectedArrivalTime" -> detailedService.expectedArrivalTime,
      "scheduledDepartureTime" -> detailedService.scheduledDepartureTime,
      "expectedDepartureTime" -> detailedService.expectedDepartureTime
    )
  }
}

// TODO: Can this be avoided?
case class CallingPointWrapper(
                              callingPoints: Seq[CallingPoint] = Seq()
                              )

object CallingPointWrapper {
  implicit val reads: Reads[CallingPointWrapper] =
    (__ \ "callingPoint").read[Seq[CallingPoint]] map CallingPointWrapper.apply
}
