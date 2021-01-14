package hornby.models.platform

import hornby.models.huxley.DetailedService
import play.api.libs.json._

case class Service(
                    platform: String = "",
                    origin: String = "",
                    destination: String = "",
                    callingAt: Seq[CallingPoint] = Seq()
                  )

object Service {
  implicit val writes: Writes[Service] = Json.writes[Service]

  // TODO: Implement this function
  // TODO: Can we make this implicit?
  def convert(huxleyService: DetailedService): Service = {
    Service()
  }
}
