package com.example.unmarshalling

import akka.http.scaladsl.unmarshalling.{ FromStringUnmarshaller, Unmarshaller }
import java.time.LocalDate
import java.time.format.DateTimeParseException

trait LocalDateFromStringUnmarshaller {
  implicit val localDateFromStringUnmarshaller: FromStringUnmarshaller[LocalDate] = Unmarshaller.strict[String, LocalDate] { s: String =>
    try LocalDate.parse(s)
    catch {
      case e: DateTimeParseException => throw
        if (s.isEmpty) Unmarshaller.NoContentException
        else new IllegalArgumentException(s"'${s}' is not a valid date", e)
    }
  }
}
