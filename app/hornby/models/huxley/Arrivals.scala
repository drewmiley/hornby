package hornby.models.huxley

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Arrivals(
                       crs: String = "",
                       services: Option[Seq[SimpleService]] = None
                     )

object Arrivals {
  implicit val reads: Reads[Arrivals] = (
    (__ \ "crs").read[String] and
      (__ \ "trainServices").readNullable[Seq[SimpleService]]
    )(Arrivals.apply _)

  implicit val writes: Writes[Arrivals] = Json.writes[Arrivals]
}
