package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class SimpleService(
                    originCRS: String = "",
                    destinationCRS: String = "",
                    serviceID: String = "",
                    scheduledArrivalTime: Option[String] = None,
                    expectedArrivalTime: Option[String] = None,
                    scheduledDepartureTime: Option[String] = None,
                    expectedDepartureTime: Option[String] = None,
                    platform: Option[String] = None
                  ) {
  val isArrival: Boolean = scheduledArrivalTime.isDefined
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

  implicit val writes: Writes[SimpleService] = Json.writes[SimpleService]
}
