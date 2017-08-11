package com.example

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.PathDirectives
import akka.http.scaladsl.server.directives.RouteDirectives
import com.example.model.{ Pet, Sex, Species }
import com.example.unmarshalling.{ LocalDateFromStringUnmarshaller, SexFromStringUnmarshaller, SpeciesFromStringUnmarshaller }
import java.time.LocalDate
import scala.concurrent.ExecutionContext.Implicits.global

object Routes extends Directives with RouteDirectives with PathDirectives with JsonSupport with LocalDateFromStringUnmarshaller with SexFromStringUnmarshaller with SpeciesFromStringUnmarshaller {
  def routes: Route =
    pathEndOrSingleSlash { // Listens to the top `/`
      complete(Message("Server up and running")) // Completes with some text
    } ~ path("pet") {
      get {
        parameters('dobStart.as[LocalDate].?, 'dobFinish.as[LocalDate].?, 'name.?, 'sex.as[Sex].?, 'species.as[Species].?) { (dobStart, dobFinish, name, sex, species) =>
          onSuccess(Pet.search(dobStart, dobFinish, name, sex, species)) { r => complete(r) }
        }
      } ~ post {
        formFields('dob.as[LocalDate], 'name, 'sex.as[Sex], 'species.as[Species]) { (dob, name, sex, species) =>
          onSuccess(Pet.create(dob, name, sex, species)) { r => complete(r) }
        }
      }
    } ~ path("pet" / LongNumber) { id =>
      get {
        onSuccess(Pet.read(id)) { r =>
          r match {
            case Some(p) => complete(p)
            case None => complete(Message("404"))
          }
        }
      }
    } ~ path("hello") { // Listens to paths that are exactly `/hello`
      get { // Listens only to GET requests
        Thread.sleep(2000)
        complete(Message("Say hello to akka-http")) // Completes with some text
      }
    }
}
