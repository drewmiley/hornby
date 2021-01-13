package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

// Can combine with Departure
case class Arrival(
                            originCRS: String = "",
                            destinationCRS: String = "",
                            serviceID: String = "",
                            sta: String = "",
                            eta: String = "",
                            platform: String = ""
                          )

object Arrival {
  implicit val reads: Reads[Departure] = (
    (__ \ "origin" \ "crs").read[String] and
      (__ \ "destination" \ "crs").read[String] and
      (__ \ "serviceIdUrlSafe").read[String] and
      (__ \ "sta").read[String] and
      (__ \ "eta").read[String] and
      (__ \ "platform").read[String]
    )(Arrival.apply _)
}
