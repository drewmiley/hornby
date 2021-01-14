package hornby.models.platform

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Service(
                    crs: String = "",
                    services: Option[Seq[CallingPoint]] = None
                  )

object Service {
  implicit val writes: Writes[Service] = Json.writes[Service]
}
