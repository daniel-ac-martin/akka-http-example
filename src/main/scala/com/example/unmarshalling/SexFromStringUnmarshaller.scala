package com.example.unmarshalling

import akka.http.scaladsl.unmarshalling.{ FromStringUnmarshaller, Unmarshaller }
import com.example.model.Sex

trait SexFromStringUnmarshaller {
  implicit val sexFromStringUnmarshaller: FromStringUnmarshaller[Sex] = Unmarshaller.strict[String, Sex] { s: String =>
    try Sex(s)
    catch {
      case e: Exception => throw
        if (s.isEmpty) Unmarshaller.NoContentException
        else new IllegalArgumentException(s"'${s}' is not a valid sex", e)
    }
  }
}
