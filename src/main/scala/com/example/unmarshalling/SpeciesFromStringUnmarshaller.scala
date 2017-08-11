package com.example.unmarshalling

import akka.http.scaladsl.unmarshalling.{ FromStringUnmarshaller, Unmarshaller }
import com.example.model.Species

trait SpeciesFromStringUnmarshaller {
  implicit val speciesFromStringUnmarshaller: FromStringUnmarshaller[Species] = Unmarshaller.strict[String, Species] { s: String =>
    try Species(s)
    catch {
      case e: Exception => throw
        if (s.isEmpty) Unmarshaller.NoContentException
        else new IllegalArgumentException(s"'${s}' is not a valid species", e)
    }
  }
}
