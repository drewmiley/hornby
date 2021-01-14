package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Departures(
                     crs: String = "",
                     services: Option[Seq[SimpleService]] = None
                   )

object Departures {
  implicit val reads: Reads[Departures] = (
    (__ \ "crs").read[String] and
      (__ \ "trainServices").readNullable[Seq[SimpleService]]
    )(Departures.apply _)

  implicit val writes: Writes[Departures] = Json.writes[Departures]
}
