package com.example.routes

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Route
import com.example.JsonSupport
import com.example.model.{ Message, Pet, Sex, Species }
import com.example.unmarshalling.{ LocalDateFromStringUnmarshaller, SexFromStringUnmarshaller, SpeciesFromStringUnmarshaller }
import java.time.LocalDate

object PetRoutes extends Directives with JsonSupport with LocalDateFromStringUnmarshaller with SexFromStringUnmarshaller with SpeciesFromStringUnmarshaller {
  def routes: Route =
    path("pet") {
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
    }
}
