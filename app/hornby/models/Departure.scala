package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

// Can combine with Arrival
case class Departure(
                       originCRS: String = "",
                       destinationCRS: String = "",
                       serviceID: String = "",
                       std: String = "",
                       etd: String = "",
                       platform: String = ""
                     )

object Departure {
  implicit val reads: Reads[Departure] = (
    (__ \ "origin" \ "crs").read[String] and
      (__ \ "destination" \ "crs").read[String] and
      (__ \ "serviceIdUrlSafe").read[String] and
      (__ \ "std").read[String] and
      (__ \ "etd").read[String] and
      (__ \ "platform").read[String]
    )(Departure.apply _)
}
