package hornby.models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Arrivals(
                   dummy: String = "",
                   dummy1: String = ""
                   )

object Arrivals {
  implicit val reads: Reads[Arrivals] = (
    (__ \ "dummy").read[String] and
      (__ \ "dummy1").read[String]
  )(Arrivals.apply _)
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
