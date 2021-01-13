//package hornby
//
//import javax.inject.Inject
//import play.api.Configuration
//import play.api.cache.{NamedCache, SyncCacheApi}
//import play.api.libs.json.Json
//import play.api.libs.ws._
//
//import scala.concurrent.{ExecutionContext, Future}
//
//class HornbyService @Inject()(ws: WSClient, @NamedCache("session-cache") cache: SyncCacheApi, configuration: Configuration)(implicit ec: ExecutionContext) {
//  val apiKey: String = configuration.get[String]("apiKey")
//  val apiBase: String = "https://api.departureboard.io/api/v2.0/"
//
//  def addApiKeyToRequest(url: String): String = {
//    if (url.contains("?")) {
//      url + s"&apiKey=$apiKey"
//    } else {
//      url + s"?apiKey=$apiKey"
//    }
//  }
//  //    https://api.departureboard.io/api/v2.0/getDeparturesByCRS/NEW/?apiKey=apiKey
//  def getStationData(stationCode: String): Future[String] = {
//        val apiRoute: String = s"getDeparturesByCRS/$stationCode/"
//        val address: String = addApiKeyToRequest(s"$apiBase$apiRoute")
//        cache.set("test", "test")
//        ws.url(address).get().map { response =>
//          System.out.println(response.body)
//          (Json.parse(response.body) \ "crs").as[String]
//        }
//      }
//
//}
//
////class API @Inject()(ws: WSClient, @NamedCache("session-cache") cache: SyncCacheApi, configuration: Configuration)(implicit ec: ExecutionContext) {
////  private val endpoint = configuration.getString("api").getOrElse("http://localhost:8080/") + "api/"
////
////  private val getValidQuizOptionsEndpoint = s"${endpoint}quizoptions"
////  private val getValidQuizCodesEndpoint = s"${endpoint}quizcodes"
////  private def generateQuizEndpoint = s"${endpoint}newquiz"
////  private def getQuizByCodeEndpoint(code: String) = s"${endpoint}quiz/$code"
////  private def submitAnswersEndpoint(code: String, user: String) = s"${endpoint}answers/$code/$user"
////  private def getLeaderboardByCodeEndpoint(code: String) = s"${endpoint}leaderboard/$code"
////  private def getLeaderboardsByUserEndpoint(user: String = "") =
////    s"${endpoint}leaderboards${if(user.nonEmpty) s"?user=$user" else ""}"
////
////  def getValidQuizOptions: Future[ValidQuizOptions] = {
////    def getViaApi: Future[ValidQuizOptions] =
////      ws.url(getValidQuizOptionsEndpoint).get().map { response =>
////        val validQuizOptions = Json.parse(response.body).as[ValidQuizOptions]
////        cache.set("validQuizOptions", validQuizOptions)
////        validQuizOptions
////      }
////    cache.get[ValidQuizOptions]("validQuizOptions") map { Future.successful } getOrElse getViaApi
////  }
////
////  def getValidQuizCodes(forceViaApi: Boolean = false): Future[Seq[String]] = {
////    def getViaApi: Future[Seq[String]] =
////      ws.url(getValidQuizCodesEndpoint).get().map { response =>
////        val validQuizCodes = Json.parse(response.body).as[Seq[String]]
////        cache.set("validQuizCodes", validQuizCodes)
////        validQuizCodes
////      }
////    (forceViaApi, cache.get[Seq[String]]("validQuizCodes")) match {
////      case (false, Some(validQuizCodes)) => Future.successful(validQuizCodes)
////      case _ => getViaApi
////    }
////  }
////
////  def generateQuiz(generateQuiz: GenerateQuiz): Future[String] = {
////    val req = generateQuiz.toRequestBody
////    ws.url(generateQuizEndpoint).post(req).map { response =>
////      (Json.parse(response.body) \ "code").as[String]
////    }
////  }
////
////  def getQuizByCode(code: String): Future[Seq[Question]] =
////    ws.url(getQuizByCodeEndpoint(code)).get().map { response =>
////      cache.set("code", code)
////      (Json.parse(response.body) \ "quiz").as[Seq[Question]]
////    }
////
////  def submitAnswers(submitAnswers: SubmitAnswers): Future[String] = {
////    val req = submitAnswers.toRequestBody
////    ws.url(submitAnswersEndpoint(submitAnswers.code, submitAnswers.name)).post(req).map { response =>
////      (Json.parse(response.body) \ "code").as[String]
////    }
////  }
////
////  def getLeaderboardByCode(code: String): Future[Seq[LeaderboardRow]] =
////    ws.url(getLeaderboardByCodeEndpoint(code)).get().map { response =>
////      (Json.parse(response.body) \ "results").as[Seq[LeaderboardRow]]
////    }
////
////  def getLeaderboardsByUser(user: String = ""): Future[Seq[Leaderboard]] =
////    ws.url(getLeaderboardsByUserEndpoint(user)).get().map { response =>
////      Json.parse(response.body).as[Seq[Leaderboard]]
////    }
////}
