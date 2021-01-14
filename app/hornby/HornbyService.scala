package hornby

import hornby.models._

import javax.inject.Inject
import play.api.Configuration
import play.api.cache.{NamedCache, SyncCacheApi}
import play.api.libs.json.Json
import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}

class HornbyService @Inject()(ws: WSClient, @NamedCache("session-cache") cache: SyncCacheApi, configuration: Configuration)(implicit ec: ExecutionContext) {
//  val apiKey: String = configuration.get[String]("apiKey")
  val apiBase: String = "https://huxley2.azurewebsites.net/"

  // Due to change of API, apiKey no longer used, although we are now using a live API instance that may not be fully reliable.
//  def addApiKeyToRequest(url: String): String = {
//    if (url.contains("?")) {
//      url + s"&apiKey=$apiKey"
//    } else {
//      url + s"?apiKey=$apiKey"
//    }
//  }

//    val address: String = addApiKeyToRequest(s"$apiBase$apiRoute")

  def getDepartures(stationCode: String): Future[String] = {
    val departuresAddress: String = s"departures/$stationCode"
    ws.url(departuresAddress).get().map { response =>
      System.out.println(response.body)
      val departures = Json.parse(response.body).as[Departures]
      departures.toString
    }
  }

  def getArrivals(stationCode: String): Future[String] = {
    val arrivalsAddress: String = s"arrivals/$stationCode"
    ws.url(arrivalsAddress).get().map { response =>
      System.out.println(response.body)
      val arrivals = Json.parse(response.body).as[Arrivals]
      arrivals.toString
    }
  }

  def getCRSByQuery(crsQuery: String): Future[String] = {
    val crsQueryAddress: String = s"crs/$crsQuery"
    ws.url(crsQueryAddress).get().map { response =>
      System.out.println(response.body)
      val crsQueryResult = Json.parse(response.body).as[Seq[StationCRS]]
      crsQueryResult.toString
    }
  }

  def getDetailedServiceByID(serviceID: String): Future[String] = {
    val serviceIdAddress: String = s"service/{serviceID}"
    ws.url(serviceIdAddress).get().map { response =>
      System.out.println(response.body)
      val detailedService = Json.parse(response.body).as[DetailedService]
      detailedService.toString
    }
  }

  def getStationData(stationCode: String): Future[String] = {
    val address: String = s"departures/$stationCode/"
    cache.set("test", "test")
    ws.url(address).get().map { response =>
      System.out.println(response.body)
      (Json.parse(response.body) \ "crs").as[String]
    }
  }

}
