package com.example

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.PathDirectives
import akka.http.scaladsl.server.directives.RouteDirectives

import scala.concurrent.ExecutionContext.Implicits.global

import com.example.model.Pet

object Routes extends Directives with RouteDirectives with PathDirectives with JsonSupport {
  def routes: Route =
    pathEndOrSingleSlash { // Listens to the top `/`
      complete(Message("Server up and running")) // Completes with some text
    } ~
      path("pet" / IntNumber) { id =>
        get {
          onSuccess(Pet.find(id)) { r =>
            r match {
              case Some(p) => complete(p)
              case None => complete(Message("404"))
            }
          }
        }
      } ~
      path("hello") { // Listens to paths that are exactly `/hello`
        get { // Listens only to GET requests
          Thread.sleep(2000)
          complete(Message("Say hello to akka-http")) // Completes with some text
        }
      }
}
