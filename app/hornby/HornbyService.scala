package hornby

import hornby.models._
import hornby.models.huxley.{Arrivals, Departures, DetailedService, StationCRS}

import javax.inject.Inject
import play.api.Configuration
import play.api.cache.{NamedCache, SyncCacheApi}
import play.api.libs.json.{JsString, Json}
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

  def getDepartures(stationCode: String): Future[Departures] = {
    ws.url(s"$apiBase/departures/$stationCode").get().map { response =>
      Json.parse(response.body).as[Departures]
    }
  }

  def getArrivals(stationCode: String): Future[Arrivals] = {
    ws.url(s"$apiBase/arrivals/$stationCode").get().map { response =>
      Json.parse(response.body).as[Arrivals]
    }
  }

  def getCRSByQuery(crsQuery: String): Future[Seq[StationCRS]] = {
    ws.url(s"$apiBase/crs/$crsQuery").get().map { response =>
      Json.parse(response.body).as[Seq[StationCRS]]
    }
  }

  def getDetailedServiceByID(serviceID: String): Future[DetailedService] = {
    ws.url(s"$apiBase/service/$serviceID").get().map { response =>
      Json.parse(response.body).as[DetailedService]
    }
  }
}
