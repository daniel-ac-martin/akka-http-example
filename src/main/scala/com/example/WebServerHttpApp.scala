package com.example

//import akka.event.{ Logging, LoggingAdapter }
//import akka.event.Logging.WarningLevel
//import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.{ HttpApp, Route }
//import akka.http.scaladsl.server.directives.DebuggingDirectives
//import akka.http.scaladsl.server.directives.LoggingMagnet
//import de.heikoseeberger.akkahttpjson4s.Json4sSupport
//import org.json4s.DefaultFormats
//import org.json4s.native.Serialization

/**
 * Server will be started calling `WebServerHttpApp.startServer()`
 * and it will be shutdown after pressing return.
 */
object WebServerHttpApp extends HttpApp with App {
  // Routes that this WebServer must handle are defined here
  // Please note this method was named `route` in versions prior to 10.0.7
  def routes: Route = Routes.routes

  // This will start the server until the return key is pressed
  startServer(Config.httpd.host, Config.httpd.port)
}
