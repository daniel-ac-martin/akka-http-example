package com.example

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.io.StdIn

object Main extends App {
  implicit val system = ActorSystem("example")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  implicit val log: LoggingAdapter = Logging(system, getClass)

  val bindingFuture = Http().bindAndHandle(Routes.routes, "localhost", 8080)

  println("Press RETURN to stop...")
  log.info(s"Server online at http://localhost:8080/")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => {
      log.info("Shutting down the server")
      system.terminate()
    }) // and shutdown when done
}
