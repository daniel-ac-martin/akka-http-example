package com.example

import akka.event.Logging.InfoLevel
import akka.http.scaladsl.model.{ HttpRequest, RemoteAddress }
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult
import akka.http.scaladsl.server.directives.LogEntry
import akka.http.scaladsl.server.directives.DebuggingDirectives.{ logRequest, logRequestResult }
import akka.http.scaladsl.server.directives.MiscDirectives.extractClientIP

trait MyLogging {
  val requestString: HttpRequest => String = req => s"${req.method.name} ${req.uri}"

  val requestInfo: RemoteAddress => HttpRequest => LogEntry = { ip => req =>
    val reqStr = requestString(req)

    LogEntry(s"${ip} -> ${reqStr} : ${req}", InfoLevel)
  }

  val responseInfo: RemoteAddress => HttpRequest => RouteResult => Option[LogEntry] = { ip => req =>
    val reqStr = requestString(req)

    {
      case RouteResult.Complete(res) => Some(LogEntry(s"${ip} <- ${reqStr} ${res.status}", InfoLevel))
      case RouteResult.Rejected(msg) => Some(LogEntry(s"${ip} <- ${reqStr} ${msg}", InfoLevel))
    }
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
