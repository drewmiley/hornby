package hornby.models.huxley

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class DetailedService(
                       _previousCallingPointsWrapper: Option[Seq[CallingPointWrapper]] = None,
                       _subsequentCallingPointsWrapper: Option[Seq[CallingPointWrapper]] = None,
                       crs: String = "",
                       platform: Option[String] = None,
                       scheduledArrivalTime: Option[String] = None,
                       expectedArrivalTime: Option[String] = None,
                       scheduledDepartureTime: Option[String] = None,
                       expectedDepartureTime: Option[String] = None
                     ) {
  // TODO: This is smelly code
  val previousCallingPoints: Seq[CallingPoint] = if (_previousCallingPointsWrapper.getOrElse(Seq()).nonEmpty) {
    _previousCallingPointsWrapper.getOrElse(Seq()).head.callingPoints
  } else {
    Seq()
  }
  val subsequentCallingPoints: Seq[CallingPoint] = if (_subsequentCallingPointsWrapper.getOrElse(Seq()).nonEmpty) {
    _subsequentCallingPointsWrapper.getOrElse(Seq()).head.callingPoints
  } else {
    Seq()
  }
}

object DetailedService {
  implicit val reads: Reads[DetailedService] = (
    (__ \ "previousCallingPoints").readNullable[Seq[CallingPointWrapper]] and
      (__ \ "subsequentCallingPoints").readNullable[Seq[CallingPointWrapper]] and
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
