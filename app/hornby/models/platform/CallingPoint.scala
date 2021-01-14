package hornby.models.platform

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class CallingPoint(
                         stationName: String = "",
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
  implicit val writes: Writes[CallingPoint] = Json.writes[CallingPoint]
}
