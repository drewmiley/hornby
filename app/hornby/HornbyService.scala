package hornby

import cats.data.OptionT
import hornby.models.{huxley, _}
import hornby.models.huxley.{Arrivals, Departures, DetailedService, StationCRS}
import hornby.models.platform.{Service, Station}

import javax.inject.Inject
import play.api.Configuration
import play.api.cache.{NamedCache, SyncCacheApi}
import play.api.libs.json.{JsString, Json}
import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

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

  private def getNextTrainsOnPlatformsForCRS(crs: String): Future[Option[Station]] = {
    val uniqueServiceIDs = for {
      arrivals <- getArrivals(crs)
      departures <- getDepartures(crs)
      arrivalServices <- Future(arrivals.services.getOrElse(Seq()))
      departureServices <- Future(departures.services.getOrElse(Seq()))
    } yield (arrivalServices ++ departureServices).map(_.serviceID).distinct
    val detailedServices = uniqueServiceIDs
      .map(_.map(getDetailedServiceByID))
      .flatMap(Future.sequence)
    val detailedServicesWithUniquePlatforms: Future[Seq[DetailedService]] = {
      detailedServices.flatMap(services => {
        Future.successful(services.groupBy(_.platform).values.map(_.head).toSeq)
      })
    }
    detailedServicesWithUniquePlatforms.map(services => {
      val platformServices: Seq[Service] = services.map(service => Service.convert(service))
      Some(Station(crs, platformServices))
    })
  }

  def getNextTrainsOnPlatforms(stationName: String) = {
    val getCRSByQuery: Future[Seq[StationCRS]] = ws.url(s"$apiBase/crs/$stationName").get()
      .map { response => Json.parse(response.body).as[Seq[StationCRS]] }
    getCRSByQuery.flatMap(crsStations => {
      if (crsStations.nonEmpty && crsStations.head.stationName == stationName) {
        getNextTrainsOnPlatformsForCRS(crsStations.head.crsCode)
      } else {
        Future.successful(None)
      }
    })
  }
}
