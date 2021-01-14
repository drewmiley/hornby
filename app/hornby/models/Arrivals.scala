package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Arrivals(
                       crs: String = "",
                       services: Option[Seq[SimpleService]] = None
                     )

object Arrivals {
  implicit val reads: Reads[Arrivals] = (
    (__ \ "crs").read[String] and
      (__ \ "trainServices").readNullable[Seq[SimpleService]]
    )(Arrivals.apply _)
}
