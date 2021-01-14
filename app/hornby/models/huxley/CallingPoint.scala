package hornby.models.huxley

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class CallingPoint(
                       crs: String = "",
                       scheduledTime: String = "",
                       expectedTime: Option[String] = None,
                       actualTime: Option[String] = None
                     ) {
  val hasCalled: Boolean = actualTime match {
    case Some("Delayed") | None => false
    case _ => true
  }
}

object CallingPoint {
  implicit val reads: Reads[CallingPoint] = (
    (__ \ "crs").read[String] and
      (__ \ "st").read[String] and
      (__ \ "et").readNullable[String] and
      (__ \ "at").readNullable[String]
    )(CallingPoint.apply _)

  implicit val writes: Writes[CallingPoint] = Json.writes[CallingPoint]
}
