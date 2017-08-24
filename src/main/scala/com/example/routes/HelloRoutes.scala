package com.example.routes

import akka.http.scaladsl.server.{ Directives, Route }
import com.example.JsonSupport
import com.example.model.Message

object HelloRoutes extends Directives with JsonSupport {
  def routes: Route =
    path("hello") {
      get {
        Thread.sleep(2000)
        complete(Message("Say hello to akka-http"))
      }
    }
}
