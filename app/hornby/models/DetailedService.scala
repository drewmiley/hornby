package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class DetailedService(
                       previousCallingPoints: Seq[CallingPoint] = Seq(),
                       subsequentCallingPoints: Seq[CallingPoint] = Seq(),
                       crs: String = "",
                       sta: String = "",
                       eta: String = "",
                       std: String = "",
                       etd: String = ""
                     )

object DetailedService {
  implicit val reads: Reads[DetailedService] = (
    (__ \ "previousCallingPoints"(0) \ "callingPoint").read[Seq[CallingPoint]] and
      (__ \ "subsequentCallingPoints"(0) \ "callingPoint").read[Seq[CallingPoint]] and
      (__ \ "crs").read[String] and
      (__ \ "sta").read[String] and
      (__ \ "eta").read[String] and
      (__ \ "std").read[String] and
      (__ \ "etd").read[String]
    )(DetailedService.apply _)
}

//case class Leaderboard(code: String = "", rows: Seq[LeaderboardRow] = Seq()) {
//  val rowsWithPosition: Seq[LeaderboardRow] = rows
//    .sorted(Ordering.by[LeaderboardRow, Int](_.score).reverse)
//    .foldLeft(Seq[LeaderboardRow]())((acc, row) => {
//      val position: Int = if (acc.nonEmpty && row.score == acc.last.score) {
//        acc.last.positionAsString.toInt
//      } else {
//        acc.length + 1
//      }
//      acc :+ LeaderboardRow(row.score, row.user, Some(position))
//    })
//}
//
//object Leaderboard {
//  implicit val reads: Reads[Leaderboard] = (
//    (__ \ "code").read[String] and
//      (__ \ "results").read[Seq[LeaderboardRow]]
//    )(Leaderboard.apply _)
//}
