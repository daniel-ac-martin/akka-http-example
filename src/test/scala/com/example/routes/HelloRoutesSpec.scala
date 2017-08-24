package com.example.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{ Matchers, WordSpec }

class HelloRoutesSpec extends WordSpec with Matchers with ScalatestRouteTest {
  "HelloRoutes" should {
    "answer to GET requests to `/hello`" in {
      Get("/hello") ~> HelloRoutes.routes ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "{\"msg\":\"Say hello to akka-http\"}"
      }
    }
    "not handle a POST request to `/hello`" in {
      Post("/hello") ~> HelloRoutes.routes ~> check {
        handled shouldBe false
      }
    }
    "respond with 405 when not issuing a GET to `/hello` and route is sealed" in {
      Put("/hello") ~> Route.seal(HelloRoutes.routes) ~> check {
        status shouldBe StatusCodes.MethodNotAllowed
      }
    }
  }
}
