package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Departures(
                     crs: String = "",
                     services: Seq[Departure] = Seq()
                   )

object Departures {
  implicit val reads: Reads[Departures] = (
    (__ \ "crs").read[String] and
      (__ \ "trainServices").read[Seq[Departure]]
    )(Departures.apply _)
}
