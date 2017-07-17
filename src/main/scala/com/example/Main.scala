package com.example

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.io.StdIn

object Main extends App with MyLogging with ScalikeJdbcAsync {
  implicit val system = ActorSystem("example")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  implicit val log: LoggingAdapter = Logging(system, getClass)

  connectToDatabase
  val bindingFuture = Http().bindAndHandle(logRequests(Routes.routes), Config.httpd.host, Config.httpd.port)

  //println("Press RETURN to stop...")
  log.info(s"Server online at http://${Config.httpd.host}:${Config.httpd.port}/")
  /*
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => {
      log.info("Shutting down the server")
      system.terminate()
    }) // and shutdown when done
  */
}
