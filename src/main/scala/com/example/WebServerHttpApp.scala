package com.example

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{ HttpApp, Route }
import spray.json._

final case class Message(msg: String)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val messageFormat = jsonFormat1(Message)
}

/**
 * Server will be started calling `WebServerHttpApp.startServer("localhost", 8080)`
 * and it will be shutdown after pressing return.
 */
object WebServerHttpApp extends HttpApp with App with JsonSupport {
  lazy val hello = Message("Say hello to akka-http")
  // Routes that this WebServer must handle are defined here
  // Please note this method was named `route` in versions prior to 10.0.7
  def routes: Route =
    pathEndOrSingleSlash { // Listens to the top `/`
      complete("Server up and running") // Completes with some text
    } ~
      path("hello") { // Listens to paths that are exactly `/hello`
        get { // Listens only to GET requests
          complete(hello) // Completes with some text
        }
      }

  // This will start the server until the return key is pressed
  startServer("localhost", 8080)
}
