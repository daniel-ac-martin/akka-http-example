package com.example.routes

import akka.http.scaladsl.server.{ Directives, Route }
import com.example.model.Message

object RootRoutes extends Directives {
  def routes: Route =
    pathEndOrSingleSlash {
      complete("Server up and running")
    }
}
