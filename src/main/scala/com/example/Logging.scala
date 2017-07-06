package com.example

import akka.event.Logging.InfoLevel
import akka.http.scaladsl.model.{ HttpRequest, RemoteAddress }
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult
import akka.http.scaladsl.server.directives.LogEntry
import akka.http.scaladsl.server.directives.DebuggingDirectives.{ logRequest, logRequestResult }
import akka.http.scaladsl.server.directives.MiscDirectives.extractClientIP

final case class RequestData(ip: String, method: String, url: String)
final case class ResponseData(ip: String, method: String, url: String, status: String)

trait MyLogging {
  val requestInfo: RemoteAddress => HttpRequest => LogEntry = { ip => req =>
    val data = RequestData(ip.toString, req.method.name, req.uri.toString)

    LogEntry(data, InfoLevel)
  }

  val responseInfo: RemoteAddress => HttpRequest => RouteResult => Option[LogEntry] = ip => req =>
    {
      case RouteResult.Complete(res) => Some(LogEntry(ResponseData(ip.toString, req.method.name, req.uri.toString, res.status.toString), InfoLevel))
      case RouteResult.Rejected(msg) => Some(LogEntry(ResponseData(ip.toString, req.method.name, req.uri.toString, msg.toString), InfoLevel))
    }

  val logRequests: Route => Route = { routes =>
    extractClientIP { ip =>
      logRequest(requestInfo(ip)) {
        logRequestResult(responseInfo(ip)) {
          routes
        }
      }
    }
  }
}
