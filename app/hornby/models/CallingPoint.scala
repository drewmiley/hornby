package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

// If et defined, hasCalledAt is true. Else hasCalledAt is false
case class CallingPoint(
                       crs: String = "",
                       st: String = "",
                       et: Option[String] = None,
                       at: Option[String] = None
                     )

object CallingPoint {
  implicit val reads: Reads[CallingPoint] = (
    (__ \ "crs").read[String] and
      (__ \ "st").read[String] and
      (__ \ "et").readNullable[String] and
      (__ \ "at").readNullable[String]
    )(CallingPoint.apply _)
}
