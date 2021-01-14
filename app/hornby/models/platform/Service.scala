package hornby.models.platform

import play.api.libs.json._

case class Service(
                    platform: String = "",
                    origin: String = "",
                    destination: String = "",
                    callingAt: Seq[CallingPoint] = Seq()
                  )

object Service {
  implicit val writes: Writes[Service] = Json.writes[Service]
}
