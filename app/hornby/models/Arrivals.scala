package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Arrivals(
                       crs: String = "",
                       services: Seq[Arrival] = Seq()
                     )

object Arrivals {
  implicit val reads: Reads[Arrivals] = (
    (__ \ "crs").read[String] and
      (__ \ "trainServices").read[Seq[Arrival]]
    )(Arrivals.apply _)
}
