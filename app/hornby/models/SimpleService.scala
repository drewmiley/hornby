package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class SimpleService(
                    originCRS: String = "",
                    destinationCRS: String = "",
                    serviceID: String = "",
                    sta: Option[String] = None,
                    eta: Option[String] = None,
                    std: Option[String] = None,
                    etd: Option[String] = None,
                    platform: Option[String] = None
                  ) {
  val isArrival: Boolean = sta.isDefined
}

object SimpleService {
  implicit val reads: Reads[SimpleService] = (
    (__ \ "origin" \\ "crs").read[String] and
      (__ \ "destination" \\ "crs").read[String] and
      (__ \ "serviceIdPercentEncoded").read[String] and
      (__ \ "sta").readNullable[String] and
      (__ \ "eta").readNullable[String] and
      (__ \ "std").readNullable[String] and
      (__ \ "etd").readNullable[String] and
      (__ \ "platform").readNullable[String]
    )(SimpleService.apply _)
}
