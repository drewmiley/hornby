package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class DetailedService(
                       private val previousCallingPointsWrapper: Seq[CallingPointWrapper] = Seq(),
                       private val subsequentCallingPointsWrapper: Seq[CallingPointWrapper] = Seq(),
                       crs: String = "",
                       platform: String = "",
                       sta: String = "",
                       eta: String = "",
                       std: String = "",
                       etd: String = ""
                     ) {
  val previousCallingPoints: Seq[CallingPoint] = previousCallingPointsWrapper.head.callingPoints
  val subsequentCallingPoints: Seq[CallingPoint] = subsequentCallingPointsWrapper.head.callingPoints
}

object DetailedService {
  implicit val reads: Reads[DetailedService] = (
    (__ \ "previousCallingPoints").read[Seq[CallingPointWrapper]] and
      (__ \ "subsequentCallingPoints").read[Seq[CallingPointWrapper]] and
      (__ \ "crs").read[String] and
      (__ \ "platform").read[String] and
      (__ \ "sta").read[String] and
      (__ \ "eta").read[String] and
      (__ \ "std").read[String] and
      (__ \ "etd").read[String]
    )(DetailedService.apply _)
}

case class CallingPointWrapper(
                              callingPoints: Seq[CallingPoint] = Seq()
                              )

object CallingPointWrapper {
  implicit val reads: Reads[CallingPointWrapper] =
    (__ \ "callingPoint").read[Seq[CallingPoint]] map CallingPointWrapper.apply
}
