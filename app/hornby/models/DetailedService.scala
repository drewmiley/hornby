package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class DetailedService(
                       previousCallingPoints: Seq[CallingPoint] = Seq(),
                       subsequentCallingPoints: Seq[CallingPoint] = Seq(),
                       crs: String = "",
                       sta: String = "",
                       eta: String = "",
                       std: String = "",
                       etd: String = ""
                     )

object DetailedService {
  implicit val reads: Reads[DetailedService] = (
    (__ \ "previousCallingPoints"(0) \ "callingPoint").read[Seq[CallingPoint]] and
      (__ \ "subsequentCallingPoints"(0) \ "callingPoint").read[Seq[CallingPoint]] and
      (__ \ "crs").read[String] and
      (__ \ "sta").read[String] and
      (__ \ "eta").read[String] and
      (__ \ "std").read[String] and
      (__ \ "etd").read[String]
    )(DetailedService.apply _)
}
